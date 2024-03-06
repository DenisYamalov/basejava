package ru.javawebinar.basejava.storage.serialstrategy;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.util.Map;

public class DataStreamStrategy implements SerializationStrategy {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            // TODO implements sections
            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            sections.entrySet().forEach((s) -> {
                try {
                    dos.writeUTF(s.getKey().toString());
                } catch (IOException e) {
                    //TODO throw storage exception
                    throw new RuntimeException(e);
                }
                Class<? extends Section> sectionClass = s.getValue().getClass();
                Sections sectionsClasses = Sections.valueOf(sectionClass.getName());
                switch (sectionsClasses) {
                    case COMPANY_SECTION:
                        CompanySection companySection = (CompanySection) s;
                        companySection.getCompanies().stream();
                        break;
                    case LIST_SECTION:
                        ListSection listSection = (ListSection) s;
                        break;
                    default:
                        //text section
                        TextSection textSection = (TextSection) s;
                        break;
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            // TODO implements sections
            return resume;
        }
    }

    private static enum Sections {
        COMPANY_SECTION(CompanySection.class),
        LIST_SECTION(ListSection.class),
        TEXT_SECTION(TextSection.class);
        private final Class<? extends Section> section;

        Sections(Class<? extends Section> section) {
            this.section = section;
        }

        public Class<? extends Section> getSection() {
            return section;
        }
    }
}
