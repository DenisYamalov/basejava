package ru.javawebinar.basejava.storage.serialstrategy;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.LocalDateAdapter;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class DataStreamStrategy implements SerializationStrategy {
    private static final LocalDateAdapter localDateAdapter = new LocalDateAdapter();

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            writeWithException(r.getContacts().entrySet(), dos, (t) -> {
                dos.writeUTF(t.getKey().name());
                dos.writeUTF(t.getValue());
            });

            writeWithException(r.getSections().entrySet(), dos, (sectionEntry) -> {
                SectionType sectionType = sectionEntry.getKey();
                dos.writeUTF(sectionType.toString());

                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) sectionEntry.getValue()).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeWithException(((ListSection) sectionEntry.getValue()).getList(), dos, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeWithException(((CompanySection) sectionEntry.getValue()).getCompanies(), dos,
                                           (company) -> {
                            dos.writeUTF(company.getHomepage().getName());
                            dos.writeUTF(company.getHomepage().getUrl());

                            writeWithException(company.getPeriods(), dos, (period) -> {
                                dos.writeUTF(localDateAdapter.marshal(period.getStartDate()));
                                dos.writeUTF(localDateAdapter.marshal(period.getFinishDate()));
                                dos.writeUTF(period.getDescription());
                                String title = period.getTitle();
                                dos.writeUTF(title == null ? "" : title);
                            });
                        });
                        break;
                    default:
                        break;
                }
            });
        }
    }

    @FunctionalInterface
    private interface CustomConsumer<T> {
        void accept(T t) throws IOException;
    }

    private <T> void writeWithException(Collection<T> collection,
                                        DataOutputStream dos,
                                        CustomConsumer<T> customConsumer) throws IOException {
        Objects.requireNonNull(customConsumer);
        dos.writeInt(collection.size());
        for (T t : collection) {
            customConsumer.accept(t);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);

            readWithException(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            readWithException(dis, () -> {
                Section section;
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        section = new TextSection(dis.readUTF());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        section = new ListSection(readAndWriteCollectionWithException(dis, dis::readUTF));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Company> companies = readAndWriteCollectionWithException(dis, () -> {
                            String companyName = dis.readUTF();
                            String website = dis.readUTF();

                            List<Company.Period> periods = readAndWriteCollectionWithException(dis, () -> {
                                LocalDate startDate = localDateAdapter.unmarshal(dis.readUTF());
                                LocalDate finishDate = localDateAdapter.unmarshal(dis.readUTF());
                                String description = dis.readUTF();
                                String title = dis.readUTF();
                                return new Company.Period(startDate, finishDate, description, title.isEmpty() ? null
                                        : title);
                            });
                            return new Company(companyName, website, periods);
                        });
                        section = new CompanySection(companies);
                        break;
                    default:
                        section = null;
                        break;
                }
                resume.setSection(sectionType, section);
            });
            return resume;
        }
    }

    @FunctionalInterface
    private interface CustomWriter {
        void accept() throws IOException;
    }

    private void readWithException(DataInputStream dis, CustomWriter customWriter) throws IOException {
        Objects.requireNonNull(customWriter);
        int mapSize = dis.readInt();
        for (int i = 0; i < mapSize; i++) {
            customWriter.accept();
        }
    }

    @FunctionalInterface
    private interface CustomSupplier<T> {
        T accept() throws IOException;
    }

    private <T> List<T> readAndWriteCollectionWithException(DataInputStream dis,
                                                            CustomSupplier<T> customSupplier) throws IOException {
        Objects.requireNonNull(customSupplier);
        int collectionSize = dis.readInt();
        final List<T> collection = new ArrayList<>();
        for (int i = 0; i < collectionSize; i++) {
            collection.add(customSupplier.accept());
        }
        return collection;
    }
}
