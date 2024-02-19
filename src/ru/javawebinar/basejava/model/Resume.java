package ru.javawebinar.basejava.model;


import ru.javawebinar.basejava.exception.ResumeException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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

}
