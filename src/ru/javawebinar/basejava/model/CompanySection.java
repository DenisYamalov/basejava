package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.exception.ResumeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CompanySection extends Section {
    private final Set<Period> periods = new TreeSet<>();

    public void addPeriod(Period period) {
        if (!periods.contains(period)) {
            periods.add(period);
        } else {
            throw new ResumeException("Period " + period + " already exist.");
        }
    }

    public void updatePeriod(Period period) {
        //TODO find way to search periods by field
        if (periods.contains(period)) {
            periods.remove(period);
            periods.add(period);
        } else {
            throw new ResumeException("Period " + period + " already exist.");
        }
    }

    public void removePeriod(Period period) {
        if (periods.contains(period)) {
            periods.remove(period);
        } else {
            throw new ResumeException("Period " + period + " not exist.");
        }
    }

    public List<Period> getPeriods() {
        return new ArrayList<>(periods);
    }
}
