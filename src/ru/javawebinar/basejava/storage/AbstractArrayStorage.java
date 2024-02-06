package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes;

    @Override
    protected Resume doGet(Integer searchKey) {
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

    @Override
    public int size() {
        return countResumes;
    }

    @Override
    protected final boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume r) {
        storage[searchKey] = r;
    }

    @Override
    protected Stream<Resume> resumeStream() {
        return Arrays.stream(storage).filter(Objects::nonNull);
    }
}

