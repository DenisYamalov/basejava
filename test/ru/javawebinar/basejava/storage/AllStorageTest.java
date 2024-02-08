package ru.javawebinar.basejava.storage;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ListStorageTest.class,
        ArrayStorageTest.class,
        MapResumeStorage.class,
        MapStorageTest.class,
        SortedArrayStorageTest.class})
public class AllStorageTest {
}
