package ru.javawebinar.basejava.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextSectionTest {
    static final String OBJECTIVE = "Ведущий стажировок и корпоративного обучения по Java Web и " +
            "Enterprise технологиям";
    static final String PERSONAL = "Аналитический склад ума, сильная логика,креативность, инициативность." +
            "Пурист кода и архитектуры.";
    TextSection OBJECTIVE_SECTION;
    TextSection PERSONAL_SECTION;

    @BeforeEach
    void setUp() {
        OBJECTIVE_SECTION = new TextSection(OBJECTIVE);
        PERSONAL_SECTION = new TextSection(PERSONAL);
    }

    @Test
    void getText() {
        assertEquals(OBJECTIVE, OBJECTIVE_SECTION.getText());
        assertEquals(PERSONAL, PERSONAL_SECTION.getText());
    }
}