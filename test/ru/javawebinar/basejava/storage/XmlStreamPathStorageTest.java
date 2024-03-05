package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serialstrategy.ObjectStreamStrategy;
import ru.javawebinar.basejava.storage.serialstrategy.XmlStreamSrategy;

class XmlStreamPathStorageTest extends AbstractStorageTest {
    public XmlStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(),new XmlStreamSrategy()));
    }
}