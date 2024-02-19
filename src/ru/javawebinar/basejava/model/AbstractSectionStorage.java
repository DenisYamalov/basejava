package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public abstract class AbstractSectionStorage<T> extends Section {
    protected final Collection<T> storage;

    protected AbstractSectionStorage(Collection<T> storage) {
        this.storage = storage;
    }

    public void add(T t) {
        if (!contains(t)) {
            if (t != null) {
                storage.add(t);
            }
        } else {
            addExisting(t);
        }
    }

    public void update(T t) {
        //TODO find way to search periods by field
        if (contains(t)) {
            storage.remove(t);
            storage.add(t);
        } else {
            addNotExisting(t);
        }
    }

    public void remove(T t) {
        if (contains(t)) {
            storage.remove(t);
        } else {
            addNotExisting(t);
        }
    }

    public List<T> getAll() {
        return new ArrayList<>(storage);
    }

    protected abstract void addExisting(T t);

    protected abstract void addNotExisting(T t);

    private boolean contains(T t) {
        return storage.contains(t);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractSectionStorage)) return false;
        AbstractSectionStorage<?> that = (AbstractSectionStorage<?>) o;
        return Objects.equals(storage, that.storage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storage);
    }
}
