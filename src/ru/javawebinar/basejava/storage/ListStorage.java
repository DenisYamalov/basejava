package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> resumeList = new ArrayList<>();

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    protected Resume getResume(Integer searchKey) {
        return resumeList.get(searchKey);
    }

    @Override
    protected void insertResume(Integer searchKey, Resume r) {
        resumeList.add(r);
    }

    @Override
    protected void updateResume(Integer searchKey, Resume r) {
        resumeList.set(searchKey, r);
    }

    @Override
    protected void deleteResume(Integer searchKey) {
        resumeList.remove(searchKey.intValue());
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
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (resumeList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
