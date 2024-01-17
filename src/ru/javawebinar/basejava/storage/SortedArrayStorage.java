package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        System.out.println("Current index = " + index);
        if (countResumes >= storage.length) {
            System.out.println("Storage fulfilled, resume " + r.getUuid() + " not added");
        } else if (index >= 0) {
            System.out.println("Resume uuid = " + r.getUuid() + " exists");
        } else {
            int insertIndex = Math.abs(index) - 1;
            System.arraycopy(storage, insertIndex + 1, storage, insertIndex + 2, countResumes);
            storage[insertIndex] = r;
            System.out.println("Resume added: " + r.getUuid());
            countResumes++;
        }
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, countResumes, searchKey);
    }
}
