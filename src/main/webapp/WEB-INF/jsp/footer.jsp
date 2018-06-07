<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>

    <fmt:message bundle = "${bundle}" key ="copyright" var = "copyright"/>
</head>
<body>
<footer class="footer-style-4">
    <div class="copy-right style-2">
        <div class="container">
            <div class="row">
                <div class="copy-right-inner">
                    <p>Copyright © 2018 ${copyright}</p>
                </div>
            </div>
        </div>
    </div>
</footer>
</body>
</html>
