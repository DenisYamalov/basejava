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
@SelectClasses(TextSectionTest.class)
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


    private static final String ACHIEVEMENT_1 = "Организация команды и успешная реализация Java проектов для " +
            "сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга " +
            "показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + "
            + "Vaadin проект для комплексных DIY смет";
    private static final String ACHIEVEMENT_2 = "С 2013 года: разработка проектов \"Разработка Web приложения\"," +
            "\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP)" +
            ". Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 " +
            "выпускников.";
    private static final String QUALIFICATION_1 = "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, " +
            "WebLogic, WSO2 ";
    private static final String QUALIFICATION_2 = "Version control: Subversion, Git, Mercury, ClearCase, Perforce";


    private final Resume resume = new Resume(UUID, FULL_NAME);
    private final ListSection achievements = new ListSection();
    private final ListSection qualifications = new ListSection();

    @BeforeEach
    void setUp() {
        resume.addContact(ContactType.PHONE, PHONE);
        resume.addContact(ContactType.SKYPE, SKYPE);
        resume.addContact(ContactType.EMAIL, EMAIL);
        resume.addContact(ContactType.LINKED_IN, LINKED_IN);
        resume.addContact(ContactType.GIT_HUB, GIT_HUB);
        resume.addContact(ContactType.STACKOVERFLOW, STACKOVERFLOW);
        resume.addContact(ContactType.HOMEPAGE, HOMEPAGE);

        resume.addSection(SectionType.OBJECTIVE, new TextSection(OBJECTIVE));
        resume.addSection(SectionType.PERSONAL, new TextSection(PERSONAL));
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
        assertThrowsExist(phone, PHONE);
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
        assertThrowsNotExist(ContactType.PHONE);
    }

    void doRemoveContact(ContactType contactType) {
        resume.removeContact(contactType);
    }

    @Test
    void addSection() {
    }

    @Test
    void updateSection() {
    }

    @Test
    void getSection() {
    }

    @Test
    void removeSection() {
    }

    void assertThrowsExist(ContactType contactType, String contact) {
        assertThrows(ResumeException.class, () -> resume.addContact(contactType, contact));
    }

    void assertThrowsNotExist(ContactType contactType) {
        assertThrows(ResumeException.class, () -> resume.getContact(contactType));
    }

    void assertGetContact(String contact, ContactType contactType) {
        assertEquals(contact, resume.getContact(contactType));
    }
}