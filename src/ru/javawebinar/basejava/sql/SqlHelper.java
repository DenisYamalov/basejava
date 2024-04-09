package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.dbstrategy.SqlExecutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T execute(String sql, SqlExecutor<T> executor) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            return executor.execute(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> T transactionAndBatchExecute (Connection conn, Resume r, String sql, SqlExecutor<T> executor, String batchSQL, SqlExecutor<T> batchExecutor) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            executor.execute(ps);
        }
        try (PreparedStatement ps = conn.prepareStatement(batchSQL)) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                batchExecutor.execute(ps);
                ps.addBatch();
            }
            ps.executeBatch();
        }
        return null;
    }

    public <T> void executeAndClose(String sql, SqlExecutor<T> executor, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            executor.execute(ps);
        }
    }

    public <T> void executeBatch(Resume r,
                                 String batchSQL,
                                 SqlExecutor<T> batchExecutor,
                                 Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(batchSQL)) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                batchExecutor.execute(ps);
            }
            ps.executeBatch();
        }
    }
}
