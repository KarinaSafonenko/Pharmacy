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


<form name="registration" method="POST" action="/ControllerServlet">
    <input type="hidden" name="command" value="registration" />
    Фамилия:<br/>
    <input type="text" name="surname" value="${surname}"/>
    <br/>
    <c:if test = "${wrong_surname}">${wrong_surname_message}</c:if>
    <br>Имя:<br/>
    <input type="text" name="name" value="${name}"/>
    <br/>
    <c:if test="${wrong_name}">${wrong_name_message}</c:if>
    <br>Отчество:<br/>
    <input type="text" name="patronymic" value="${patronymic}"/>
    <br/>
    <c:if test="${wrong_patronymic}">${wrong_patronymic_message}</c:if>
    <br>Пол:<br/>
    <select name="sex">
        <option value="male">мужской</option>
        <option value="female">женский</option>
    </select>
    <br>Адрес электронной почты:<br/>
    <input type="text" name="mail" value="${mail}"/>
    <br/>
    <c:if test="${wrong_email}">${wrong_email_message}</c:if>
    <br>Логин:<br/>
    <input type="text" name="login" value="${login}"/>
    <br/>
    <c:if test="${wrong_login}">${wrong_login_message}</c:if>
    <c:if test="${duplicate_login}">${duplicate_login_message}</c:if>
    <br/>Пароль:<br/>
    <input type="password" name="password"/>
    <br/>
    <c:if test="${wrong_password}">${wrong_password_message}</c:if>
    <br/>
    <br/>Повторите пароль:<br/>
    <input type="password" name="repeat_password"/>
    <br/>
    <c:if test="${wrong_password}">${different_passwords}</c:if>
    <br/>
    <input type="submit" value="Зарегистрироваться"/>
</form>
</body>
</html>