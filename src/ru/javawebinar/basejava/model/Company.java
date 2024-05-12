package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    private static final long serialVersionUID = 4814339807787759827L;
    private Link homepage;
    private List<Period> periods;

    public Company() {
    }

    public Company(Link homepage, List<Period> periods) {
        this.homepage = homepage;
        this.periods = periods;
    }

    public Company(String companyName, String website, List<Period> periods) {
        this.homepage = new Link(companyName, website);
        this.periods = periods;
    }

    public Company(String companyName, String website, Period... periods) {
        this(new Link(companyName, website), Arrays.stream(periods).collect(Collectors.toList()));
    }

    public Link getHomepage() {
        return homepage;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void setHomepage(Link homepage) {
        this.homepage = homepage;
    }

    public void setPeriods(List<Period> periods) {
        this.periods = periods;
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

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Comparable<Period>, Serializable {
        private static final long serialVersionUID = -5512544207295098365L;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate finishDate;
        private String description;
        private String title;

        public Period() {
        }

        public Period(LocalDate startDate, LocalDate finishDate, String description) {
            this(startDate, finishDate, description, null);
        }

        public Period(LocalDate startDate, LocalDate finishDate, String description, String title) {
            this.startDate = startDate;
            this.finishDate = finishDate;
            this.description = description;
            this.title = title;
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
            return "Period{" + "startDate=" + startDate + ", finishDate=" + finishDate + ", title='" + title + '\'' + ", " + "description='" + description + '\'' + '}';
        }
    }
}
