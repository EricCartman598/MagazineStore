<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="magazine"/>

<html lang="${cookie['lang'].value}">
<head>
    <title>Library</title>
</head>
<body>
<table border=1>
    <thead>
    <tr>
        <th><fmt:message key="library.userId"/></th>
        <th><fmt:message key="library.magazineId"/></th>
        <th><fmt:message key="library.expiration"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${library}" var="magazine">
        <tr>
            <td><c:out value="${magazine.userId}"/></td>
            <td><c:out value="${magazine.magazineId}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>