<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
</head>
<body>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="property.message" var="bundle" scope="request"/>

<fmt:message bundle = "${bundle}" key ="welcome" var = "welcome_message"/>

${welcome_message}, ${sessionScope.name} ${sessionScope.surname} [${sessionScope.role}]!

<form name="locale" method="POST" action="/ControllerServlet">
    <input type="hidden" name="command" value="change_locale" />
    <input type="hidden" name="locale" value="ru_RU" />
    <input type="submit" value="Изменить локаль на русский"/>
</form>

<form name="logout" method="POST" action="/ControllerServlet">
    <input type="hidden" name="command" value="logout" />
    <input type="submit" value="Выйти"/>
</form>
</body>
</html>
