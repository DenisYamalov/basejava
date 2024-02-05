package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage<T> implements Storage {
    @Override
    public final Resume get(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            getNotExistingSearchKey(uuid);
        }
        return getResume(searchKey);
    }

    @Override
    public void save(Resume r) {
        T searchKey = getSearchKey(r.getUuid());
        if (isExist(searchKey)) {
            getExistingSearchKey(r.getUuid());
        }
        insertResume(searchKey, r);
    }

    @Override
    public final void update(Resume resume) {
        T searchKey = getSearchKey(resume.getUuid());
        if (!isExist(searchKey)) {
            getNotExistingSearchKey(resume.getUuid());
        }
        updateResume(searchKey, resume);
    }

    @Override
    public void delete(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            getNotExistingSearchKey(uuid);
        }
        deleteResume(searchKey);
    }

    /**
     * Gets resume by validated searchKey
     *
     * @param searchKey checked searchKey
     * @return resume
     */
    protected abstract Resume getResume(T searchKey);

    /**
     * Insert validated resume by validated searchKey
     *
     * @param searchKey checked searchKey
     * @param r         resume
     */
    protected abstract void insertResume(T searchKey, Resume r);

    /**
     * Update validated resume by validated searchKey
     *
     * @param searchKey checked searchKey
     * @param r         resume
     */
    protected abstract void updateResume(T searchKey, Resume r);

    protected abstract T getSearchKey(String uuid);

    /**
     * Delete resume by validated searchKey
     *
     * @param searchKey checked searchKey
     */
    protected abstract void deleteResume(T searchKey);

    protected abstract boolean isExist(T searchKey);

    private void getExistingSearchKey(String uuid) {
        throw new ExistStorageException(uuid);
    }

    private void getNotExistingSearchKey(String uuid) {
        throw new NotExistStorageException(uuid);
    }
}
