package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    public static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    @Override
    protected final void incrementCounter() {
        countResumes++;
    }

    protected void checkStorageLimit(Resume r) {
        if (countResumes == STORAGE_LIMIT) {
            throw new StorageException("Storage fulfilled, resume " + r.getUuid() + " not added", r.getUuid());
        }
    }

    @Override
    protected Resume getResume(int index) {
        return storage[index];
    }

    @Override
    protected final void decrementCounter() {
        countResumes--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    @Override
    public int size() {
        return countResumes;
    }

    @Override
    protected void updateResume(Resume resume, int index) {
        storage[index] = resume;
    }
}

