<%@ page import="ru.javawebinar.basejava.model.*" %>
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
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}" pattern="[A-Za-z ]{1,255}" title="Имя не должно быть пустым,или состоять из пробелов." required></dd>
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
                        <c:if test="${resume.getSection(sectionType)==null}">
                            <dd><textarea name="${sectionType.name()}" rows="3" cols="100"></textarea></dd>
                        </c:if>
                        <c:if test="${resume.getSection(sectionType)!=null}">
                            <dd><textarea name="${sectionType.name()}" rows="3"
                                          cols="100"><%=((TextSection) resume.getSection(sectionType)).getText()%></textarea>
                            </dd>
                        </c:if>
                    </c:when>
                    <c:when test="${sectionType==SectionType.ACHIEVEMENT || sectionType==SectionType.QUALIFICATIONS}">
                        <c:if test="${resume.getSection(sectionType)==null}">
                            <dd><textarea name="${sectionType.name()}" rows="3" cols="100"></textarea></dd>
                        </c:if>
                        <c:if test="${resume.getSection(sectionType)!=null}">
                            <dd><textarea name="${sectionType.name()}" rows="3"
                                          cols="100"><%=String.join("\n", ((ListSection) resume.getSection(sectionType)).getList())%></textarea>
                            </dd>
                        </c:if>
                    </c:when>
<%--                    <c:when test="${sectionType==SectionType.EXPERIENCE || sectionType==SectionType.EDUCATION}">--%>
<%--                        <c:if test="${resume.getSection(sectionType)!=null}">--%>
<%--                            <c:forEach var="company"--%>
<%--                                       items="<%=((CompanySection) resume.getSection(sectionType)).getCompanies()%>">--%>
<%--                                <dd>--%>
<%--                                    <label class="companyName">Наиемнование организации--%>
<%--                                        <input type="text" name="companyName" value="${company.homepage.name}">--%>
<%--                                    </label>--%>
<%--                                    <label class="companyUrl">Ссылка--%>
<%--                                        <input type="text" name="companyUrl" value="${company.homepage.url}">--%>
<%--                                    </label>--%>
<%--                                    <c:forEach var="period" items="${company.periods}">--%>
<%--                                        <label>Дата начала--%>
<%--                                            <input type="date" name="startDate" value="${period.startDate}">--%>
<%--                                        </label>--%>
<%--                                        <label>Дата завершения--%>
<%--                                            <input type="date" name="finishDate" value="${period.finishDate}">--%>
<%--                                        </label>--%>
<%--                                        <label>Должность--%>
<%--                                            <input type="text" name="companyObjective" value="${period.title}">--%>
<%--                                        </label>--%>
<%--                                        <label>Описание--%>
<%--                                            <textarea name="${sectionType.name()}" rows="3"--%>
<%--                                                      cols="100">${period.description}</textarea>--%>
<%--                                        </label>--%>
<%--                                        <button>Добавить период</button>--%>
<%--                                    </c:forEach>--%>
<%--                                </dd>--%>
<%--                                <button>Добавить организацию</button>--%>
<%--                            </c:forEach>--%>
<%--                        </c:if>--%>
<%--                        <c:if test="${resume.getSection(sectionType)==null}">--%>

<%--                        </c:if>--%>
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