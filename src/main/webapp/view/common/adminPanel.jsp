<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="magazine"/>

<html lang="${cookie['lang'].value}">
<div class="col-sm-2">
    <h2 class="col-sm-10 offset-sm-1"><fmt:message key="header.admin_panel"/></h2>
    <div class="list-group col-sm-10 offset-sm-1">
        <c:if test="${param.activeElement == 'Magazines'}">
            <a href="/control/magazines" class="list-group-item list-group-item-action active"><fmt:message key="admin_panel.magazinesList"/> </a>
            <a href="/control/users" class="list-group-item list-group-item-action"><fmt:message key="admin_panel.userList"/></a>
            <a href="/control/orders" class="list-group-item list-group-item-action"><fmt:message key="admin_panel.orderList"/></a>
        </c:if>
        <c:if test="${param.activeElement == 'Users'}">
            <a href="/control/magazines" class="list-group-item list-group-item-action"><fmt:message key="admin_panel.magazinesList"/></a>
            <a href="/control/users" class="list-group-item list-group-item-action active"><fmt:message key="admin_panel.userList"/></a>
            <a href="/control/orders" class="list-group-item list-group-item-action"><fmt:message key="admin_panel.orderList"/></a>
        </c:if>
        <c:if test="${param.activeElement == 'Orders'}">
            <a href="/control/magazines" class="list-group-item list-group-item-action"><fmt:message key="admin_panel.magazinesList"/></a>
            <a href="/control/users" class="list-group-item list-group-item-action"><fmt:message key="admin_panel.userList"/></a>
            <a href="/control/orders" class="list-group-item list-group-item-action active"><fmt:message key="admin_panel.orderList"/></a>
        </c:if>
    </div>
</div>

