package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int countResumes;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, countResumes, null);
        countResumes = 0;
    }

    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (countResumes >= storage.length) {
            System.out.println("Storage fulfilled, resume " + r.getUuid() + " not added");
        } else if (index >= 0) {
            System.out.println("Resume uuid = " + r.getUuid() + " exists");
        } else {
            saveWithoutCheck(index, r);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        printNotExist(uuid);
        return null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, countResumes);
    }

    @Override
    public int size() {
        return countResumes;
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
        } else {
            printNotExist(resume.getUuid());
        }
    }

    protected abstract int getIndex(String uuid);

    protected void printNotExist(String uuid) {
        System.out.println("Resume " + uuid + " doesn't exist in storage");
    }

    protected abstract void saveWithoutCheck(int index, Resume r);
}
