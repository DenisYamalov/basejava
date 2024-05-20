package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet("/resume")
public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        storage = Config.get().getStorage();
        super.init(config);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName").trim();
        if (fullName.isEmpty()) fullName = null;
        Resume r;
        if (uuid.isEmpty()) {
            r = new Resume(UUID.randomUUID().toString(), fullName);
        } else {
            r = storage.get(uuid);
            r.setFullName(fullName);
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && !value.trim().isEmpty()) {
                r.setContact(type, value);
            } else {
                r.getContacts().remove(type);
            }
        }
        for (SectionType sectionType : SectionType.values()) {
            String value = request.getParameter(sectionType.name());
            if (value != null && !value.trim().isEmpty()) {
                value = value.trim();
                Section section = null;
                String[] periodsCounts = null;
                switch (sectionType) {
                    case PERSONAL:
                    case OBJECTIVE:
                        section = new TextSection(value);
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        section = new ListSection(Arrays.stream(value.split("\n"))
                                                          .map(String::trim)
                                                          .filter(s -> !s.isEmpty())
                                                          .collect(Collectors.toList()));
                        break;
                    case EDUCATION:
                        periodsCounts = request.getParameterValues("educationPeriods");
                    case EXPERIENCE:
                        if (periodsCounts == null) {
                            periodsCounts = request.getParameterValues("experiencePeriods");
                        }
                        String[] companyNames = request.getParameterValues(sectionType.name() + ":companyName");
                        String[] companyUrls = request.getParameterValues(sectionType.name() + ":companyUrl");
                        String[] companyStartDates = request.getParameterValues(sectionType.name() + ":startDate");
                        String[] companyFinishDates = request.getParameterValues(sectionType.name() + ":finishDate");
                        String[] companyObjectives = request.getParameterValues(sectionType.name() +
                                                                                        ":companyObjective");
                        String[] companyPeriodDescriptions = request.getParameterValues(sectionType.name() +
                                                                                                ":periodDescription");

                        List<Company> companies = new ArrayList<>();

                        int totalPeriodsCount = 0;
                        int companiesCount = periodsCounts.length;
                        for (int i = 0; i < companiesCount; i++) {
                            int periodsCount = Integer.parseInt(periodsCounts[i]);
                            List<Company.Period> periods = new ArrayList<>();
                            for (int j = 0; j < periodsCount; j++) {
                                int periodIndex = j + totalPeriodsCount;
                                String companyObjective = null;
                                if (companyObjectives != null) {
                                    companyObjective = companyObjectives[periodIndex];
                                }
                                String stratDateString = companyStartDates[periodIndex];
                                LocalDate startDate = null;
                                if (!(stratDateString.isEmpty() || stratDateString.isBlank())) {
                                    startDate = LocalDate.parse(stratDateString);
                                }

                                LocalDate finishDate = null;
                                String finishDateString = companyFinishDates[periodIndex];
                                if (!(finishDateString.isBlank() || finishDateString.isEmpty())) {
                                    finishDate = LocalDate.parse(finishDateString);
                                }

                                String companyPeriodDescription = companyPeriodDescriptions[periodIndex];

                                if (startDate != null || finishDate != null) {
                                    periods.add(new Company.Period(startDate,
                                                                   finishDate,
                                                                   companyPeriodDescription,
                                                                   companyObjective));
                                }
                            }
                            totalPeriodsCount += periodsCount;
                            if (!periods.isEmpty()) {
                                Company company = new Company(companyNames[i], companyUrls[i], periods);
                                companies.add(company);
                            }
                        }

                        section = new CompanySection(companies);
                        break;
                }
                r.setSection(sectionType, section);
            } else {
                r.getSections().remove(sectionType);
            }
        }
        if (uuid.isEmpty()) {
            storage.save(r);
        } else {
            storage.update(r);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException, ServletException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;

            case "view":
            case "edit":
                r = storage.get(uuid);
                break;
            case "add":
                r = new Resume();
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
