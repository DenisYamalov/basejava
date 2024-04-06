package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.storage.dbstrategy.SqlExecutor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            if (e.getSQLState().startsWith("23")) {
                throw new ExistStorageException(e);
            }
            throw new StorageException(e);
        }
    }
}
