package ru.javawebinar.basejava.storage.serialstrategy;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.LocalDateAdapter;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

public class DataStreamStrategy implements SerializationStrategy {
    private static final LocalDateAdapter localDateAdapter = new LocalDateAdapter();

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            writeWithExeption(r.getContacts().entrySet(), dos, (dosT, t) -> {
                dosT.writeUTF(t.getKey().name());
                dosT.writeUTF(t.getValue());
            });

            writeWithExeption(r.getSections().entrySet(), dos, (dosT, sectionEntry) -> {
                SectionType sectionType = sectionEntry.getKey();
                dosT.writeUTF(sectionType.toString());

                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dosT.writeUTF(((TextSection) sectionEntry.getValue()).getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeWithExeption(((ListSection) sectionEntry.getValue()).getList(), dosT,
                                          (DataOutputStream::writeUTF));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeWithExeption(((CompanySection) sectionEntry.getValue()).getCompanies(), dosT,
                                          (dos1, company) -> {
                            dos1.writeUTF(company.getHomepage().getName());
                            dos1.writeUTF(company.getHomepage().getUrl());

                            writeWithExeption(company.getPeriods(), dos1, (dos2, period) -> {
                                dos2.writeUTF(localDateAdapter.marshal(period.getStartDate()));
                                dos2.writeUTF(localDateAdapter.marshal(period.getFinishDate()));
                                dos2.writeUTF(period.getDescription());
                                String title = period.getTitle();
                                dos2.writeUTF(title == null ? "" : title);
                            });
                        });
                        break;
                    default:
                        break;
                }
            });
        }
    }

    private interface CustomConsumer<T> {
        void accept(DataOutputStream dos, T t) throws IOException;
    }

    private <T> void writeWithExeption(Collection<T> collection,
                                       DataOutputStream dos,
                                       CustomConsumer<T> customConsumer) throws IOException {
        Objects.requireNonNull(customConsumer);
        dos.writeInt(collection.size());
        for (T t : collection) {
            customConsumer.accept(dos, t);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionsSize = dis.readInt();
            for (int j = 0; j < sectionsSize; j++) {
                Section section;
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        section = new TextSection(dis.readUTF());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        int listSectionSize = dis.readInt();
                        String[] listSectionArray = new String[listSectionSize];
                        for (int k = 0; k < listSectionSize; k++) {
                            listSectionArray[k] = dis.readUTF();
                        }
                        section = new ListSection(listSectionArray);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        int companiesSize = dis.readInt();
                        Company[] companies = new Company[companiesSize];

                        for (int m = 0; m < companiesSize; m++) {
                            String companyName = dis.readUTF();
                            String website = dis.readUTF();

                            int periodsSize = dis.readInt();
                            Company.Period[] periods = new Company.Period[periodsSize];

                            for (int n = 0; n < periodsSize; n++) {
                                LocalDate startDate = localDateAdapter.unmarshal(dis.readUTF());
                                LocalDate finishDate = localDateAdapter.unmarshal(dis.readUTF());
                                String description = dis.readUTF();
                                String title = dis.readUTF();
                                periods[n] = new Company.Period(startDate, finishDate, description, title.isEmpty() ?
                                        null : title);
                            }
                            companies[m] = new Company(companyName, website, periods);
                        }
                        section = new CompanySection(companies);
                        break;
                    default:
                        section = null;
                        break;
                }
                resume.setSection(sectionType, section);
            }
            return resume;
        }
    }
}
