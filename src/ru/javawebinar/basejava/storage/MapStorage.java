package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    protected Resume doGet(String searchKey) {
        return resumeMap.get(searchKey);
    }

    @Override
    protected void doSave(String searchKey, Resume r) {
        resumeMap.put(r.getUuid(), r);
    }

    @Override
    protected void doUpdate(String searchKey, Resume r) {
        resumeMap.replace(r.getUuid(), r);
    }

    @Override
    protected void doDelete(String searchKey) {
        resumeMap.remove(searchKey);
    }

    @Override
    protected Stream<Resume> resumeStream() {
        return resumeMap.values().stream();
    }

    @Override
    public int size() {
        return resumeMap.size();
    }
}
