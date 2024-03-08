package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serialstrategy.ObjectStreamStrategy;

import static org.junit.jupiter.api.Assertions.*;

class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new FileStorage(STORAGE_DIR,new ObjectStreamStrategy()));
    }
}