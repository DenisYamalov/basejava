package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> resumeList = new ArrayList<>();

    protected boolean isExist(int index) {
        return index < 0;
    }

    private void getExistingSearchKey(String uuid) {
        throw new ExistStorageException(uuid);
    }

    private void getNotExistingSearchKey(String uuid) {
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (isExist(index)) {
            getNotExistingSearchKey(uuid);
        }
        return resumeList.get(index);
    }

    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (!isExist(index)) {
            getExistingSearchKey(r.getUuid());
        }
        resumeList.add(r);
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (isExist(index)) {
            getNotExistingSearchKey(resume.getUuid());
        }
        resumeList.set(index, resume);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (isExist(index)) {
            getNotExistingSearchKey(uuid);
        }
        resumeList.remove(index);
    }

    @Override
    public Resume[] getAll() {
        return resumeList.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return resumeList.size();
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (resumeList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
