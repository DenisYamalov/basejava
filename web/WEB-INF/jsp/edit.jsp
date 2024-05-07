<%@ page import="ru.javawebinar.basejava.util.HtmlUtil" %>
<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.SectionType"/>
            <dl>
                <dt>${sectionType.title}:</dt>
                <c:choose>
                    <c:when test="${sectionType==SectionType.PERSONAL || sectionType==SectionType.OBJECTIVE}">
                        <dd><textarea name="${sectionType.name()}" rows="3"
                                      cols="100"><%=((TextSection) resume.getSection(sectionType)).getText()%></textarea>
                        </dd>
                    </c:when>
                    <c:when test="${sectionType==SectionType.ACHIEVEMENT || sectionType==SectionType.QUALIFICATIONS}">
                        <dd><textarea name="${sectionType.name()}" rows="3"
                                      cols="100"><%=String.join("\n", ((ListSection) resume.getSection(sectionType)).getList())%></textarea>
                        </dd>
                    </c:when>
<%--                    <c:when test="${sectionType==SectionType.EXPERIENCE || sectionType==SectionType.EDUCATION}">--%>
<%--                        <c:forEach var="company" items="<%=((CompanySection) resume.getSection(sectionType)).getCompanies()%>">--%>
<%--                            <dd>--%>
<%--                                <label class="companyName">Наиемнование организации--%>
<%--                                    <input type="text" value="${company.homepage.name}">--%>
<%--                                </label>--%>
<%--                                <label class="companyUrl">Ссылка--%>
<%--                                    <input type="text" value="${company.homepage.url}">--%>
<%--                                </label>--%>
<%--                                <c:forEach var="period" items="${company.periods}">--%>
<%--                                    <label>Дата начала--%>
<%--                                        <input type="date" value="${period.startDate}">--%>
<%--                                    </label>--%>
<%--                                    <label>Дата завершения--%>
<%--                                        <input type="date" value="${period.finishDate}">--%>
<%--                                    </label>--%>
<%--                                    <label>Должность--%>
<%--                                        <input type="text" value="${period.title}">--%>
<%--                                    </label>--%>
<%--                                    <label>Описание--%>
<%--                                        <textarea rows="3" cols="100">${period.description}</textarea>--%>
<%--                                    </label>--%>
<%--                                </c:forEach>--%>
<%--                            </dd>--%>
<%--                        </c:forEach>--%>
<%--                    </c:when>--%>
                </c:choose>

            </dl>
        </c:forEach>

        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>