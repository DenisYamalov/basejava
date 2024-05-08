<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="resume" scope="request" type="ru.javawebinar.basejava.model.Resume"/>
<jsp:useBean id="sectionType" scope="request" type="ru.javawebinar.basejava.model.SectionType"/>
<c:if test="${resume.getSection(sectionType)==null}">
    <dd><textarea name="${sectionType.name()}" rows="3"
                  cols="100"></textarea>
    </dd>
</c:if>