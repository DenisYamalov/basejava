package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.exception.ResumeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSectionStorage<String> {
    public ListSection() {
        super(new ArrayList<>());
    }

    @Override
    protected void addExisting(String s) {
        throw new ResumeException("Story " + s + " already exist.");
    }

    @Override
    protected void addNotExisting(String s) {
        throw new ResumeException("Story " + s + " not exist.");
    }

    @Override
    public String toString() {
        return "ListSection{" + "storage=" + storage + "} " + super.toString();
    }
}
