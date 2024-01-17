package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    private static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];

    protected int countResumes;

    @Override
    public int size() {
        return countResumes;
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

    protected abstract int getIndex(String uuid);

    protected void printNotExist(String uuid) {
        System.out.println("Resume " + uuid + " doesn't exist in storage");
    }
}
