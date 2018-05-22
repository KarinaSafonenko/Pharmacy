<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html><head><title>Login</title></head>
<body>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.message" var="bundle" scope="request"/>

<fmt:message bundle = "${bundle}" key ="wrongNameMessage" var = "wrong_name_message"/>
<fmt:message bundle = "${bundle}" key ="duplicateLoginMessage" var = "duplicate_login_message"/>
<fmt:message bundle = "${bundle}" key ="wrongSurnameMessage" var = "wrong_surname_message"/>
<fmt:message bundle = "${bundle}" key ="wrongPatronymicMessage" var = "wrong_patronymic_message"/>
<fmt:message bundle = "${bundle}" key ="wrongEmailMessage" var = "wrong_email_message"/>
<fmt:message bundle = "${bundle}" key ="wrongLoginMessage" var = "wrong_login_message"/>
<fmt:message bundle = "${bundle}" key ="wrongPasswordMessage" var = "wrong_password_message"/>
<fmt:message bundle = "${bundle}" key ="differentPasswordsMessage" var = "different_passwords"/>

<fmt:message bundle = "${bundle}" key ="nameMessage" var = "name_message"/>
<fmt:message bundle = "${bundle}" key ="surnameMessage" var = "surname_message"/>
<fmt:message bundle = "${bundle}" key ="patronymicMessage" var = "patronymic_message"/>
<fmt:message bundle = "${bundle}" key ="sexMessage" var = "sex_message"/>
<fmt:message bundle = "${bundle}" key ="maleMessage" var = "male_message"/>
<fmt:message bundle = "${bundle}" key ="femaleMessage" var = "female_message"/>
<fmt:message bundle = "${bundle}" key ="emailMessage" var = "email_message"/>
<fmt:message bundle = "${bundle}" key ="repeatPasswordMessage" var = "repeat_password"/>
<fmt:message bundle = "${bundle}" key ="register" var = "register"/>
<fmt:message bundle = "${bundle}" key ="loginMessage" var = "login_message"/>
<fmt:message bundle = "${bundle}" key ="passwordMessage" var = "password_message"/>


<form name="registration" method="POST" action="/ControllerServlet">
    <input type="hidden" name="command" value="registration" />
    ${surname_message}<br/>
    <input type="text" name="surname" value="${requestScope.surname}"/>
    <br/>
    <c:if test = "${requestScope.wrong_surname}">${wrong_surname_message}</c:if>
    <br>${name_message}<br/>
    <input type="text" name="name" value="${requestScope.name}"/>
    <br/>
    <c:if test="${requestScope.wrong_name}">${wrong_name_message}</c:if>
    <br>${patronymic_message}<br/>
    <input type="text" name="patronymic" value="${requestScope.patronymic}"/>
    <br/>
    <c:if test="${requestScope.wrong_patronymic}">${wrong_patronymic_message}</c:if>
    <br>${sex_message}<br/>
    <select name="sex">
        <option value="male">${male_message}</option>
        <option value="female">${female_message}</option>
    </select>
    <br>${email_message}<br/>
    <input type="text" name="mail" value="${requestScope.mail}"/>
    <br/>
    <c:if test="${requestScope.wrong_email}">${wrong_email_message}</c:if>
    <br>${login_message}<br/>
    <input type="text" name="login" value="${requestScope.login}"/>
    <br/>
    <c:if test="${requestScope.wrong_login}">${wrong_login_message}</c:if>
    <c:if test="${requestScope.duplicate_login}">${duplicate_login_message}</c:if>
    <br/>${password_message}<br/>
    <input type="password" name="password"/>
    <br/>
    <c:if test="${requestScope.wrong_password}">${wrong_password_message}</c:if>
    <br/>
    <br/>${repeat_password}<br/>
    <input type="password" name="repeat_password"/>
    <br/>
    <c:if test="${requestScope.wrong_password}">${different_passwords}</c:if>
    <br/>
    <input type="submit" value="${register}"/>
</form>
</body>
</html>