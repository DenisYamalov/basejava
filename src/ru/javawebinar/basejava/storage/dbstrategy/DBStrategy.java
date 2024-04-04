package ru.javawebinar.basejava.storage.dbstrategy;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface DBStrategy<T>{
    T execute (PreparedStatement ps) throws SQLException;
}
