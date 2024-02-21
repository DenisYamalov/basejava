package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
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
//        addEducation(resume);
    }

    private static void addContacts(Resume resume) {
        resume.setContact("PHONE", "+7(921) 855-0482 ");
        resume.setContact("SKYPE", "skype:grigory.kislin");
        resume.setContact("EMAIL", "gkislin@yandex.ru");
        resume.setContact("LINKED_IN", "https://www.linkedin.com/in/gkislin");
        resume.setContact("GIT_HUB", "https://github.com/gkislin");
        resume.setContact("STACKOVERFLOW", "https://stackoverflow.com/users/548473");
        resume.setContact("HOMEPAGE", "http://gkislin.ru/");
        LOGGER.info("Contacts added: " + resume);
    }

    private static void addTextSections(Resume resume) {
        resume.setSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного " +
                                                                                "обучения" + " по Java Web и " +
                                                                                "Enterprise технологиям"));
        resume.setSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, " +
                                                                               "креативность, инициативность. Пурист "
                                                                               + "кода и архитектуры."));

        LOGGER.info("Text sections added: " + resume);
    }

    private static void addAchievements(Resume resume) {
        ListSection achievements = new ListSection();
        List<String> achievementsList = achievements.getList();
        achievementsList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: " +
                                      "приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга " +
                                      "показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, " +
                                      "многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievementsList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", " +
                                      "\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы " +
                                      "(JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн " +
                                      "стажировок и ведение проектов. Более 3500 выпускников.");
        achievementsList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами " +
                                      "Wrike" + ". Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, " +
                                      "Zendesk.");
        achievementsList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. " +
                                      "Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления " +
                                      "окружением на стеке: " + "Scala/Play/Anorm/JQuery. Разработка SSO " +
                                      "аутентификации и авторизации различных ERP модулей, " + "интеграция CIFS/SMB " +
                                      "java сервера.");
        achievementsList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, " +
                                      "Spring," + " Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для " +
                                      "алгоритмического трейдинга.");
        achievementsList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных " +
                                      "сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики " +
                                      "сервисов и информации о состоянии через систему мониторинга Nagios. Реализация" +
                                      " онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ " +
                                      "Django).");
        achievementsList.add("Реализация протоколов по приему платежей всех основных платежных системы России " +
                                      "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        resume.setSection(SectionType.ACHIEVEMENT, achievements);

        LOGGER.info("Achievements added: " + resume);
    }

    private static void addQualifications(Resume resume) {
        ListSection qualifications = new ListSection();
        List<String> qualificationsList = qualifications.getList();
        qualificationsList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2 ");
        qualificationsList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualificationsList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, "
                                        + "SQLite, MS SQL, HSQLDB");
        qualificationsList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualificationsList.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualificationsList.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring " +
                                        "(MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, " +
                                        "GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse " +
                                        "SWT, JUnit, Selenium (htmlelements).");
        qualificationsList.add("Python: Django.");
        qualificationsList.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js ");
        qualificationsList.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualificationsList.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, " +
                                        "SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, " +
                                        "ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualificationsList.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualificationsList.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, " +
                                        "Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        qualificationsList.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, " +
                                        "архитектурных шаблонов, UML, функционального программирования ");
        qualificationsList.add("Родной русский, английский \"upper intermediate\"");


        resume.setSection(SectionType.QUALIFICATIONS, qualifications);

        LOGGER.info("Qualifications added: " + resume);
    }

    private static void addExperience(Resume resume) {
        CompanySection experience1 = new CompanySection("Java Online Projects","http://javaops.ru/");
        experience1.getPeriods().add(new Period(LocalDate.of(2013, Month.OCTOBER, 1), LocalDate.now(),"Создание, организация и проведение Java онлайн проектов и стажировок.", "Автор проекта."));

        CompanySection experience2 = new CompanySection("Wrike","https://www.wrike.com");
        experience2.getPeriods().add(new Period(LocalDate.of(2014, Month.OCTOBER, 1), LocalDate.of(2016, Month.JANUARY
                , 1), "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, " + "Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.", "Старший разработчик (backend)"));

        resume.setSection(SectionType.EXPERIENCE, experience1);
        LOGGER.info("Experience added: " + resume);
        resume.setSection(SectionType.EXPERIENCE,experience2);
        LOGGER.info("Experience added: " + resume);
    }

//    private static void addEducation(Resume resume) {
//        CompanySection education = new CompanySection();
//
//        education.add(new Period(LocalDate.of(2013, Month.MARCH, 1), LocalDate.of(2013, Month.APRIL, 1),
//                                 "Coursera", "https://www.coursera.org/course/progfun",
//                                 "'Functional " + "Programming Principles in Scala' by Martin Odersky"));
//        education.add(new Period(LocalDate.of(2011, Month.MARCH, 1), LocalDate.of(2011, Month.APRIL, 1),
//                                 "Luxoft", "http://www.luxoft-training.ru/training/catalog/course" +
//                                                      ".html?ID=22366",
//                                 "Курс 'Объектно-ориентированный анализ ИС. " + "Концептуальное " +
//                                                      "моделирование на UML.'"));
//
//        resume.setSection(SectionType.EDUCATION, education);
//
//        LOGGER.info("Education added: " + resume);
//    }
}
