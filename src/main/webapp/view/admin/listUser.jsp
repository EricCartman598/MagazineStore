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
        <jsp:param name="activeElement" value="Users"/>
    </jsp:include>
    <div class="col-sm-9">
        <h2>User list</h2>
        <c:if test="${fn:length(users) == 0}">
            <div class="alert alert-warning" role="alert">
                <fmt:message key="editUser.empty"/>
            </div>
        </c:if>
        <table class="table table-striped table-bordered table-sm">
            <thead>
            <tr class="d-flex">
                <th class="col-1">#</th>
                <th class="col-1"><fmt:message key="user.firstName"/></th>
                <th class="col-1"><fmt:message key="user.lastName"/></th>
                <th class="col-2"><fmt:message key="user.email"/></th>
                <th class="col-2"><fmt:message key="user.birthDate"/></th>
                <th class="col-2"><fmt:message key="user.created"/></th>
                <th class="col-1"><fmt:message key="user.role"/></th>
                <th class="col-2">Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr class="d-flex">
                    <form method="post">
                        <th class="col-sm-1"><c:out value="${user.id}"/></th>
                        <td class="col-sm-1"><c:out value="${user.firstName}"/></td>
                        <td class="col-sm-1"><c:out value="${user.lastName}"/></td>
                        <td class="col-sm-2"><c:out value="${user.email}"/></td>
                        <fmt:parseDate value="${ user.birthDate }" pattern="yyyy-MM-dd" var="parsedBirthDate"
                                       type="both"/>
                        <td class="col-sm-2"><fmt:formatDate pattern="dd.MM.yyyy" value="${ parsedBirthDate }"/></td>
                        <fmt:parseDate value="${ user.createdDate }" pattern="yyyy-MM-dd" var="parsedCreatedDate"
                                       type="both"/>
                        <td class="col-sm-2"><fmt:formatDate pattern="dd.MM.yyyy"
                                                             value="${ parsedCreatedDate }"/></td>
                        <td class="col-sm-1">
                            <select class="form-control form-control-sm" name="role">
                                <c:if test="${user.role == 'ADMIN'}">
                                    <option selected><fmt:message key="role.admin"/></option>
                                    <option>Customer</option>
                                </c:if>
                                <c:if test="${user.role == 'CUSTOMER'}">
                                    <option selected><fmt:message key="role.customer"/> </option>
                                    <option><fmt:message key="role.admin"/> </option>
                                </c:if>
                            </select>
                        </td>
                        <td class="col-sm-2">
                            <c:if test="${user.id != 1 && user.id != sessionScope.userId}">
                                <input type="hidden" name="userId" value="${user.id}">
                                <input type="submit" class="btn btn-primary btn-sm col-sm-8 offset-sm-2 p-0"
                                       value="Change role">
                            </c:if>
                        </td>
                    </form>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>