<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Login</title>
</head>
<body>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.message" var="bundle" scope="request"/>

<fmt:message bundle = "${bundle}" key ="wrongConfirmationCode" var = "wrong_confirmation_message"/>
<fmt:message bundle = "${bundle}" key ="confirmationMessage" var = "confirmation_message"/>
<fmt:message bundle = "${bundle}" key ="completeRegistration" var = "complete_registration"/>


<form name="confirmation" method="POST" action="/ControllerServlet">
<input type="hidden" name="command" value="confirmation"/>
${confirmation_message}<br/>
<input type="text" name="confirmation_code"/>
<br/>
    <c:if test="${confirmation_failed}">${wrong_confirmation_message}</c:if>
    <br/>
    <input type="submit" value="${complete_registration}"/>
</form>
</body>
</html>
