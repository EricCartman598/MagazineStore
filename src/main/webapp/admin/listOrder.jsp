<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="magazine"/>

<html lang="${cookie['lang'].value}">
<head>
    <title></title>
</head>
<body>
<table border=1>
    <thead>
    <tr>
        <th><fmt:message key="order_tab.id"/></th>
        <th><fmt:message key="order_tab.userId"/></th>
        <th><fmt:message key="order_tab.price"/></th>
        <th><fmt:message key="order_tab.created"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${orders}" var="order">
        <tr>
            <td><c:out value="${order.id}"/></td>
            <td><c:out value="${order.userId}"/></td>
            <td><c:out value="${order.price}"/></td>
            <fmt:parseDate value="${ order.created }" pattern="yyyy-MM-dd'T'HH:mm:ss" var="parsedDateTime" type="both"/>
            <td><fmt:formatDate pattern="dd.MM.yyyy HH:mm:ss" value="${ parsedDateTime }"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>