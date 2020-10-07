<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:include page="/view/common/header.jsp">
    <jsp:param name="pageTitle" value="Admin panel"/>
</jsp:include>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="magazine"/>

<html lang="${cookie['lang'].value}">
<div class="row m-0">
    <jsp:include page="/view/common/adminPanel.jsp">
        <jsp:param name="activeElement" value="Orders"/>
    </jsp:include>
    <div class="col-sm-9">
        <h2>Order list</h2>
        <c:if test="${fn:length(orderAndUser) == 0}">
            <div class="alert alert-warning" role="alert">
                <fmt:message key="editOrder.empty"/>
            </div>
        </c:if>
        <table class="table table-striped table-bordered table-sm">
            <thead>
            <tr class="d-flex">
                <th class="col-1">#</th>
                <th class="col-2"><fmt:message key="user.firstName"/></th>
                <th class="col-2"><fmt:message key="user.lastName"/></th>
                <th class="col-2"><fmt:message key="user.email"/></th>
                <th class="col-1"><fmt:message key="order.price"/></th>
                <th class="col-2"><fmt:message key="order.created"/></th>
                <th class="col-2">Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${orderAndUser}" var="order">
                <tr class="d-flex">
                    <th class="col-sm-1"><c:out value="${order.key.id}"/></th>
                    <td class="col-sm-2"><c:out value="${order.value.firstName}"/></td>
                    <td class="col-sm-2"><c:out value="${order.value.lastName}"/></td>
                    <td class="col-sm-2"><c:out value="${order.value.email}"/></td>
                    <td class="col-sm-1"><c:out value="${order.key.price}"/>$</td>
                    <fmt:parseDate value="${ order.key.created }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                                   type="both"/>
                    <td class="col-sm-2"><fmt:formatDate pattern="dd.MM.yyyy HH:mm"
                                                         value="${ parsedDateTime }"/></td>
                    <td class="col-sm-2">
                        <a class="btn btn-primary btn-sm col-sm-8 offset-sm-2 p-0"
                           href="orders?orderToSeeId=${order.key.id}">See more</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>