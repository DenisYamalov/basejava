package ru.javawebinar.basejava.model;

import java.util.Set;
import java.util.TreeSet;

public class CompanySection extends Section {
    private final String companyName;
    private final String website;
    private Set<Period> periods = new TreeSet<>();

    public CompanySection(String companyName, String website) {
        this.companyName = companyName;
        this.website = website;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getWebsite() {
        return website;
    }

    public Set<Period> getPeriods() {
        return periods;
    }

    public void setPeriods(Set<Period> periods) {
        this.periods = periods;
    }

    @Override
    public String toString() {
        return "CompanySection{" + "companyName='" + companyName + '\'' + ", website='" + website + '\'' + ", periods=" + periods + "} " + super.toString();
    }
}
