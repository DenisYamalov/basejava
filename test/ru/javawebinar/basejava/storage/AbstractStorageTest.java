package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    protected static final int INITIAL_SIZE = 3;
    protected final Storage storage;
    protected static final String UUID_1 = UUID.randomUUID().toString();
    protected static final String UUID_2 = UUID.randomUUID().toString();
    protected static final String UUID_3 = UUID.randomUUID().toString();
    protected static final String UUID_4 = UUID.randomUUID().toString();
    protected static final String UUID_NOT_EXIST = "dummy";
    protected static final Resume RESUME1 = ResumeTestData.fulfillResume(UUID_1, "Mike Mires");
    protected static final Resume RESUME2 = ResumeTestData.fulfillResume(UUID_2, "Annie Goodman");
    protected static final Resume RESUME3 = ResumeTestData.fulfillResume(UUID_3, "John Travolta");
    protected static final Resume RESUME4 = ResumeTestData.fulfillResume(UUID_4, "Dan Brown");

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
        List<Resume> resumeList = storage.getAllSorted();
        assertArrayEquals(new Resume[0], resumeList.toArray());
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
        List<Resume> resumeList = storage.getAllSorted();
        assertFalse(resumeList.contains(RESUME1));
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
        Arrays.sort(resumes, Storage.RESUME_COMPARATOR);
        Resume[] testingArray = storage.getAllSorted().toArray(Resume[]::new);
        assertArrayEquals(resumes, testingArray);
    }

    @Test
    void size() {
        assertSize(INITIAL_SIZE);
    }

    @Test
    void update() {
        Resume resume = ResumeTestData.fulfillResume(UUID_1, "Steven Seagal");
        storage.update(resume);
        assertGet(resume);
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(ResumeTestData.fulfillResume(UUID_NOT_EXIST
                , "")));
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