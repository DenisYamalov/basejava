package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serialstrategy.JsonStreamStrategy;
import ru.javawebinar.basejava.storage.serialstrategy.XmlStreamSrategy;

class JsonStreamPathStorageTest extends AbstractStorageTest {
    public JsonStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(),new JsonStreamStrategy()));
    }
}