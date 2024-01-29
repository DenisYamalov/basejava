package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    private static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    @Override
    public final void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (countResumes == STORAGE_LIMIT) {
            throw new StorageException("Storage fulfilled, resume " + r.getUuid() + " not added", r.getUuid());
        } else if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            insertResume(index, r);
            countResumes++;
        }
    }

    @Override
    protected Resume getResume(int index) {
        return storage[index];
    }

    @Override
    protected void decrementCounter() {
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

    protected abstract void insertResume(int index, Resume r);
}

