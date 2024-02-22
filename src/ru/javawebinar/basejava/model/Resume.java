package ru.javawebinar.basejava.model;

import java.util.*;

public class Resume {
    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final Map<ContactType, String> contactsMap = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sectionMap = new EnumMap<>(SectionType.class);

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

    public void setContact(ContactType contactType, String contact) {
        contactsMap.put(contactType, contact);
    }

    public String getContact(ContactType contactType) {
        return contactsMap.get(contactType);
    }

    public void removeContact(ContactType contactType) {
        contactsMap.remove(contactType);
    }

    public void setSection(SectionType sectionType, Section section) {
        sectionMap.put(sectionType, section);
    }

    public Section getSection(SectionType sectionType) {
        return sectionMap.get(sectionType);
    }

    public void removeSection(SectionType sectionType) {
        sectionMap.remove(sectionType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resume)) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) && Objects.equals(fullName, resume.fullName)
                && Objects.equals(contactsMap, resume.contactsMap)
                && Objects.equals(sectionMap, resume.sectionMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contactsMap, sectionMap);
    }

    @Override
    public String toString() {
        return "Resume{"
                + "uuid='" + uuid + '\''
                + ", fullName='" + fullName + '\''
                + ", contactsMap=" + contactsMap
                + ", sectionMap=" + sectionMap + '}';
    }

}
