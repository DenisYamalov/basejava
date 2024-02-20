package ru.javawebinar.basejava.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ResumeException;
import ru.javawebinar.basejava.storage.AbstractStorage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListSectionTest {
    static final ListSection achievements = new ListSection();
    static final String ACHIEVEMENT_1 = "Организация команды и успешная реализация Java проектов для " +
            "сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга " +
            "показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + "
            + "Vaadin проект для комплексных DIY смет";
    static final String ACHIEVEMENT_2 = "С 2013 года: разработка проектов \"Разработка Web приложения\"," +
            "\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP)" +
            ". Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 " +
            "выпускников.";
    static final String ACHIEVEMENT_3 = "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.";

    @BeforeEach
    void setUp() {
        achievements.storage.clear();
        achievements.add(ACHIEVEMENT_1);
        achievements.add(ACHIEVEMENT_2);
    }

    @Test
    void add() {
        achievements.add(ACHIEVEMENT_3);
        assertSize(3);
        assertTrue(achievements.storage.contains(ACHIEVEMENT_3));
    }

    @Test
    void addExist() {
        assertThrows(ResumeException.class, () -> achievements.add(ACHIEVEMENT_2));
    }

    @Test
    void testUpdate() {
    }

    @Test
    void remove() {
        achievements.remove(ACHIEVEMENT_2);
        assertSize(1);
        assertThrows(ResumeException.class, () -> achievements.remove(ACHIEVEMENT_2));
        assertFalse(achievements.storage.contains(ACHIEVEMENT_2));
    }

    @Test
    void testGetAll() {
        List<String> testList = List.of(ACHIEVEMENT_1, ACHIEVEMENT_2);
        assertSize(2);
        assertEquals(testList, achievements.getAll());
    }

    private static void assertSize(int size) {
        assertEquals(size, achievements.storage.size());
    }
}