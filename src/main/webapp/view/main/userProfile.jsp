<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/view/common/header.jsp">
    <jsp:param name="pageTitle" value="Profile"/>
</jsp:include>



<div class="row m-0">
    <jsp:include page="/view/common/profilePanel.jsp">
        <jsp:param name="activeElement" value="info"/>
    </jsp:include>
    <div class="col-sm-9">
        <c:if test="${showData == null || showData == 'userInfo'}">
            <h2>User info</h2>
            <c:if test="${message != null && message != \"\"}">
                <p style="font-size: 24px; color: red">${message}</p>
            </c:if>
            <div style="width: 100%" align="right">
                <div style="display: inline-block; margin: 10px">Your first name: ${user.firstName}</div>
                <div style="display: inline-block; margin: 10px">Your last Name: ${user.lastName}</div>
                <div style="display: inline-block; margin: 10px">Your role: ${user.role}</div>
                <div style="display: inline-block; margin: 10px">Your balance: ${user.balance}</div>
            </div>
            <div class="col-sm-9">
                <button class="btn btn-success mb-3" type="button" data-toggle="collapse"
                        data-target="#editDataFormBlock"
                        aria-expanded="false" aria-controls="editDataFormBlock">
                    Edit profile
                </button>
                <div class="collapse" id="editDataFormBlock">
                    <form id="editDataForm" action="/changeUserData" method="post" class="needs-validation" novalidate>
                        <div class="form-group row">
                            <label for="inputFirstName" class="col-sm-2 col-form-label">First name</label>
                            <div class="col-sm-10">
                                <input type="text" name="firstName" value="${user.firstName}" class="form-control"
                                       id="inputFirstName"
                                       placeholder="First name"
                                       required>
                                <div class="invalid-feedback">
                                    Please enter your first name.
                                </div>
                                <div class="valid-feedback">
                                    Looks good!
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="inputLastName" class="col-sm-2 col-form-label">Last name</label>
                            <div class="col-sm-10">
                                <input type="text" name="lastName" value="${user.lastName}" class="form-control"
                                       id="inputLastName"
                                       placeholder="Last name"
                                       required>
                                <div class="invalid-feedback">
                                    Please enter your last name.
                                </div>
                                <div class="valid-feedback">
                                    Looks good!
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="inputBirthDate" class="col-sm-2 col-form-label">Birth date</label>
                            <div class="col-sm-10">
                                <input type="date" name="birthDate" value=${user.birthDate} class="form-control"
                                       id="inputBirthDate"
                                       placeholder="Birth date"
                                       required>
                                <div class="invalid-feedback">
                                    Please enter your birth date.
                                </div>
                                <div class="valid-feedback">
                                    Looks good!
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="inputEmail" class="col-sm-2 col-form-label">Birth date</label>
                            <div class="col-sm-10">
                                <input type="email" name="login" value="${user.email}" class="form-control"
                                       id="inputEmail"
                                       placeholder="Email"
                                       required>
                                <div class="invalid-feedback">
                                    Please enter your email.
                                </div>
                                <div class="valid-feedback">
                                    Looks good!
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-sm-10">
                                <button type="submit" class="btn btn-primary">Apply changes</button>
                            </div>
                        </div>
                    </form>
                </div>

                <button class="btn btn-success mb-3" type="button" data-toggle="collapse"
                        data-target="#changePasswordFormBlock"
                        aria-expanded="false" aria-controls="changePasswordFormBlock">
                    Change password
                </button>
                <div class="collapse" id="changePasswordFormBlock">
                    <form id="changePasswordForm" action="/changeUserPassword" method="post" class="needs-validation"
                          novalidate>
                        <div class="form-group row">
                            <label for="oldPassword" class="col-sm-2 col-form-label">Old password</label>
                            <div class="col-sm-10">
                                <input type="password" name="oldPassword" class="form-control" id="oldPassword"
                                       placeholder="Old password"
                                       required>
                                <div class="invalid-feedback">
                                    Please enter your old password.
                                </div>
                                <div class="valid-feedback">
                                    Looks good!
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="newPassword" class="col-sm-2 col-form-label">New password</label>
                            <div class="col-sm-10">
                                <input type="password" name="newPassword" class="form-control" id="newPassword"
                                       placeholder="New password"
                                       required>
                                <div class="invalid-feedback">
                                    Please enter your new password.
                                </div>
                                <div class="valid-feedback">
                                    Looks good!
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label for="repeatedNewPassword" class="col-sm-2 col-form-label">New password</label>
                            <div class="col-sm-10">
                                <input type="password" name="repeatPassword" class="form-control"
                                       id="repeatedNewPassword"
                                       placeholder="Repeat password"
                                       required>
                                <div class="invalid-feedback">
                                    Please repeat your new password.
                                </div>
                                <div class="valid-feedback">
                                    Looks good!
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-sm-10">
                                <button type="submit" class="btn btn-primary">Change password</button>
                            </div>
                        </div>
                    </form>
                </div>

                <button class="btn btn-success mb-3" type="button" data-toggle="collapse"
                        data-target="#topUpBalanceFormBlock"
                        aria-expanded="false" aria-controls="topUpBalanceFormBlock">
                    Top up your balance
                </button>
                <div class="collapse" id="topUpBalanceFormBlock">
                    <form id="topUpBalanceForm" action="/topUpUserBalance" method="post" class="needs-validation"
                          novalidate>
                        <div class="form-group row">
                            <label for="oldPassword" class="col-sm-2 col-form-label">Amount</label>
                            <div class="col-sm-10">
                                <input type="number" step="0.1" name="amount" class="form-control" id="amount"
                                       placeholder="Amount"
                                       required>
                                <div class="invalid-feedback">
                                    Please enter amount.
                                </div>
                                <div class="valid-feedback">
                                    Looks good!
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-sm-10">
                                <button type="submit" class="btn btn-primary">Top up balance</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </c:if>
        <c:if test="${showData == 'userLibrary'}">
            <h2>User library</h2>
            <table class="table table-striped table-bordered table-sm">
                <thead>
                <tr class="d-flex">
                    <th class="col-6">Name</th>
                    <th class="col-6">Expiration</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${userLibrary}" var="entry">
                    <tr class="d-flex">
                        <td class="col-sm-6"><c:out value="${entry.key.name}"/></td>
                        <td class="col-sm-6"><c:out value="${entry.value}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${showData == 'userHistory'}">
            <h2>User history</h2>
            <c:forEach items="${userHistory}" var="entry">
                Order #${entry.key.id}<br/>
                price: ${entry.key.price}<br/>
                created date: ${entry.key.created}
                <table class="table table-striped table-bordered table-sm">
                    <thead>
                    <tr class="d-flex">
                        <th class="col-12">Magazines</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="magazine" items="${entry.value}">
                        <tr>
                            <td><c:out value="${magazine.name} "/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:forEach>
        </c:if>
    </div>
</div>

</body>
</html>
