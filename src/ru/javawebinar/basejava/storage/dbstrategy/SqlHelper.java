package ru.javawebinar.basejava.storage.dbstrategy;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T execute(String sql, DBStrategy<T> dbStrategy) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            return dbStrategy.execute(ps);
        } catch (SQLException e) {
            throw new ExistStorageException(e);
        }
    }
}
