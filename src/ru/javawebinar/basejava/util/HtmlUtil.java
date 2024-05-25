package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.List;

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
                List<Company> companies = companySection.getCompanies();
                if (companies != null) {
                    companies.forEach(company -> {
                        stringBuilder.append("<li>");
                        String url = company.getHomepage().getUrl();
                        stringBuilder.append("<a ");
                        if (!url.isEmpty()) {
                            stringBuilder.append("href=").append(url);
                        }
                        stringBuilder.append(">")
                                .append(company.getHomepage().getName()).append("</a>");
                        List<Company.Period> periods = company.getPeriods();
                        periods.forEach(period -> {
                            LocalDate finishDate = period.getFinishDate();
                            stringBuilder.append("<div>").append(formatDates(period)).append("</div>");
                            String title = period.getTitle();
                            if (title != null) {
                                stringBuilder.append("<div>").append(title).append("</div>");
                            }
                            stringBuilder.append("<div>").append(period.getDescription()).append("</div>");
                        });
                        stringBuilder.append("</li>");
                    });
                }
                stringBuilder.append("</ul>");
                return stringBuilder.toString();
        }
        return "";
    }

    public static List<Company> getNewCompany() {
        Company company = new Company();
        company.setPeriods(List.of(new Company.Period()));
        return List.of(company);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String formatDates(Company.Period period) {
        return DateUtil.format(period.getStartDate()) + " - " + DateUtil.format(period.getFinishDate());
    }
}
