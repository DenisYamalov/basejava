package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.ContactType;

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
}
