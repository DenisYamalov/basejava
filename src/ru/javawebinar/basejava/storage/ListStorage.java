package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> resumeList = new ArrayList<>();

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public void save(Resume r) {
        resumeList.add(r);
    }

    //TODO move same code to AbstractStorage
    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return resumeList.get(index);
    }

    //TODO move same code to AbstractStorage
    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
        } else {
            throw new NotExistStorageException(uuid);
        }
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
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            resumeList.set(index, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }

    }

    //TODO change index searching or provide that equals calculating only by uuid
    protected int getIndex(String uuid) {
        return resumeList.indexOf(new Resume(uuid));
    }

    protected void deleteResume(int index) {
        resumeList.remove(index);
    }

}
