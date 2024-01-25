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
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void save() {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test
    void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(new Resume(UUID_1)));
    }

    @Test
    void storageOverflow() {
        try {
            for (int i = 4; i <= 10000; i++) {
                storage.save(new Resume("uuid" + i));
            }
        } catch (Exception e) {
            fail("Storage fulfilled before limit");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }

    @Test
    void get() {
        Resume resume = new Resume(UUID_1);
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Test
    void delete() {
        Resume resume = new Resume(UUID_1);
        storage.delete(UUID_1);
        assertFalse(Arrays.asList(storage.getAll()).contains(resume));
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete("dummy"));
    }

    @Test
    void getAll() {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");
        Resume[] resumes = {r1, r2, r3};
        assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
    }

    @Test
    void update() {
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("123")));
    }

    @Test
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }
}