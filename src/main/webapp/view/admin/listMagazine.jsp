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
        <jsp:param name="activeElement" value="Magazines"/>
    </jsp:include>
    <div class="col-sm-9">
        <h2>Magazines list</h2>
        <c:if test="${fn:length(magazines) == 0}">
            <div class="alert alert-warning" role="alert">
                <fmt:message key="listMagazine.empty"/>
            </div>
        </c:if>
        <table class="table table-striped table-bordered table-sm">
            <thead>
            <tr class="d-flex">
                <th class="col-1"><fmt:message key="magazine.id"/></th>
                <th class="col-3"><fmt:message key="magazine.name"/></th>
                <th class="col-5"><fmt:message key="magazine.description"/></th>
                <th class="col-1"><fmt:message key="magazine.periodDays"/></th>
                <th class="col-1"><fmt:message key="magazine.price"/></th>
                <th class="col-1">Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${magazines}" var="magazineByOrderId">
                <tr class="d-flex">
                    <th class="col-sm-1"><c:out value="${magazineByOrderId.id}"/></th>
                    <td class="col-sm-3"><c:out value="${magazineByOrderId.name}"/></td>
                    <td class="col-sm-5"><c:out value="${magazineByOrderId.description}"/></td>
                    <td class="col-sm-1"><c:out value="${magazineByOrderId.periodDays}"/></td>
                    <td class="col-sm-1"><c:out value="${magazineByOrderId.price}"/>$</td>
                    <td class="col-sm-1"><a class="btn btn-primary btn-sm col-sm-8 offset-sm-2 p-0"
                                            href="editMagazine?magazineId=${magazineByOrderId.id}"><fmt:message key="msg.edit"/> </a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <button class="btn btn-success mb-3" type="button" data-toggle="collapse" data-target="#collapseExample"
                aria-expanded="false" aria-controls="collapseExample">
            <fmt:message key="magazine.addNew"/>
        </button>

        <div class="collapse" id="collapseExample">
            <form action="?action=addMagazine" method="post" class="needs-validation" novalidate>
                <div class="form-group row">
                    <label for="inputName" class="col-sm-2 col-form-label">Magazine name</label>
                    <div class="col-sm-6">
                        <input type="text" name="name" class="form-control" id="inputName" placeholder="Magazine name"
                               required>
                        <div class="invalid-feedback">
                            <fmt:message key="editMagazine.name"/>
                        </div>
                        <div class="valid-feedback">
                            <fmt:message key="register.good"/>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputDescription" class="col-sm-2 col-form-label">Description</label>
                    <div class="col-sm-6">
                    <textarea class="form-control" name="description" id="inputDescription" aria-label="With textarea"
                              placeholder="Description" required></textarea>
                        <div class="invalid-feedback">
                            <fmt:message key="editMagazine.description"/>
                        </div>
                        <div class="valid-feedback">
                            <fmt:message key="register.good"/>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputPeriod" class="col-sm-2 col-form-label"><fmt:message key="editMagazine.periodInDays"/> </label>
                    <div class="col-sm-6">
                        <input type="number" name="periodDays" class="form-control" id="inputPeriod"
                               placeholder="Period"
                               required>
                        <div class="invalid-feedback">
                            <fmt:message key="editMagazine.periodic"/>
                        </div>
                        <div class="valid-feedback">
                            <fmt:message key="register.good"/>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputPrice" class="col-sm-2 col-form-label"><fmt:message key="magazine.price"/> </label>
                    <div class="input-group col-sm-6">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon">$</span>
                        </div>
                        <input type="number" name="price" class="form-control" step="0.01" id="inputPrice"
                               placeholder="Price"
                               required>
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
                        <button type="submit" class="btn btn-primary"><fmt:message key="msg.add"/> </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="/scripts/simpleValidation.js"></script>
</body>
</html>