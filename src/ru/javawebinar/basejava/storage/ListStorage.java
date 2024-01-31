package ru.javawebinar.basejava.storage;

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
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        return resumeList.get(index);
    }
    @Override
    public void save(Resume r) {
        resumeList.add(r);

    }
    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        resumeList.set(index,resume);
    }
    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        resumeList.remove(index);
    }
//TODO remove
    @Override
    protected void insertResume(int index, Resume r) {
        resumeList.add(r);
    }

//    @Override
    protected Resume getResume(int index) {
        return resumeList.get(index);
    }

    @Override
    public Resume[] getAll() {
        return resumeList.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return resumeList.size();
    }



    protected void updateResume(Resume resume, int index) {
        resumeList.set(index, resume);
    }

//TODO compare uuid
    @Override
    protected int getIndex(String uuid) {
        return resumeList.indexOf(new Resume(uuid));
    }

    @Override
    protected void deleteResume(int index) {
        resumeList.remove(index);
    }

}
