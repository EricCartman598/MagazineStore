<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html lang="${cookie['lang'].value}">
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="magazine"/>

<jsp:include page="/view/common/header.jsp">
    <jsp:param name="pageTitle" value="Cart"/>
</jsp:include>

<c:set var="counter" value="${0}"/>
<c:forEach items="${cookie}" var="cookies">
    <c:if test="${cookies.key == 'userId' && cookies.value.value != ''}">
        <input type="hidden" id="balance-field" value="${userBalance}">
        <c:set var="counter" value="${1}"/>
    </c:if>
</c:forEach>
<c:if test="${counter == 0}">
    <input type="hidden" id="balance-field" value="-1">
</c:if>

<h2 class="offset-sm-1"><fmt:message key="cart.yourcart"/>:</h2>
<div class="row">
    <div class="col-sm-7 offset-sm-1 py-1 pr-3">
        <div id="empty-cart-alert">
            <c:if test="${fn:length(sessionScope.magazinesCart) == 0}">
                <div class="alert alert-warning" role="alert">
                    <fmt:message key="cart.warn"/>
                </div>
            </c:if>
        </div>
        <c:forEach items="${sessionScope.magazinesCart}" var="cartMap">
            <div class="card bg-light border-secondary mb-2 cart-row">
                <input type="hidden" class="cart-item-id" value="${cartMap.key.id}">
                <div class="card-header row no-gutters pb-1">
                    <h5 class="col-sm-10 text-left"><c:out value="${cartMap.key.name}"/></h5>
                    <div class="col-sm-2">
                        <button type="button" class="close cart-delete-button">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </div>
                <div class="row no-gutters">
                    <div class="col-sm-2 align-self-center p-1">
                        <img src="/img/coming-soon.png" class="col-sm-8 offset-sm-2 card-img">
                    </div>
                    <div class="col-sm-10 card-body d-flex flex-column pb-1">
                        <div class="row mx-0 pb-2">
                            <div class="col-sm-8 px-0">
                                <p class="card-text">
                                    <c:out value="${cartMap.key.description}"/>
                                </p>
                            </div>
                            <div class="col-sm-4">
                                <h5 class="card-text text-center my-0 cart-price">
                                    <c:out value="${cartMap.key.price}"/>$
                                </h5>
                                <p class="card-text text-center item-period-days">
                                    <c:out value="${cartMap.key.periodDays}"/> <fmt:message key="cart.days"/>
                                </p>
                            </div>
                        </div>
                        <div class="mt-auto">
                            <hr class="w-100 my-1">
                            <div class="row align-items-center mx-0">
                                <div class="col-sm-6 d-flex flex-row align-items-center">
                                    <div class="col-sm-4 input-group input-group-sm">
                                        <div class="input-group-prepend">
                                            <button class="btn btn-success cart-quantity-down" type="button">-</button>
                                        </div>
                                        <input type="text" class="text-center form-control cart-quantity-input"
                                               value="${cartMap.value}">
                                        <div class="input-group-append">
                                            <button class="btn btn-success cart-quantity-up" type="button">+</button>
                                        </div>
                                    </div>
                                    <span class="card-text mr-3 ml-1"> - </span>
                                    <span class="card-text local-period-subscription"> <fmt:message key="cart.subscriprion1"/></span>
                                </div>
                                <h4 class="col-sm-4 offset-sm-2 card-text text-center local-sum"><fmt:message key="cart.sum"/></h4>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="col-sm-4 py-1 pr-3">
        <div class="col-sm-8 offset-sm-2">
            <div class="card bg-light border-secondary mb-2">
                <div class="card-header row no-gutters pb-1">
                    <h5><fmt:message key="cart.yourorder"/>:</h5>
                </div>
                <div class="col-sm-10 offset-sm-1 card-body">
                    <div class="row">
                        <p class="col-sm-6 pl-0"><fmt:message key="cart.totalamount"/>:</p>
                        <p id="cart-total-amount" class="col-sm-6 text-right pr-0">0<fmt:message key="cart.pieces"/></p>
                    </div>
                    <hr>
                    <div class="row">
                        <h4 class="col-sm-6 pl-0"><fmt:message key="cart.totalprice"/>:</h4>
                        <h4 id="cart-total-price" class="col-sm-6 text-right pr-0">0$</h4>
                    </div>
                    <form action="?action=order" method="post">
                        <input class="col-sm-12 btn btn-success mt-2" id="submit-order-button"
                               type="submit"
                               value="<fmt:message key="cart.order"/>">
                        <div id="ph-to-alert" class="mt-3">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/scripts/editingCart.js"></script>
</body>
</html>
