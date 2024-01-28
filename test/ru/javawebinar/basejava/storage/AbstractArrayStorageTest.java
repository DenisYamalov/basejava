package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final Resume RESUME1 = new Resume("uuid1");
    private static final Resume RESUME2 = new Resume("uuid2");
    private static final Resume RESUME3 = new Resume("uuid3");
    private static final Resume RESUME4 = new Resume("uuid4");

    public AbstractArrayStorageTest(Storage storage) {
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
        assertSize(4);
    }

    @Test
    void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME1));
    }

    @Test
    void storageOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (Exception e) {
            fail("Storage fulfilled before limit");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }

    @Test
    void get() {
        assertGet(RESUME1);
        assertGet(RESUME2);
        assertGet(RESUME3);
    }

    @Test
    void delete() {
        storage.delete(RESUME1.getUuid());
        assertFalse(Arrays.asList(storage.getAll()).contains(RESUME1));
        assertSize(2);
        assertThrowsNotExist(RESUME1.getUuid());
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete("dummy"));
    }

    @Test
    void getAll() {
        Resume[] resumes = {RESUME1, RESUME2, RESUME3};
        assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    void size() {
        assertSize(3);
    }

    @Test
    void update() {
        Resume resume = new Resume(RESUME1.getUuid());
        storage.update(resume);
        assertGet(resume);
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("123")));
    }

    @Test
    public void getNotExist() {
        assertThrowsNotExist("dummy");
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