package ru.javawebinar.basejava.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ResumeException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompanySectionTest {
    static final CompanySection experience = new CompanySection();
    static final Period JAVA_ONLINE = new Period(LocalDate.of(2013, Month.OCTOBER, 1), LocalDate.now(),
            "Java Online " + "Projects", "http://javaops.ru/", "Создание, " +
            "организация и проведение Java онлайн " + "проектов и " +
            "стажировок.", "Автор проекта.");
    static final Period WRIKE = new Period(LocalDate.of(2014, Month.OCTOBER, 1), LocalDate.of(2016, Month.JANUARY
            , 1), "Wrike", "https://www.wrike.com/",
            "Проектирование и разработка онлайн платформы управления " +
                    "проектами Wrike (Java 8 API, Maven, " + "Spring, MyBatis, " +
                    "Guava, Vaadin, PostgreSQL, Redis). Двухфакторная " +
                    "аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
            "Старший разработчик (backend)");
    static final Period RIT_CENTER = new Period(LocalDate.of(2012, Month.APRIL, 1),
            LocalDate.of(2014, Month.OCTOBER, 1),
            "RIT Center", "",
            "Организация процесса разработки системы ERP для разных окружений: релизная политика, " +
                    "версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway)," +
                    "конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части " +
                    "системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего " +
                    "назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online " +
                    "редактирование из браузера документов MS Office. Maven + plugin development, Ant," +
                    " Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita," +
                    " Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python", "Java архитектор");

    @BeforeEach
    void setUp() {
        experience.storage.clear();
        experience.add(JAVA_ONLINE);
        experience.add(WRIKE);
    }

    @Test
    void add() {
        experience.add(RIT_CENTER);
        assertSize(3);
        assertTrue(experience.storage.contains(RIT_CENTER));
    }
    @Test
    void addExist() {
        assertThrows(ResumeException.class, () -> experience.add(WRIKE));
    }

    @Test
    void update() {
    }

    @Test
    void remove() {
        experience.remove(WRIKE);
        assertSize(1);
        assertThrows(ResumeException.class, () -> experience.remove(WRIKE));
        assertFalse(experience.storage.contains(WRIKE));
    }

    @Test
    void getAll() {
        List<Period> testList = List.of(JAVA_ONLINE, WRIKE);
        assertSize(2);
        assertEquals(testList, experience.getAll());
    }
    private static void assertSize(int size) {
        assertEquals(size, experience.storage.size());
    }
}