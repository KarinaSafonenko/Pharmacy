<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="duplicate_login_message" var = "duplicate_login_message"/>
    <fmt:message bundle = "${bundle}" key ="wrong_email_message" var = "wrong_email_message"/>
    <fmt:message bundle = "${bundle}" key ="wrong_login_message" var = "wrong_login_message"/>
    <fmt:message bundle = "${bundle}" key ="wrong_password_message" var = "wrong_password_message"/>
    <fmt:message bundle = "${bundle}" key ="different_passwords_message" var = "different_passwords_message"/>
    <fmt:message bundle = "${bundle}" key ="sex" var = "sex_message"/>
    <fmt:message bundle = "${bundle}" key ="male" var = "male_message"/>
    <fmt:message bundle = "${bundle}" key ="female" var = "female_message"/>
    <fmt:message bundle = "${bundle}" key ="email" var = "email_message"/>
    <fmt:message bundle = "${bundle}" key ="repeat_password_message" var = "repeat_password"/>
    <fmt:message bundle = "${bundle}" key ="login" var = "login_message"/>
    <fmt:message bundle = "${bundle}" key ="password_message" var = "password_message"/>
</head>
<body class="animsition">
<%@ include file="initials.jsp"%>
<div class="form-group organic-form-2">
    <label>${sex_message}:</label>
    <div class="widget widget-control-header">
        <div class="select-custom-wrapper">
            <select class="no-border" name="sex">
                <option value="male">${male_message}</option>
                <option value="female">${female_message}</option>
            </select>
        </div>
    </div>
</div>
<div class="form-group organic-form-2">
    <label>${email_message}:</label>
    <input class="form-control" type="email" name="mail" value="${requestScope.mail}">
</div>
<c:if test="${requestScope.wrong_email}"><label class="text-danger">${wrong_email_message}</label></c:if>
<div class="form-group organic-form-2">
    <label>${login_message}:</label>
    <input class="form-control" type="text" name="login" value="${requestScope.login}">
</div>
<c:if test="${requestScope.wrong_login}"><label class="text-danger">${wrong_login_message}</label></c:if>
<c:if test="${requestScope.duplicate_login}"><label class="text-danger">${duplicate_login_message}</label></c:if>
<div class="form-group organic-form-2">
    <label>${password_message}:</label>
    <input class="form-control" type="password" name="password">
</div>
<c:if test="${requestScope.wrong_password}"><label class="text-danger">${wrong_password_message}</label></c:if>
<div class="form-group organic-form-2">
    <label>${repeat_password}:</label>
    <input class="form-control" type="password" name="repeat_password">
</div>
<c:if test="${requestScope.different_passwords}"><label class="text-danger">${different_passwords_message}</label></c:if>
</body>
</html>
