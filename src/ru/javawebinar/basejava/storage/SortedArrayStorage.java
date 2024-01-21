package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, countResumes, searchKey);
    }

    @Override
    protected void insertResume(int index, Resume r) {
        int insertIndex = Math.abs(index) - 1;
        System.arraycopy(storage, insertIndex + 1, storage, insertIndex + 2, countResumes);
        storage[insertIndex] = r;
        System.out.println("Resume added: " + r.getUuid());
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, countResumes);
    }

    @Override
    public void update(Resume resume) {
        super.update(resume);
        Arrays.sort(storage, 0, countResumes);
    }
}
