<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<jsp:include page="/view/common/header.jsp">
    <jsp:param name="pageTitle" value="Index page"/>
</jsp:include>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="magazine"/>

<html lang="${cookie['lang'].value}">
<div id="login">
    <div class="container pt-5">
        <div class="row no-gutters justify-content-center align-items-center">
            <div class="col-sm-6 card">
                <form class="form" action="/signIn" method="post">
                    <h3 class="card-header text-center"><fmt:message key="signup.title"/></h3>
                    <div class="card-body pb-1">
                        <div class="form-group">
                            <label for="username"><fmt:message key="user.email"/>:</label><br>
                            <input type="text" name="login" id="username" class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="password"><fmt:message key="user.password"/>:</label><br>
                            <input type="password" name="password" id="password" class="form-control">
                        </div>
                        <div class="form-group pt-2">
                            <input type="submit" name="submit" class="btn btn-success btn-md"
                                   value="<fmt:message key="header.signup"/>">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

