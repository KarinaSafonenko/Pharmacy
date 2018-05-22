<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<html>
<head>
</head>
<body>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="property.message" var="bundle" scope="request"/>

<fmt:message bundle = "${bundle}" key ="forgotPasswordMessage" var = "forgot_password_message"/>
<fmt:message bundle = "${bundle}" key ="wrongPasswordMessage" var = "wrong_password_message"/>
<fmt:message bundle = "${bundle}" key ="differentPasswordsMessage" var = "different_passwords_message"/>
<fmt:message bundle = "${bundle}" key ="passwordMessage" var = "password_message"/>
<fmt:message bundle = "${bundle}" key ="repeatPasswordMessage" var = "repeat_password"/>
<fmt:message bundle = "${bundle}" key ="wrongConfirmationCode" var = "wrong_confirmation_message"/>
<fmt:message bundle = "${bundle}" key ="changePassword" var = "change_password_message"/>



<form name="login" method="POST" action="/ControllerServlet">
    <input type="hidden" name="command" value="change_forgotten_password" />
    ${forgot_password_message}<br/>
    <input type="text" name="confirmation_code" value="${confirmation_code}"/>
    <br/>
    <c:if test="${requestScope.confirmation_failed}">${wrong_confirmation_message}</c:if>
    <br/>
    <br>${password_message}<br/>
    <input type="password" name="password"/>
    <br/>
    <c:if test="${requestScope.wrong_password}">${wrong_password_message}</c:if>
    <br/>
    <br/>${repeat_password}<br/>
    <input type="password" name="repeat_password"/>
    <br/>
    <c:if test="${requestScope.different_passwords}">${different_passwords_message}</c:if>
    <br/>
    <input type="submit" value="${change_password_message}"/>
</form>
</body>
</html>
