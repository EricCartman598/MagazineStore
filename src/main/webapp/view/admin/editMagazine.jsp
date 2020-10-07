<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<jsp:include page="/view/common/header.jsp">
    <jsp:param name="pageTitle" value="Admin panel"/>
</jsp:include>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="magazine"/>

<html lang="${cookie['lang'].value}">
<div class="row m-0">
    <jsp:include page="/view/common/adminPanel.jsp">
        <jsp:param name="activeElement" value="Magazines"/>
    </jsp:include>
    <div class="col-sm-9">
        <h2>Edit magazine #<c:out value="${magazine.id}"/></h2>
        <form action="?action=addMagazine" method="post" class="needs-validation" novalidate>
            <input name="id" hidden value="${magazine.id}">
            <div class="form-group row">
                <label for="inputName" class="col-sm-2 col-form-label">Magazine name</label>
                <div class="col-sm-6">
                    <input type="text" name="name" class="form-control" id="inputName" placeholder="Magazine name"
                           value="${magazine.name}" required>
                    <div class="invalid-feedback">
                        <fmt:message key="editMagazine.name"/>
                    </div>
                    <div class="valid-feedback">
                        <fmt:message key="register.good"/>
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <label for="inputDescription" class="col-sm-2 col-form-label"><fmt:message key="magazine.description"/> </label>
                <div class="col-sm-6">
                <textarea class="form-control" name="description" id="inputDescription" aria-label="With textarea"
                          placeholder="Description" required><c:out value="${magazine.description}"/></textarea>
                    <div class="invalid-feedback">
                        <fmt:message key="editMagazine.description"/>
                    </div>
                    <div class="valid-feedback">
                        <fmt:message key="register.good"/>
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <label for="inputPeriod" class="col-sm-2 col-form-label"><fmt:message key="editMagazine.periodInDays" /></label>
                <div class="col-sm-6">
                    <input type="number" name="periodDays" class="form-control" id="inputPeriod"
                           placeholder="Period" value="${magazine.periodDays}" required>
                    <div class="invalid-feedback">
                        <fmt:message key="editMagazine.periodic"/>
                    </div>
                    <div class="valid-feedback">
                        <fmt:message key="register.good"/>
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <label for="inputPrice" class="col-sm-2 col-form-label">Price</label>
                <div class="input-group col-sm-6">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon1">$</span>
                    </div>
                    <input type="number" name="price" class="form-control" step="0.01" id="inputPrice"
                           placeholder="Price"
                           value="${magazine.price}" required>
                    <div class="invalid-feedback">
                        <fmt:message key="editMagazine.price"/>
                    </div>
                    <div class="valid-feedback">
                        <fmt:message key="register.good"/>
                    </div>
                </div>
            </div>
            <div class="form-group row">
                <div class="col-sm-6">
                    <a class="btn btn-danger" href="/control/magazines"><fmt:message key="msg.back"/></a>
                    <button type="submit" class="btn btn-success"><fmt:message key="msg.apply"/></button>
                </div>
            </div>
        </form>
    </div>
</div>
<script src="/scripts/simpleValidation.js"></script>
</body>
</html>