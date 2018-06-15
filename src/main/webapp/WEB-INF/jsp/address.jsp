<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="street" var = "street_label"/>
    <fmt:message bundle = "${bundle}" key ="house" var = "house_label"/>
    <fmt:message bundle = "${bundle}" key ="flat" var = "flat_label"/>
</head>
<body>
<div class="form-group organic-form-2">
    <label>${street_label}: </label>
    <input class="form-control" type="text" name="street" value="${street}">
</div>
<div class="form-group organic-form-2">
    <label>${house_label}: </label>
    <input class="form-control" type="number" name="house" min="1" value="${house}">
</div>
<div class="form-group organic-form-2">
    <label>${flat_label}: </label>
    <input class="form-control" type="number" name="flat" min="1" value="${flat}">
</div>
</body>
</html>
