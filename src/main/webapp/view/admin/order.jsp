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
        <div>
            <h2>Order #<c:out value="${order.id}"/>.</h2>
        </div>
        <div class="col-sm-12 row">
            <div class="row col-sm-2">
                <p class="mx-3 font-weight-bold">User ID: </p>
                <p><u><c:out value="${user.id}"/></u></p>
            </div>
            <div class="row col-sm-3">
                <p class="mx-3 font-weight-bold">User: </p>
                <p><u><c:out value="${user.lastName}"/> <c:out value="${user.firstName}"/></u></p>
            </div>
            <div class="row col-sm-2">
                <p class="mx-3 font-weight-bold">Order price: </p>
                <p><u><c:out value="${order.price}"/>$</u></p>
            </div>
        </div>
        <div>
            <h4>Magazines in order: ${fn:length(magazinesByOrderId)}</h4>
        </div>
        <c:forEach items="${magazinesByOrderId}" var="magazineByOrderId">
            <div class="card mb-3">
                <h5 class="card-header">Magazine #<c:out value="${magazineByOrderId.key.id}"/>. <c:out
                        value="${magazineByOrderId.key.name}"/></h5>
                <div class="card-body my-1">
                    <div class="row">
                        <p class="mx-3 font-weight-bold"><fmt:message key="magazine.description"/>: </p>
                        <p><c:out value="${magazineByOrderId.key.description}"/></p>
                    </div>
                    <div class="row">
                        <div class="col-sm-3 row">
                            <p class="mx-3 font-weight-bold"><fmt:message key="price.period"/>: </p>
                            <p><u><c:out value="${magazineByOrderId.key.price}"/>$</u></p>
                        </div>
                        <div class="col-sm-3 row">
                            <p class="mx-3 font-weight-bold">Period days: </p>
                            <p><u><c:out value="${magazineByOrderId.key.periodDays}"/></u></p>
                        </div>
                        <div class="col-sm-3 row">
                            <p class="mx-3 font-weight-bold">Amount: </p>
                            <p><u><c:out value="${magazineByOrderId.value}"/></u></p>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>