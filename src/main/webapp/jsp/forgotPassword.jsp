<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
</head>
<body>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="property.message" var="bundle" scope="request"/>

<fmt:message bundle = "${bundle}" key ="registrationLogin" var = "registration_login_message"/>
<fmt:message bundle = "${bundle}" key ="next" var = "next_message"/>
<fmt:message bundle = "${bundle}" key ="noSuchLogin" var = "no_such_login_message"/>

<form name="login" method="POST" action="/ControllerServlet">
    <input type="hidden" name="command" value="forgot_password" />
    ${registration_login_message}:<br/>
    <input type="text" name="login" value=""/>
    <br/>
    <c:if test = "${requestScope.no_such_login}">${no_such_login_message}</c:if>
    <br/>
    <input type="submit" value="${next_message}"/>
</form>
</body>
</html>
