package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public abstract class AbstractMapStorage<T> extends AbstractStorage<T> {
    protected final Map<String, Resume> resumeMap = new HashMap<>();

    @Override
    public void clear() {
        resumeMap.clear();
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
