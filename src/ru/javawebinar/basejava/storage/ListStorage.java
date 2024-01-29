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
    public void save(Resume r) {
        resumeList.add(r);
    }

    @Override
    protected Resume getResume(int index) {
        return resumeList.get(index);
    }

    @Override
    protected void decrementCounter() {
        //do nothing
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

    @Override
    protected int getIndex(String uuid) {
        return resumeList.indexOf(new Resume(uuid));
    }

    @Override
    protected void deleteResume(int index) {
        resumeList.remove(index);
    }

}
