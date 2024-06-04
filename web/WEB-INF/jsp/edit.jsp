<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page import="ru.javawebinar.basejava.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="js/data_post.js"></script>
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded" id="form">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" pattern="[A-Za-z А-Яа-я]{1,255}"
                       title="Имя не должно быть пустым, состоять из пробелов или содержать цифры/спец.символы."
                       required></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr/>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.SectionType"/>
            <dl>
                <dt>${sectionType.title}:</dt>
                <c:choose>
                    <c:when test="${sectionType eq SectionType.PERSONAL || sectionType eq SectionType.OBJECTIVE}">
                        <dd><textarea name="${sectionType.name()}" rows="3" cols="100"><%=
                        resume.getSection(sectionType) == null ? ""
                                : ((TextSection) resume.getSection(sectionType)).getText()%></textarea>
                        </dd>
                    </c:when>
                    <c:when test="${sectionType eq SectionType.ACHIEVEMENT || sectionType eq SectionType.QUALIFICATIONS}">
                        <dd>
                            <textarea name="${sectionType.name()}" rows="3" cols="100"><%=
                            resume.getSection(sectionType) == null ? ""
                                    : String.join("\n", ((ListSection) resume.getSection(sectionType)).getList())%></textarea>
                        </dd>
                    </c:when>
                    <c:when test="${sectionType eq SectionType.EXPERIENCE || sectionType eq SectionType.EDUCATION}">
                        <c:forEach var="company"
                                   items="<%=resume.getSection(sectionType)==null ? HtmlUtil.getNewCompany()
                                   : ((CompanySection) resume.getSection(sectionType)).getCompanies()%>">
                            <div class="${sectionType.name()} companyContainer">
                                <dd>
                                    <label class="companyName">Наименование организации
                                        <input type="text" name="${sectionType.name()}:companyName"
                                               value="${company.homepage.name}">
                                    </label>
                                    <label class="companyUrl">Ссылка
                                        <input type="text" name="${sectionType.name()}:companyUrl"
                                               value="${company.homepage.url}">
                                    </label>
                                    <c:forEach var="period" items="${company.periods}">
                                        <div class="periodContainer">
                                            <label>Дата начала
                                                <input type="text" name="${sectionType.name()}:startDate"
                                                       value="${DateUtil.format(period.startDate)}">
                                            </label>
                                            <label>Дата завершения
                                                <input type="text" name="${sectionType.name()}:finishDate"
                                                       value="${DateUtil.format(period.finishDate)}">
                                            </label>
                                            <c:if test="${sectionType eq SectionType.EXPERIENCE}">
                                                <label>Должность
                                                    <input type="text" name="${sectionType.name()}:companyObjective"
                                                           value="${period.title}">
                                                </label>
                                            </c:if>
                                            <label>Описание
                                                <textarea name="${sectionType.name()}:periodDescription" rows="3"
                                                          cols="100">${period.description}</textarea>
                                            </label>
                                        </div>
                                    </c:forEach>
                                    <button class="add_button add_period_button" type="button">Добавить период
                                    </button>
                                    <button class="delete_button" type="button">Удалить период</button>
                                </dd>
                            </div>
                        </c:forEach>
                        <button class="add_button add_education_button" id="add_organization" type="button">Добавить организацию</button>
                    </c:when>
                </c:choose>
            </dl>
            <hr/>
        </c:forEach>
<%--        <hr/>--%>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>