package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int countResumes;

    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    public void save(Resume r) {
        storage[countResumes] = r;
        countResumes++;
    }

    public Resume get(String uuid) {
        return Arrays.stream(storage)
                .limit(countResumes)
                .filter(r -> uuid.equals(r.getUuid()))
                .findFirst().orElse(null);
    }

    public void delete(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                storage[i] = storage[countResumes - 1];
                storage[countResumes] = null;
                countResumes--;
            }
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
}
