package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.TextSection;
import ru.javawebinar.basejava.util.JsonParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.javawebinar.basejava.storage.AbstractStorageTest.RESUME1;

public class JsonParserTest {
    @Test
    public void testResume() throws Exception {
        String json = JsonParser.write(RESUME1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        assertEquals(RESUME1, resume);
    }

    @Test
    public void write() throws Exception {
        Section section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1, Section.class);
        System.out.println(json);
        Section section2 = JsonParser.read(json, Section.class);
        assertEquals(section1, section2);
    }
}
