package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    public void save(Resume r) {
        if (countResumes >= storage.length) {
            System.out.println("Storage fulfilled, resume " + r.getUuid() + " not added");
        } else if (getIndex(r.getUuid()) != -1) {
            System.out.println("Resume uuid = " + r.getUuid() + " exists");
        } else {
            storage[countResumes] = r;
            countResumes++;
        }
    }

    @Override
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

    protected int getIndex(String uuid) {
        for (int i = 0; i < countResumes; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
