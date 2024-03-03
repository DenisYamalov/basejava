package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serialstrategy.ObjectStreamStrategy;

public class ObjectStreamPathStorage extends PathStorage {
    public ObjectStreamPathStorage(String dir) {
        super(dir, new ObjectStreamStrategy());
    }
}
