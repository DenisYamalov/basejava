package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes;

    @Override
    protected Resume getResume(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    public final void save(Resume r) {
        if (countResumes == STORAGE_LIMIT) {
            throw new StorageException("Storage fulfilled, resume " + r.getUuid() + " not added", r.getUuid());
        }
        super.save(r);
        countResumes++;
    }

    @Override
    public final void delete(String uuid) {
        super.delete(uuid);
        countResumes--;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
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
    protected final boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void updateResume(Integer searchKey, Resume r) {
        storage[searchKey] = r;
    }
}

