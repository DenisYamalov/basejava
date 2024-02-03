package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, countResumes, searchKey);
    }

    @Override
    protected void insertResume(Integer searchKey, Resume r) {
        int insertIndex = Math.abs(searchKey) - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, countResumes - insertIndex);
        storage[insertIndex] = r;
        System.out.println("Resume added: " + r.getUuid());
    }

    @Override
    protected void deleteResume(Integer searchKey) {
        int numberMoved = countResumes - searchKey - 1;
        if (numberMoved > 0) {
            System.arraycopy(storage, searchKey + 1, storage, searchKey, numberMoved);
        }
    }
}
