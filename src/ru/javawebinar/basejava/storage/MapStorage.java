package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {
    private final Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String uuid) {
        return resumeMap.containsKey(uuid);
    }

    @Override
    public void clear() {
        resumeMap.clear();
    }

    @Override
    protected Resume getResume(String searchKey) {
        return resumeMap.get(searchKey);
    }

    @Override
    protected void insertResume(String searchKey, Resume r) {
        resumeMap.put(r.getUuid(), r);
    }

    @Override
    protected void updateResume(String searchKey, Resume r) {
        resumeMap.replace(r.getUuid(), r);
    }

    @Override
    protected void deleteResume(String searchKey) {
        resumeMap.remove(searchKey);
    }

    @Override
    public Resume[] getAll() {
        return resumeMap.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return resumeMap.size();
    }
}
