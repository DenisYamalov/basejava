package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.serialstrategy.SerializationStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private SerializationStrategy serializationStrategy;

    protected PathStorage(String dir, SerializationStrategy serializationStrategy) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.serializationStrategy = serializationStrategy;
    }

    @Override
    public void clear() {
        getPathStream("Path delete error").forEach(this::doDelete);
    }


    private Stream<Path> getPathStream(String exceptionMessage) {
        Supplier<Stream<Path>> streamSupplier = () -> {
            try {
                return Files.list(directory);
            } catch (IOException e) {
                throw new StorageException(exceptionMessage, e);
            }
        };
        return streamSupplier.get();
    }

    public int size() {
        return (int) getPathStream("Directory read error").filter(p -> !Files.isDirectory(p)).count();
    }

    @Override
    protected List<Resume> getAll() {
        return getPathStream("Directory read error").filter(p -> !Files.isDirectory(p)).map(this::doGet).collect(Collectors.toList());
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(Files.newInputStream(path));
        } catch (IOException e) {
            throw new StorageException("File read error", getFileName(path), e);
        }
    }

    protected Resume doRead(InputStream is) throws IOException {
        return serializationStrategy.doRead(is);
    }

    @Override
    protected void doSave(Path path, Resume r) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + path.toAbsolutePath(), getFileName(path), e);
        }
        doUpdate(path, r);
    }

    protected void doWrite(Resume r, OutputStream os) throws IOException {
        serializationStrategy.doWrite(r, os);
    }

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
            throw new StorageException("Path delete error", getFileName(path), e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    public void setSerializationStrategy(SerializationStrategy serializationStrategy) {
        this.serializationStrategy = serializationStrategy;
    }

    private static String getFileName(Path path) {
        return path.getFileName().toString();
    }
}
