package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doSave(Integer searchKey, Resume r) {
        storage[countResumes] = r;
    }

    @Override
    protected void doDelete(Integer searchKey) {
        storage[searchKey] = storage[countResumes - 1];
        storage[countResumes] = null;
    }

}
