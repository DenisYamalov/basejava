package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    protected static final int INITIAL_SIZE = 3;
    protected final Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";
    protected static final String UUID_NOT_EXIST = "dummy";
    protected static final Resume RESUME1 = new Resume(UUID_1, "Mike Mires");
    protected static final Resume RESUME2 = new Resume(UUID_2, "Annie Goodman");
    protected static final Resume RESUME3 = new Resume(UUID_3, "John Travolta");
    protected static final Resume RESUME4 = new Resume(UUID_4, "Dan Brown");

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(RESUME1);
        storage.save(RESUME2);
        storage.save(RESUME3);
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        assertArrayEquals(new Resume[0], storage.getAll());
    }

    @Test
    void save() {
        storage.save(RESUME4);
        assertGet(RESUME4);
        assertSize(INITIAL_SIZE + 1);
    }

    @Test
    void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME1));
    }

    @Test
    void get() {
        assertGet(RESUME1);
        assertGet(RESUME2);
        assertGet(RESUME3);
    }

    @Test
    void delete() {
        storage.delete(UUID_1);
        assertFalse(Arrays.asList(storage.getAll()).contains(RESUME1));
        assertSize(INITIAL_SIZE - 1);
        assertThrowsNotExist(UUID_1);
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_NOT_EXIST));
    }

    @Test
    void getAll() {
        Resume[] resumes = {RESUME1, RESUME2, RESUME3};
        Resume[] testingArray = storage.getAll();
        Arrays.sort(testingArray);
        assertArrayEquals(resumes, testingArray);
    }

    @Test
    void size() {
        assertSize(INITIAL_SIZE);
    }

    @Test
    void update() {
        Resume resume = new Resume(UUID_1, "Steven Seagal");
        storage.update(resume);
        assertGet(resume);
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume(UUID_NOT_EXIST, "")));
    }

    @Test
    public void getNotExist() {
        assertThrowsNotExist(UUID_NOT_EXIST);
    }

    private void assertThrowsNotExist(String uuid) {
        assertThrows(NotExistStorageException.class, () -> storage.get(uuid));
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}