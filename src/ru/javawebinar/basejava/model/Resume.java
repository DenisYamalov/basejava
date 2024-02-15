package ru.javawebinar.basejava.model;


import ru.javawebinar.basejava.exception.ResumeException;

import java.time.LocalDate;
import java.util.*;

/**
 * Initial resume class
 */
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume)) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) && Objects.equals(fullName, resume.fullName) && Objects.equals(contactsMap, resume.contactsMap) && Objects.equals(sectionMap, resume.sectionMap);
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
        private final String companyName;
        private final String website;
        private final Period period;

        public CompanySection(String companyName, String website, Period period) {
            this.companyName = companyName;
            this.website = website;
            this.period = period;
        }

        public String getCompanyName() {
            return companyName;
        }

        public String getWebsite() {
            return website;
        }

        public Period getPeriod() {
            return period;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CompanySection)) return false;
            CompanySection that = (CompanySection) o;
            return Objects.equals(companyName, that.companyName) && Objects.equals(website, that.website) && Objects.equals(period, that.period);
        }

        @Override
        public int hashCode() {
            return Objects.hash(companyName, website, period);
        }

        @Override
        public String toString() {
            return "CompanySection{" +
                    "companyName='" + companyName + '\'' +
                    ", website='" + website + '\'' +
                    ", period=" + period +
                    "} " + super.toString();
        }
    }

    private class Period {
        private final LocalDate startDate;
        private LocalDate finishDate;
        private final String title;
        private String description;

        public Period(LocalDate startDate, LocalDate finishDate, String title, String description) {
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
            return "Period{" +
                    "startDate=" + startDate +
                    ", finishDate=" + finishDate +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    public class StorySection extends Section {
        private List<String> stories = new ArrayList<>();

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
