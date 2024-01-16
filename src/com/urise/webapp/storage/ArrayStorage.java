package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int countResumes;

    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    public void save(Resume r) {
        if (countResumes >= storage.length){
            System.out.println("Storage fulfilled, resume " + r.getUuid() + " not added");
        } else if (getIndex(r.getUuid()) != -1) {
            System.out.println("Resume uuid = " + r.getUuid() + " exists");
        } else {
            storage[countResumes] = r;
            countResumes++;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        printNotExist(uuid);
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            storage[index] = storage[countResumes - 1];
            storage[countResumes] = null;
            countResumes--;
        } else {
            printNotExist(uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    public int size() {
        return countResumes;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
        } else {
            printNotExist(resume.getUuid());
        }
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
    private void printNotExist(String uuid){
        System.out.println("Resume " + uuid + " doesn't exist in storage");
    }
}
