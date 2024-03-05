package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serialstrategy.DataStreamStrategy;

class DataStreamPathStorageTest extends AbstractArrayStorageTest {
    public DataStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new DataStreamStrategy()));
    }
}