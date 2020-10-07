<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="/view/common/header.jsp">
    <jsp:param name="pageTitle" value="Registration"/>
</jsp:include>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="magazine"/>



    <c:if test="${error != null && !error.isEmpty()}">
    <p>${error}<p>
    </c:if>
    <div class="container">
        <h2 class="p-1"><fmt:message key="header.signup"/></h2>
        <div id="registrationFormBlock">
            <form id="registrationForm" action="/signUp" method="post" class="needs-validation" novalidate>
                <div class="form-group row">
                    <label for="inputFirstName" class="col-sm-2 col-form-label"><fmt:message
                            key="user.firstName"/> </label>
                    <div class="col-sm-4">
                        <input type="text" name="firstName" value="${user.firstName}" class="form-control"
                               id="inputFirstName"
                               placeholder="First name"
                               required>
                        <div class="invalid-feedback">
                            <fmt:message key="register.enterName"/>
                        </div>
                        <div class="valid-feedback">
                            <fmt:message key="register.good"/>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputLastName" class="col-sm-2 col-form-label"><fmt:message
                            key="user.lastName"/></label>
                    <div class="col-sm-4">
                        <input type="text" name="lastName" value="${user.lastName}" class="form-control"
                               id="inputLastName"
                               placeholder="Last name"
                               required>
                        <div class="invalid-feedback">
                            <fmt:message key="register.enterLastName"/>
                        </div>
                        <div class="valid-feedback">
                            <fmt:message key="register.good"/>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputBirthDate" class="col-sm-2 col-form-label"><fmt:message
                            key="user.birthDate"/></label>
                    <div class="col-sm-4">
                        <input type="date" name="birthDate" value="${user.birthDate}" class="form-control"
                               id="inputBirthDate"
                               placeholder="Birth date"
                               required>
                        <div class="invalid-feedback">
                            <fmt:message key="register.birthDate"/>
                        </div>
                        <div class="valid-feedback">
                            <fmt:message key="register.good"/>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputEmail" class="col-sm-2 col-form-label"><fmt:message key="user.email"/></label>
                    <div class="col-sm-4">
                        <input type="email" name="login" value="${user.email}" class="form-control" id="inputEmail"
                                placeholder="Email"
                               required>
                        <div class="invalid-feedback">
                            <fmt:message key="register.email"/>
                        </div>
                        <div class="valid-feedback">
                           <fmt:message key="register.good"/>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputPassword" class="col-sm-2 col-form-label"><fmt:message
                            key="user.password"/></label>
                    <div class="col-sm-4">
                        <input type="password" name="password" class="form-control" id="inputPassword"
                               placeholder="New password"
                               required>
                        <div class="invalid-feedback">
                        </div>
                        <div class="valid-feedback">
                            <fmt:message key="register.good"/>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputRepeatedPassword" class="col-sm-2 col-form-label"><fmt:message key="register.repeat"/> </label>
                    <div class="col-sm-4">
                        <input type="password" name="passwordAgain" class="form-control" id="inputRepeatedPassword"
                               placeholder="Repeat password"
                               required>
                        <div class="invalid-feedback">
                            <fmt:message key="register.password"/>
                        </div>
                        <div class="valid-feedback">
                            <fmt:message key="register.good"/>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-sm-4">
                        <button type="submit" class="btn btn-primary"><fmt:message key="header.signup"/></button>
                    </div>
                </div>
            </form>
        </div>
    </div>

</body>
</html>
