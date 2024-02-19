package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period implements Comparable<Period> {
    private final LocalDate startDate;
    private LocalDate finishDate;
    private final String companyName;
    private final String website;
    private final String title;
    private String description;

    public Period(LocalDate startDate, LocalDate finishDate, String companyName,
                  String website, String description) {
        this(startDate, finishDate, companyName, website, null, description);
    }

    public Period(LocalDate startDate, LocalDate finishDate, String companyName,
                  String website, String description, String title) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.companyName = companyName;
        this.website = website;
        this.title = title;
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getWebsite() {
        return website;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Period)) return false;
        Period period = (Period) o;
        return Objects.equals(startDate, period.startDate) && Objects.equals(finishDate, period.finishDate)
                && Objects.equals(companyName, period.companyName) && Objects.equals(website, period.website)
                && Objects.equals(title, period.title) && Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, finishDate, companyName, website, title, description);
    }

    @Override
    public String toString() {
        return "Period{" +
                "startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", companyName='" + companyName + '\'' +
                ", website='" + website + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public int compareTo(Period o) {
        return startDate.compareTo(o.startDate);
    }
}
