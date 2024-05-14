<%@ page import="ru.javawebinar.basejava.model.*" %>
<%@ page import="ru.javawebinar.basejava.util.HtmlUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" pattern="[A-Za-z ]{1,255}"
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
        <h3>Секции:</h3>
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
                        <c:set var="companyList" value="<%=new ArrayList<Company>()%>" scope="page"/>
                        <c:forEach var="company"
                                   items="<%=resume.getSection(sectionType)==null ? HtmlUtil.getNewCompany() : ((CompanySection) resume.getSection(sectionType)).getCompanies()%>">
                            <div class="companyContainer">
                                <dd>
                                    <label class="companyName">Наименование организации
                                        <input type="text" name="companyName"
                                               value="${company.homepage.name}">
                                    </label>
<%--                                    <p>companyName = ${pageScope.get("company")}</p>--%>
                                    <label class="companyUrl">Ссылка
                                        <input type="text" name="companyUrl"
                                               value="${company.homepage.url}">
                                    </label>
                                    <c:set var="periodList" value="<%=new ArrayList<Company.Period>()%>" scope="page"/>
                                    <c:forEach var="period" items="${company.periods}">
                                        <div class="period_container">
                                            <label>Дата начала
                                                <input type="date" name="startDate"
                                                       value="${period.startDate}">
                                            </label>
                                            <label>Дата завершения
                                                <input type="date" name="finishDate"
                                                       value="${period.finishDate}">
                                            </label>
                                            <label>Должность
                                                <input type="text" name="companyObjective"
                                                       value="${period.title}">
                                            </label>
                                            <label>Описание
                                                <textarea name="${sectionType.name()}" rows="3"
                                                          cols="100">${period.description}</textarea>
                                            </label>
                                        </div>
                                        ${periodList.add(period)}
                                    </c:forEach>

                                    <button class="add_button add_period_button" type="button">Добавить период
                                    </button>
                                </dd>
                            </div>
                            ${company.periods=periodList}
                            ${periodList.clear()}
                            ${companyList.add(company)}
                            ${pageScope.put(sectionType.name(),companyList)}
                        </c:forEach>
                        <button class="add_button" id="add_organization" type="button">Добавить организацию</button>
                    </c:when>
                </c:choose>
            </dl>
        </c:forEach>
        <script>
            let clonePeriod = 1
            $('.add_period_button').click(function () {
                $('.period_container').clone()
                    // .attr('id', 'empty_period_' + ++clonePeriod)
                    .insertBefore(this);
            });
        </script>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>