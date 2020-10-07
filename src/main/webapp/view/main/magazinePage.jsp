<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="magazine"/>

<jsp:include page="/view/common/header.jsp">
    <jsp:param name="pageTitle" value="Magazines"/>
</jsp:include>
<html lang="${cookie['lang'].value}">
<div class="col-sm-10 offset-sm-1 py-1 pr-3">
    <h2><fmt:message key="header.magazines"/></h2>
    <div class="row">
        <c:forEach items="${magazines}" var="magazineByOrderId">
            <div class="col-sm-3 d-flex p-2">
                <div class="card bg-light border-secondary">
                    <h5 class="card-header text-left">
                        <c:out value="${magazineByOrderId.name}"/>
                    </h5>
                    <div class="row no-gutters h-100">
                        <div class="col-sm-3 align-self-center p-1">
                            <img src="/img/coming-soon.png" class="card-img">
                        </div>
                        <div class="col-sm-9">
                            <div class="card-body">
                                <p class="card-text"><c:out value="${magazineByOrderId.description}"/></p>
                            </div>
                        </div>
                    </div>
                    <div class="d-flex flex-column">
                        <div class="mt-auto">
                            <hr class="w-75 my-1">
                            <div class="row justify-content-center align-items-center mb-1 px-2">
                                <input type="hidden" class="shop-item-id" value="${magazineByOrderId.id}">
                                <span class="col-sm-4 card-text pl-4"><c:out
                                        value="${magazineByOrderId.periodDays}"/> <fmt:message key="magazines.subscription"/></span>
                                <div class="col-sm-4 button-ph text-center">
                                    <c:if test="${fn:length(sessionScope.magazinesCart) == 0}">
                                        <button type="button" class="btn btn-success shop-add-button"><fmt:message key="magazines.tocart"/></button>
                                    </c:if>
                                    <c:if test="${fn:length(sessionScope.magazinesCart) > 0}">
                                        <c:set var="counter" value="${0}"/>
                                        <c:forEach items="${sessionScope.magazinesCart}" var="cartElement">
                                            <c:if test="${cartElement.key.id == magazineByOrderId.id}">
                                                <button type="button" class="btn btn-danger shop-delete-button"><fmt:message key="magazines.remove"/>
                                                </button>
                                                <c:set var="counter" value="${1}"/>
                                            </c:if>
                                        </c:forEach>
                                        <c:if test="${counter == 0}">
                                            <button type="button" class="btn btn-success shop-add-button"><fmt:message key="magazines.tocart"/>
                                            </button>
                                        </c:if>
                                    </c:if>
                                </div>
                                <p class="col-sm-4 card-text text-right pr-4"><c:out
                                        value="${magazineByOrderId.price}"/>$ </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<script src="/scripts/addingToCart.js"></script>
</body>
</html>

