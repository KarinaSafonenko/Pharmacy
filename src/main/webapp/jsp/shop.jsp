<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>${shop}</title>
    <%@ include file="../WEB-INF/jsp/style.jsp"%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="shop" var = "shop"/>
    <fmt:message bundle = "${bundle}" key ="of" var = "of"/>
    <fmt:message bundle = "${bundle}" key ="no_products" var = "no_products"/>
</head>
<body class="animsition">
<%@ include file="../WEB-INF/jsp/header.jsp"%>
<%@ include file="../WEB-INF/jsp/shop.jsp"%>
<ctg:show-dialog products="${products}" number="${product_number_on_page}" buttons="true"/>
<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/footer.jsp"/>
<%@ include file="../WEB-INF/jsp/action.jsp"%>
</body>
</html>