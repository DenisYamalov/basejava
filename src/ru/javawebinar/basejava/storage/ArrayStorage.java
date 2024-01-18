package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    protected int getIndex(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void saveWithoutCheck(int index, Resume r) {
        storage[countResumes] = r;
        countResumes++;
    }

    @Override
    protected void deleteWithoutCheck(int index) {
        storage[index] = storage[countResumes - 1];
        storage[countResumes] = null;
        countResumes--;
    }

    @Override
    protected void updateWithoutCheck(int index, Resume resume) {
        storage[index] = resume;
    }
}
