<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="property.message" var="bundle" scope="request"/>

<fmt:message bundle = "${bundle}" key ="wrongLoginOrPassword" var = "wrong_login_or_password"/>

<form name="login" method="POST" action="/ControllerServlet">
    <input type="hidden" name="command" value="login" />
Логин:<br/>
<input type="text" name="login"/>
<br/>
<br/>Пароль:<br/>
<input type="password" name="password"/>
<br/>
<c:if test="${login_failed}">${wrong_login_or_password}</c:if>
<br/>
<input type="submit" value="Войти"/>
</form>
</body>
</html>

