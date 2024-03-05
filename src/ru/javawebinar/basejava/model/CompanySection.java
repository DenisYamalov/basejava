package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.*;

public class CompanySection extends Section implements Serializable {
    private static final long serialVersionUID = -7859061215880493601L;
    private List<Company> companies;

    public CompanySection() {
    }

    public CompanySection(Company... companies) {
        this(Arrays.asList(companies));
    }

    public CompanySection(List<Company> companies) {
        Objects.requireNonNull(companies, "companies must not be null");
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return companies;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(companies, that.companies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companies);
    }

    @Override
    public String toString() {
        return "CompanySection{" + "companies=" + companies + "} " + super.toString();
    }
}