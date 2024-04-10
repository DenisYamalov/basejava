package ru.javawebinar.basejava.storage;


import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) " +
                                                                      "VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                    ps.setString(1, r.getUuid());
                    ps.setString(2, e.getKey().name());
                    ps.setString(3, e.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
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
                getSetContact(resume, resultSet);
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
        return sqlHelper.transactionalExecute(conn -> {
            List<Resume> resumes = new ArrayList<>();

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    Resume r = new Resume(resultSet.getString("uuid"), resultSet.getString("full_name"));
                    try (PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM contact")) {
                        ResultSet rs = ps1.executeQuery();
                        while (rs.next()) {
                            getSetContact(r, rs);
                        }
                    }
                    resumes.add(r);
                }
                ps.execute();
            }
            return resumes;
        });
    }

    private static void getSetContact(Resume r, ResultSet rs) throws SQLException {
        r.setContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
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
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            Map<ContactType, String> contactMap = new EnumMap<>(ContactType.class);
            //get all contacts from db
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, resume.getUuid());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    contactMap.put(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                }
            }
            //update existing contacts
            try (PreparedStatement ps = conn.prepareStatement("UPDATE contact SET value = ? " +
                                                                      "WHERE resume_uuid = ? AND type = ?")) {
                for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                    if (contactMap.containsKey(e.getKey())) {
                        ps.setString(1, e.getValue());
                        ps.setString(2, resume.getUuid());
                        ps.setString(3, e.getKey().name());
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
            }
            //remove obsolete contacts
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact " +
                                                                      "WHERE resume_uuid = ? AND type = ?")) {
                for (Map.Entry<ContactType, String> e : contactMap.entrySet()) {
                    if (!resume.getContacts().containsKey(e.getKey())) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, e.getKey().name());
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
            }
            //add new contacts
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES " +
                                                                      "(?,?,?)")) {
                for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                    if (!contactMap.containsKey(e.getKey())) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, e.getKey().name());
                        ps.setString(3, e.getValue());
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
            }
            return null;
        });
    }
}