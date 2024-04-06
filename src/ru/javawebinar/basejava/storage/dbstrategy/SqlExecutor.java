package ru.javawebinar.basejava.storage.dbstrategy;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlExecutor<T>{
    T execute (PreparedStatement ps) throws SQLException;
}
