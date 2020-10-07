<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle basename="magazine"/>

<html lang="${cookie['lang'].value}">
<head>
    <title><c:out value="${param.pageTitle}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <script src="/scripts/jQuery.js"></script>
    <script src="/scripts/validateForms.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-sm navbar-light bg-light">
    <a class="navbar-brand">Magazine store</a>
    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
        <c:if test="${param.pageTitle == 'Magazines'}">
            <li class="nav-item active">
                <a class="nav-link" href="/main/magazines"><fmt:message key="header.home"/></a>
            </li>
        </c:if>
        <c:if test="${param.pageTitle != 'Magazines'}">
            <li class="nav-item">
                <a class="nav-link" href="/main/magazines"><fmt:message key="header.home"/></a>
            </li>
        </c:if>
        <c:if test="${isRegistered == true}">
            <c:if test="${param.pageTitle == 'Profile'}">
                <li class="nav-item active">
                    <a class="nav-link" href="/view/main/userProfile.jsp">
                        <fmt:message key="header.profile"/></a>
                </li>
            </c:if>
            <c:if test="${param.pageTitle != 'Profile'}">
                <li class="nav-item">
                    <a class="nav-link" href="/view/main/userProfile.jsp">
                        <fmt:message key="header.profile"/></a>
                </li>
            </c:if>
        </c:if>
        <c:if test="${isAdmin == 'ADMIN'}">
            <c:if test="${param.pageTitle == 'Admin panel'}">
                <li class="nav-item active">
                    <a class="nav-link" tabindex="-1" href="/control/magazines">
                        <fmt:message key="header.admin_panel"/></a>
                </li>
            </c:if>
            <c:if test="${param.pageTitle != 'Admin panel'}">
                <li class="nav-item">
                    <a class="nav-link" tabindex="-1" href="/control/magazines">
                        <fmt:message key="header.admin_panel"/></a>
                </li>
            </c:if>
        </c:if>
        <c:if test="${param.pageTitle == 'Cart'}">
            <li class="nav-item active">
                <a class="nav-link" href="/main/cart">
                    <fmt:message key="header.cart"/></a>
            </li>
        </c:if>
        <c:if test="${param.pageTitle != 'Cart'}">
            <li class="nav-item">
                <a class="nav-link" href="/main/cart">
                    <fmt:message key="header.cart"/></a>
            </li>
        </c:if>
    </ul>
    <c:if test="${isRegistered == null || isRegistered == false}">
        <div class="px-2">
            <a class="btn btn-outline-success my-2 my-sm-0" href="/view/main/register.jsp"><fmt:message
                    key="header.signup"/></a>
        </div>
        <div class="px-2">
            <a class="btn btn-outline-success my-2 my-sm-0" href="/view/main/signIn.jsp"><fmt:message
                    key="header.signin"/></a>
        </div>
    </c:if>
    <c:if test="${isRegistered == true}">
        <div class="px-2">${user.firstName}</div>
        <div class="px-2">
            <a class="btn btn-outline-success my-2 my-sm-0" href="/signIn?logout=true"><fmt:message
                    key="header.logout"/></a>
        </div>
    </c:if>
    <div class="dropdown-buttonLanguage px-2">
        <button id="buttonLanguage" class="btn btn-outline-success my-2 my-sm-0 dropdown-toggle" type="button"
                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">&#x1F310;
        </button>
        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="buttonLanguage">
            <a class="dropdown-item lang-drop" onclick="changeLang('en')" href="">en</a>
            <a class="dropdown-item lang-drop" onclick="changeLang('ru')" href="">ru</a>
        </div>
    </div>
</nav>
<script>
    function changeLang(language) {
        document.cookie = 'lang=' + language + '; path=/';
    }
</script>
