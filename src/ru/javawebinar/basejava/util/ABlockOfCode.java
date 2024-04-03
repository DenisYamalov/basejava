package ru.javawebinar.basejava.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface ABlockOfCode<T>{
    T execute (PreparedStatement ps) throws SQLException;
}
