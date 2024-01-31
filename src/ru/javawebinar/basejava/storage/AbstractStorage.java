package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {


    protected abstract int getIndex(String uuid);

    protected abstract void deleteResume(int index);

    protected abstract void insertResume(int index, Resume r);
}
