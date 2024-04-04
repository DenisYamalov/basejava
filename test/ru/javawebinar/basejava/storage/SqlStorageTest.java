package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.storage.serialstrategy.XmlStreamSrategy;

class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getDbURL(), Config.get().getDbUser(), Config.get().getDbPassword()));
    }
}