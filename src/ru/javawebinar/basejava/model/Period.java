package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period implements Comparable<Period> {
    private final LocalDate startDate;
    private LocalDate finishDate;
    private final String title;
    private String description;

    public Period(LocalDate startDate, LocalDate finishDate, String description) {
        this(startDate, finishDate, description, null);
    }

    public Period(LocalDate startDate, LocalDate finishDate, String description, String title) {
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.title = title;
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
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
    public int compareTo(Period o) {
        return startDate.compareTo(o.startDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Period)) return false;
        Period period = (Period) o;
        return Objects.equals(startDate, period.startDate) && Objects.equals(finishDate, period.finishDate) && Objects.equals(title, period.title) && Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, finishDate, title, description);
    }

    @Override
    public String toString() {
        return "Period{" + "startDate=" + startDate + ", finishDate=" + finishDate + ", title='" + title + '\'' + ", "
                + "description='" + description + '\'' + '}';
    }
}
