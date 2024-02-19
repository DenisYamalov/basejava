package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.exception.ResumeException;

import java.util.TreeSet;

public class CompanySection extends AbstractSectionStorage<Period> {
    public CompanySection() {
        super(new TreeSet<>());
    }

    @Override
    protected void addExisting(Period period) {
        throw new ResumeException("Period " + period + " already exist.");
    }

    @Override
    protected void addNotExisting(Period period) {
        throw new ResumeException("Period " + period + " not exist.");
    }

    @Override
    public String toString() {
        return "CompanySection{" + "storage=" + storage + "} " + super.toString();
    }
}
