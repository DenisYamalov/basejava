package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(index);
    }

    @Override
    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
            decrementCounter();
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            updateResume(resume, index);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }
    @Override
    public final void save(Resume r) {
        int index = getIndex(r.getUuid());
        checkStorageLimit(r);
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            insertResume(index, r);
            incrementCounter();
        }
    }

    protected abstract void incrementCounter();

    protected abstract void checkStorageLimit(Resume r);


    protected abstract void updateResume(Resume resume, int index);

    protected abstract void decrementCounter();

    protected abstract Resume getResume(int index);

    protected abstract int getIndex(String uuid);

    protected abstract void deleteResume(int index);
    protected abstract void insertResume(int index, Resume r);
}
