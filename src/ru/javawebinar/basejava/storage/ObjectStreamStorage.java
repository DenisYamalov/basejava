package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serialstrategy.ObjectStreamStrategy;

import java.io.*;

public class ObjectStreamStorage extends FileStorage {
    public ObjectStreamStorage(File directory) {
        super(directory, new ObjectStreamStrategy());
    }
}
