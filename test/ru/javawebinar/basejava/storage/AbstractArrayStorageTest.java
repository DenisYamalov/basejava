package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.StorageException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    void storageOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(ResumeTestData.fulfillResume("uuid" + i, "Name" + i));
            }
        } catch (Exception e) {
            fail("Storage fulfilled before limit");
        }
        assertThrows(StorageException.class, () -> storage.save(ResumeTestData.fulfillResume("", "")));
    }
}