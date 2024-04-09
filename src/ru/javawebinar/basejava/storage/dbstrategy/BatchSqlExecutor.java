package ru.javawebinar.basejava.storage.dbstrategy;

import ru.javawebinar.basejava.model.ContactType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface BatchSqlExecutor<T>{
    void execute(PreparedStatement ps, ContactType contactType, String contact) throws SQLException;
}
