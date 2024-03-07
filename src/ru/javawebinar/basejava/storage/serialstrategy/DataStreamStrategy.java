package ru.javawebinar.basejava.storage.serialstrategy;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.LocalDateAdapter;

import java.io.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataStreamStrategy implements SerializationStrategy {
    private static final LocalDateAdapter localDateAdapter = new LocalDateAdapter();

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

            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> s : sections.entrySet()) {
                SectionType sectionType = s.getKey();
                dos.writeUTF(sectionType.toString());

                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection textSection = (TextSection) s.getValue();
                        dos.writeUTF(textSection.getText());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection listSection = (ListSection) s.getValue();
                        List<String> listSectionList = listSection.getList();
                        dos.writeInt(listSectionList.size());
                        for (String string : listSectionList) {
                            dos.writeUTF(string);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        CompanySection companySection = (CompanySection) s.getValue();
                        List<Company> companies = companySection.getCompanies();
                        dos.writeInt(companies.size());
                        for (Company company : companies) {
                            dos.writeUTF(company.getHomepage().getName());
                            dos.writeUTF(company.getHomepage().getUrl());

                            Set<Company.Period> periods = company.getPeriods();
                            dos.writeInt(periods.size());
                            for (Company.Period period : periods) {
                                dos.writeUTF(localDateAdapter.marshal(period.getStartDate()));
                                dos.writeUTF(localDateAdapter.marshal(period.getFinishDate()));
                                dos.writeUTF(period.getDescription());
                                String title = period.getTitle();
                                dos.writeUTF(title == null ? "" : title);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
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
