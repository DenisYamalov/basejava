package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage<T> implements Storage {
    @Override
    public final Resume get(String uuid) {
        return doGet(getExistingSearchKey(uuid));
    }

    @Override
    public void save(Resume r) {
        doSave(getNotExistingSearchKey(r.getUuid()), r);
    }

    @Override
    public final void update(Resume resume) {
        doUpdate(getExistingSearchKey(resume.getUuid()), resume);
    }

    @Override
    public void delete(String uuid) {
        doDelete(getExistingSearchKey(uuid));
    }

    /**
     * Gets resume by validated searchKey
     *
     * @param searchKey checked searchKey
     * @return resume
     */
    protected abstract Resume doGet(T searchKey);

    /**
     * Insert validated resume by validated searchKey
     *
     * @param searchKey checked searchKey
     * @param r         resume
     */
    protected abstract void doSave(T searchKey, Resume r);

    /**
     * Update validated resume by validated searchKey
     *
     * @param searchKey checked searchKey
     * @param r         resume
     */
    protected abstract void doUpdate(T searchKey, Resume r);

    /**
     * Delete resume by validated searchKey
     *
     * @param searchKey checked searchKey
     */
    protected abstract void doDelete(T searchKey);

    protected abstract T getSearchKey(String uuid);

    protected abstract boolean isExist(T searchKey);

    private T getExistingSearchKey(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private T getNotExistingSearchKey(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
