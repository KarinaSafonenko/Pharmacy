<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html><title>Error Page</title>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="property.message" var="bundle" scope="request"/>

<fmt:message bundle = "${bundle}" key ="errorMessage" var = "error_message"/>
<fmt:message bundle = "${bundle}" key ="toMainPage" var = "main_page"/>
${error_message}
<br><br/>
<a href="../../index.jsp">${main_page}</a>
<img src="../../images/sorry.jpg">
</body>
</html>
