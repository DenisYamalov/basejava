package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Serializable {
    private static long serialVersionUID = -8104866515450132313L;
    // Unique identifier
    @XmlAttribute(name = "uuid")
    private String uuid;
    @XmlAttribute(name = "fullName")
    private String fullName;
    private final Map<ContactType, String> contactsMap = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sectionMap = new EnumMap<>(SectionType.class);

    public Resume() {
    }

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

    public Map<ContactType, String> getContacts() {
        return contactsMap;
    }

    public Map<SectionType, Section> getSections() {
        return sectionMap;
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
        return Objects.equals(uuid, resume.uuid) && Objects.equals(fullName, resume.fullName) && Objects.equals(contactsMap, resume.contactsMap) && Objects.equals(sectionMap, resume.sectionMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contactsMap, sectionMap);
    }

    @Override
    public String toString() {
        return "Resume{" + "uuid='" + uuid + '\'' + ", fullName='" + fullName + '\'' + ", contactsMap=" + contactsMap + ", sectionMap=" + sectionMap + '}';
    }

}