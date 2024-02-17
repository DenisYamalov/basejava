package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;

import java.time.LocalDate;
import java.time.Month;
import java.util.logging.Logger;

public class ResumeTestData {
    private static final Logger LOGGER = Logger.getLogger(ResumeTestData.class.getName());

    public static void main(String[] args) {
        Resume resume = new Resume("Григорий Кислин");

        addContacts(resume);
        addTextSections(resume);
        addAchievements(resume);
        addQualifications(resume);
        addExperience(resume);
        addEducation(resume);
    }

    private static void addContacts(Resume resume) {
        resume.addContact(ContactType.PHONE, "+7(921) 855-0482 ");
        resume.addContact(ContactType.SKYPE, "skype:grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKED_IN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GIT_HUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.addContact(ContactType.HOMEPAGE, "http://gkislin.ru/");
        LOGGER.info("Contacts added: " + resume);
    }

    private static void addTextSections(Resume resume) {
        resume.addSection(SectionType.OBJECTIVE, resume.new TextSection("Ведущий стажировок и корпоративного " +
                                                                                "обучения" + " по Java Web и " +
                                                                                "Enterprise технологиям"));
        resume.addSection(SectionType.PERSONAL, resume.new TextSection("Аналитический склад ума, сильная логика, " +
                                                                               "креативность, инициативность. Пурист "
                                                                               + "кода и архитектуры."));

        LOGGER.info("Text sections added: " + resume);
    }

    private static void addAchievements(Resume resume) {
        Resume.StorySection achievements = resume.new StorySection();
        achievements.addStory("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                                      "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга " +
                                      "показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, " +
                                      "многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievements.addStory("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                                      "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы " +
                                      "(JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн " +
                                      "стажировок и ведение проектов. Более 3500 выпускников.");
        achievements.addStory("Реализация двухфакторной аутентификации для онлайн платформы управления проектами " +
                                      "Wrike" + ". Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, " +
                                      "Zendesk.");
        achievements.addStory("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                                      "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления " +
                                      "окружением на стеке: " + "Scala/Play/Anorm/JQuery. Разработка SSO " +
                                      "аутентификации и авторизации различных ERP модулей, " + "интеграция CIFS/SMB " +
                                      "java сервера.");
        achievements.addStory("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, " +
                                      "Spring," + " Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для " +
                                      "алгоритмического трейдинга.");
        achievements.addStory("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных " +
                                      "сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики " +
                                      "сервисов и информации о состоянии через систему мониторинга Nagios. Реализация" +
                                      " онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ " +
                                      "Django).");
        achievements.addStory("Реализация протоколов по приему платежей всех основных платежных системы России " +
                                      "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        resume.addSection(SectionType.ACHIEVEMENT, achievements);

        LOGGER.info("Achievements added: " + resume);
    }

    private static void addQualifications(Resume resume) {
        Resume.StorySection qualifications = resume.new StorySection();
        qualifications.addStory("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 ");
        qualifications.addStory("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.addStory("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, "
                                        + "SQLite, MS SQL, HSQLDB");
        qualifications.addStory("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualifications.addStory("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualifications.addStory("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring " +
                                        "(MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, " +
                                        "GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse " +
                                        "SWT, JUnit, Selenium (htmlelements).");
        qualifications.addStory("Python: Django.");
        qualifications.addStory("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js ");
        qualifications.addStory("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.addStory("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, " +
                                        "SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, " +
                                        "ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualifications.addStory("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualifications.addStory("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, " +
                                        "Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        qualifications.addStory("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, " +
                                        "архитектурных шаблонов, UML, функционального программирования ");
        qualifications.addStory("Родной русский, английский \"upper intermediate\"");


        resume.addSection(SectionType.QUALIFICATIONS, qualifications);

        LOGGER.info("Qualifications added: " + resume);
    }

    private static void addExperience(Resume resume) {
        Resume.CompanySection experience = resume.new CompanySection();

        experience.addPeriod(resume.new Period(LocalDate.of(2013, Month.OCTOBER, 1), LocalDate.now(),
                                               "Java Online " + "Projects", "http://javaops.ru/", "Создание, " +
                                                       "организация и проведение Java онлайн " + "проектов и " +
                                                       "стажировок.", "Автор проекта."));
        experience.addPeriod(resume.new Period(LocalDate.of(2014, Month.OCTOBER, 1), LocalDate.of(2016, Month.JANUARY
                , 1), "Wrike", "https://www.wrike.com/",
                                               "Проектирование и разработка онлайн платформы управления " +
                                                       "проектами Wrike (Java 8 API, Maven, " + "Spring, MyBatis, " +
                                                       "Guava, Vaadin, PostgreSQL, Redis). Двухфакторная " +
                                                       "аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                                               "Старший разработчик (backend)"));

        resume.addSection(SectionType.EXPERIENCE, experience);

        LOGGER.info("Experience added: " + resume);
    }

    private static void addEducation(Resume resume) {
        Resume.CompanySection education = resume.new CompanySection();

        education.addPeriod(resume.new Period(LocalDate.of(2013, Month.MARCH, 1), LocalDate.of(2013, Month.APRIL, 1),
                                              "Coursera", "https://www.coursera.org/course/progfun",
                                              "'Functional " + "Programming Principles in Scala' by Martin Odersky"));
        education.addPeriod(resume.new Period(LocalDate.of(2011, Month.MARCH, 1), LocalDate.of(2011, Month.APRIL, 1),
                                              "Luxoft", "http://www.luxoft-training.ru/training/catalog/course" +
                                                      ".html?ID=22366",
                                              "Курс 'Объектно-ориентированный анализ ИС. " + "Концептуальное " +
                                                      "моделирование на UML.'"));

        resume.addSection(SectionType.EDUCATION, education);

        LOGGER.info("Education added: " + resume);
    }
}
