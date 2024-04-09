package ru.javawebinar.basejava.storage;


import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;
import ru.javawebinar.basejava.storage.dbstrategy.BatchSqlExecutor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn ->
                  sqlHelper.transactionAndBatchExecute(conn, r, "INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                                           ps -> {
                                               ps.setString(1, r.getUuid());
                                               ps.setString(2, r.getFullName());
                                               ps.execute();
                                               return null;
                                           }, "INSERT INTO contact (resume_uuid, type, value) " +
                                                   "VALUES (?,?,?)",
                                           ps -> butchExecute(ps, r,
                                                 (ps1, contactType, contact) -> {
                                                     ps1.setString(1, r.getUuid());
                                                     ps1.setString(2, contactType.name());
                                                     ps1.setString(3, contact);
                                                 })
                                           ));
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume r " +
                                         "LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                                         "WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, resultSet.getString("full_name"));
            do {
                String value = resultSet.getString("value");
                ContactType type = ContactType.valueOf(resultSet.getString("type"));
                resume.setContact(type, value);
            } while (resultSet.next());
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumes = new LinkedHashMap<>();
        return sqlHelper.transactionalExecute(conn ->
                sqlHelper.transactionAndBatchExecute(conn, null,
                                      "SELECT * FROM resume ORDER BY full_name, uuid",
                                      ps -> {
                                          ResultSet resultSet = ps.executeQuery();
                                          while (resultSet.next()) {
                                              Resume r = new Resume(resultSet.getString("uuid"),
                                                                    resultSet.getString("full_name"));
                                              resumes.put(r.getUuid(), r);
                                          }
                                          ps.execute();
                                          return null;
                                      }, "SELECT * FROM contact",
                                      ps -> {
                                          ResultSet resultSet = ps.executeQuery();
                                          while (resultSet.next()) {
                                              Resume r = resumes.get(resultSet.getString("resume_uuid"));
                                              r.setContact(ContactType.valueOf(resultSet.getString("type")),
                                                           resultSet.getString("value"));
                                          }
                                          return new ArrayList<>(resumes.values());
                                      }
                                      ));
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) AS count FROM resume", ps -> {
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return resultSet.getInt("count");
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn ->
                  sqlHelper.transactionAndBatchExecute(conn, resume, "UPDATE resume SET full_name = ? WHERE uuid = ?",
                                        ps -> {
                                            ps.setString(1, resume.getFullName());
                                            ps.setString(2, resume.getUuid());
                                            if (ps.executeUpdate() == 0) {
                                                throw new NotExistStorageException(resume.getUuid());
                                            }
                                            return null;
                                        }, "UPDATE contact SET value = ? " +
                                                "WHERE resume_uuid = ? AND type = ?",
                                           ps -> butchExecute(ps, resume, (ps1, contactType, contact) -> {
                                               ps1.setString(1, contact);
                                               ps1.setString(2, resume.getUuid());
                                               ps1.setString(3, contactType.name());
                                           })
                                        ));
    }

    private <T> T butchExecute (PreparedStatement ps, Resume r, BatchSqlExecutor<T> batchExecutor) throws SQLException {
        for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
            batchExecutor.execute(ps, e.getKey(), e.getValue());
            ps.addBatch();
        }
        ps.executeBatch();
        return null;
    }
}
