package ru.javawebinar.basejava.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import ru.javawebinar.basejava.exception.ResumeException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.javawebinar.basejava.model.TextSectionTest.OBJECTIVE;
import static ru.javawebinar.basejava.model.TextSectionTest.PERSONAL;

@Suite
@SelectClasses({TextSectionTest.class, ListSectionTest.class, CompanySectionTest.class})
class ResumeDataTest {
    private static final String FULL_NAME = "Григорий Кислин";
    private static final String UUID = "uuid1";
    private static final String PHONE = "+7(921) 855-0482";
    private static final String NEW_NUMBER = "123";
    private static final String SKYPE = "skype:grigory.kislin";
    private static final String EMAIL = "gkislin@yandex.ru";
    private static final String LINKED_IN = "https://www.linkedin.com/in/gkislin";
    private static final String GIT_HUB = "https://github.com/gkislin";
    private static final String STACKOVERFLOW = "https://stackoverflow.com/users/548473";
    private static final String HOMEPAGE = "http://gkislin.ru/";
    private final Resume resume = new Resume(UUID, FULL_NAME);
    private final CompanySection EXPERITNCE_SECTION = CompanySectionTest.experience;
    private final TextSection OBJECTIVE_SECTION = new TextSection(OBJECTIVE);
    private final TextSection PERSONAL_SECTION = new TextSection(PERSONAL);

    @BeforeEach
    void setUp() {
        resume.addContact(ContactType.PHONE, PHONE);
        resume.addContact(ContactType.SKYPE, SKYPE);
        resume.addContact(ContactType.EMAIL, EMAIL);
        resume.addContact(ContactType.LINKED_IN, LINKED_IN);
        resume.addContact(ContactType.GIT_HUB, GIT_HUB);
        resume.addContact(ContactType.STACKOVERFLOW, STACKOVERFLOW);
        resume.addContact(ContactType.HOMEPAGE, HOMEPAGE);

        resume.addSection(SectionType.OBJECTIVE, OBJECTIVE_SECTION);
        resume.addSection(SectionType.PERSONAL, PERSONAL_SECTION);
    }

    @Test
    void getUuid() {
        assertEquals(UUID, resume.getUuid());
    }

    @Test
    void getFullName() {
        assertEquals(FULL_NAME, resume.getFullName());
    }

    @Test
    void addContact() {
        ContactType phone = ContactType.PHONE;
        assertContactThrowsExist(phone, PHONE);
        doRemoveContact(phone);
        resume.addContact(phone, NEW_NUMBER);
        assertGetContact(NEW_NUMBER, phone);
    }

    @Test
    void updateContact() {
        resume.updateContact(ContactType.PHONE, NEW_NUMBER);
        assertGetContact(NEW_NUMBER, ContactType.PHONE);
    }

    @Test
    void getContact() {
        assertGetContact(PHONE, ContactType.PHONE);
        assertGetContact(SKYPE, ContactType.SKYPE);
        assertGetContact(EMAIL, ContactType.EMAIL);
        assertGetContact(LINKED_IN, ContactType.LINKED_IN);
        assertGetContact(GIT_HUB, ContactType.GIT_HUB);
        assertGetContact(STACKOVERFLOW, ContactType.STACKOVERFLOW);
        assertGetContact(HOMEPAGE, ContactType.HOMEPAGE);
    }

    @Test
    void removeContact() {
        doRemoveContact(ContactType.PHONE);
        assertContactThrowsNotExist(ContactType.PHONE);
    }

    void doRemoveContact(ContactType contactType) {
        resume.removeContact(contactType);
    }

    @Test
    void addSection() {
        resume.addSection(SectionType.EXPERIENCE, EXPERITNCE_SECTION);
        assertEquals(EXPERITNCE_SECTION, resume.getSection(SectionType.EXPERIENCE));
    }

    @Test
    void updateSection() {
    }

    @Test
    void getSection() {
        assertEquals(OBJECTIVE_SECTION, resume.getSection(SectionType.OBJECTIVE));
        assertEquals(PERSONAL_SECTION, resume.getSection(SectionType.PERSONAL));
    }

    @Test
    void removeSection() {
        resume.removeSection(SectionType.OBJECTIVE);
        assertThrows(ResumeException.class,()->resume.getSection(SectionType.OBJECTIVE));
    }

    void assertContactThrowsExist(ContactType contactType, String contact) {
        assertThrows(ResumeException.class, () -> resume.addContact(contactType, contact));
    }

    void assertContactThrowsNotExist(ContactType contactType) {
        assertThrows(ResumeException.class, () -> resume.getContact(contactType));
    }

    void assertGetContact(String contact, ContactType contactType) {
        assertEquals(contact, resume.getContact(contactType));
    }
}