<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="/view/common/header.jsp">
    <jsp:param name="pageTitle" value="Index page"/>
</jsp:include>

<c:redirect url = "/main/magazines"/>
</body>
</html>