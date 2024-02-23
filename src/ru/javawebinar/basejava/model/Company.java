package ru.javawebinar.basejava.model;

import java.util.Objects;
import java.util.Set;

public class Company {
    private final Link homepage;
    private final Set<Period> periods;

    public Company(String companyName, String website, Set<Period> periods) {
        Objects.requireNonNull(periods,"periods must not be null");
        this.homepage = new Link(companyName, website);
        this.periods = periods;
    }

    public Link getHomepage() {
        return homepage;
    }

    public Set<Period> getPeriods() {
        return periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return Objects.equals(homepage, company.homepage) && Objects.equals(periods, company.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homepage, periods);
    }

    @Override
    public String toString() {
        return "Company{" + "homepage=" + homepage + ", periods=" + periods + '}';
    }
}
