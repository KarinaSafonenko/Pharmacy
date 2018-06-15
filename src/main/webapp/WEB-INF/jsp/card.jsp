<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="card_code" var = "code"/>
    <fmt:message bundle = "${bundle}" key ="card_number" var = "number"/>
    <fmt:message bundle = "${bundle}" key ="incorrect_card_information" var = "incorrect_card_information"/>
</head>
<body>
<div class="form-group organic-form-2">
    <label>${number}: </label>
    <input class="form-control" type="number" name="card_number" min="0" value="${card_number}">
</div>
<div class="form-group organic-form-2">
    <label>${code}: </label>
    <input class="form-control" type="number" name="card_code" min="0" value="${card_code}">
</div>
<c:if test="${wrong_card_information}"><label class="text-danger">${incorrect_card_information}</label></c:if>
</body>
</html>
