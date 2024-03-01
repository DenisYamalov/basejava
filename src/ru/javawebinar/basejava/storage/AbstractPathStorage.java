package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try (Stream<Path> pathStream = Files.list(directory)) {
            pathStream.forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    public int size() {
        try (Stream<Path> pathStream = Files.list(directory)) {
            return (int) pathStream.count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }

    @Override
    protected List<Resume> getAll() {
        try (Stream<Path> pathStream = Files.list(directory)) {
            return pathStream.map(this::doGet).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(Files.newInputStream(path));
        } catch (IOException e) {
            throw new StorageException("File read error", path.getFileName().toString(), e);
        }
    }

    protected abstract Resume doRead(InputStream is) throws IOException;

    @Override
    protected void doSave(Path path, Resume r) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + path.toAbsolutePath(),
                                       path.getFileName().toString(), e);
        }
        doUpdate(path, r);
    }

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    @Override
    protected void doUpdate(Path path, Resume r) {
        try {
            doWrite(r, Files.newOutputStream(path));
        } catch (IOException e) {
            throw new StorageException("File write error", r.getUuid(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path searchKey) {
        return Files.exists(directory);
    }
}
