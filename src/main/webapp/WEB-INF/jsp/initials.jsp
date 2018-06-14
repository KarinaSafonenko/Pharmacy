<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="wrong_name_message" var = "wrong_name_message"/>
    <fmt:message bundle = "${bundle}" key ="wrong_surname_message" var = "wrong_surname_message"/>
    <fmt:message bundle = "${bundle}" key ="wrong_patronymic_message" var = "wrong_patronymic_message"/>
    <fmt:message bundle = "${bundle}" key ="name" var = "name_message"/>
    <fmt:message bundle = "${bundle}" key ="surname" var = "surname_message"/>
    <fmt:message bundle = "${bundle}" key ="patronymic" var = "patronymic_message"/>
</head>
<body class="animsition">
<div class="form-group organic-form-2">
    <label>${surname_message}:</label>
    <input class="form-control" type="text" name="surname" value="${surname}">
</div>
<c:if test="${requestScope.wrong_surname}"><label class="text-danger">${wrong_surname_message}</label></c:if>
<div class="form-group organic-form-2">
    <label>${name_message}:</label>
    <input class="form-control" type="text" name="name" value="${name}">
</div>
<c:if test="${requestScope.wrong_name}"><label class="text-danger">${wrong_name_message}</label></c:if>
<div class="form-group organic-form-2">
    <label>${patronymic_message}:</label>
    <input class="form-control" type="text" name="patronymic" value="${patronymic}">
</div>
<c:if test="${requestScope.wrong_patronymic}"><label class="text-danger">${wrong_patronymic_message}</label></c:if>
</body>
</html>
