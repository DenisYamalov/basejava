package ru.javawebinar.basejava.model;


import ru.javawebinar.basejava.exception.ResumeException;

import java.time.LocalDate;
import java.util.*;

public class Resume {
    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final Map<ContactType, String> contactsMap = new HashMap<>();
    private final Map<SectionType, Section> sectionMap = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void addContact(ContactType contactType, String contact) {
        if (!contactsMap.containsKey(contactType)) {
            contactsMap.put(contactType, contact);
        } else {
            throw new ResumeException("Contact already exists");
        }
    }

    public void updateContact(ContactType contactType, String contact) {
        if (contactsMap.containsKey(contactType)) {
            contactsMap.put(contactType, contact);
        } else {
            throw new ResumeException("Contact not exist");
        }
    }

    public String getContact(ContactType contactType) {
        if (contactsMap.containsKey(contactType)) {
            return contactsMap.get(contactType);
        } else {
            throw new ResumeException("Contact not exist");
        }
    }

    public void removeContact(ContactType contactType) {
        if (contactsMap.containsKey(contactType)) {
            contactsMap.remove(contactType);
        } else {
            throw new ResumeException("Contact not exist");
        }
    }

    public void addSection(SectionType sectionType, Section section) {
        if (!sectionMap.containsKey(sectionType)) {
            sectionMap.put(sectionType, section);
        } else {
            throw new ResumeException("SectionType " + sectionType + " already exist.");
        }
    }

    public void updateSection(SectionType sectionType, Section section) {
        if (sectionMap.containsKey(sectionType)) {
            sectionMap.replace(sectionType, section);
        } else {
            throw new ResumeException("SectionType " + sectionType + " not exist.");
        }
    }

    public Section getSection(SectionType sectionType) {
        if (sectionMap.containsKey(sectionType)) {
            return sectionMap.get(sectionType);
        } else {
            throw new ResumeException("SectionType " + sectionType + " not exist.");
        }
    }

    public void removeSection(SectionType sectionType) {
        if (sectionMap.containsKey(sectionType)) {
            sectionMap.remove(sectionType);
        } else {
            throw new ResumeException("SectionType " + sectionType + " not exist.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume)) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) && Objects.equals(fullName, resume.fullName)
                && Objects.equals(contactsMap, resume.contactsMap) && Objects.equals(sectionMap, resume.sectionMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contactsMap, sectionMap);
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                ", contactsMap=" + contactsMap +
                ", sectionMap=" + sectionMap +
                '}';
    }

    public abstract class Section {

    }

    public class TextSection extends Section {

        private final String text;

        public TextSection(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TextSection)) return false;
            TextSection that = (TextSection) o;
            return Objects.equals(text, that.text);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text);
        }

        @Override
        public String toString() {
            return "TextSection{" +
                    "text='" + text + '\'' +
                    '}';
        }

    }

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

    public class StorySection extends Section {
        private final List<String> stories = new ArrayList<>();

        public void addStory(String story) {
            if (story != null) stories.add(story);
        }

        public List<String> getStories() {
            return new ArrayList<>(stories);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof StorySection)) return false;
            StorySection that = (StorySection) o;
            return Objects.equals(stories, that.stories);
        }

        @Override
        public int hashCode() {
            return Objects.hash(stories);
        }

        @Override
        public String toString() {
            return "StorySection{" +
                    "stories=" + stories +
                    "} " + super.toString();
        }
    }
}
