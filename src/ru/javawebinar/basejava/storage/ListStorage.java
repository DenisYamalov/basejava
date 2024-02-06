package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    protected Resume doGet(Integer searchKey) {
        return resumeList.get(searchKey);
    }

    @Override
    protected void doSave(Integer searchKey, Resume r) {
        resumeList.add(r);
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume r) {
        resumeList.set(searchKey, r);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        resumeList.remove(searchKey.intValue());
    }

    @Override
    protected Stream<Resume> resumeStream() {
        return resumeList.stream();
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
