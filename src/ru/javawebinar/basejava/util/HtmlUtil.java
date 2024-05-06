package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.*;

public class HtmlUtil {
    public static String toHtml(ContactType contactType, String value) {
        return (value == null) ? "" : toHtml0(contactType, value);
    }

    private static String toHtml0(ContactType contactType, String value) {
        switch (contactType) {
            case PHONE:
                return contactType.getTitle() + ": " + value;
            case SKYPE:
                return contactType.getTitle() + ": " + toLink("skype:" + value, value);
            case EMAIL:
                return contactType.getTitle() + ": " + toLink("mailto:" + value, value);
            default:
                return toLink(value, contactType.getTitle());
        }
    }

    private static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }
    public static String toHtml(SectionType sectionType, Section section) {
        return (section == null) ? "" : toHtml0(sectionType, section);
    }

    private static String toHtml0(SectionType sectionType, Section section) {
        StringBuilder stringBuilder = new StringBuilder(sectionType.getTitle() + ": <ul>");
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                TextSection textSection = (TextSection) section;
                return sectionType.getTitle() + ": " + textSection.getText();
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                ListSection listSection = (ListSection) section;
                listSection.getList().forEach(s -> {
                    stringBuilder.append("<li>");
                    stringBuilder.append(s);
                    stringBuilder.append("</li>");
                });
                stringBuilder.append("</ul>");
                return stringBuilder.toString();
            case EXPERIENCE:
            case EDUCATION:
                CompanySection companySection = (CompanySection) section;
                companySection.getCompanies().forEach(company -> {
                    stringBuilder.append("<li>");
                    stringBuilder.append(company);
                    stringBuilder.append("</li>");
                });
                return stringBuilder.toString();
        }
        return "";
    }
}
