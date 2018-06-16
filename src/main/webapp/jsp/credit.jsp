<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>${credits}</title>
    <%@ include file="../WEB-INF/jsp/style.jsp"%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="no_credits" var = "no_credits"/>
    <fmt:message bundle = "${bundle}" key ="start_date" var = "start_date"/>
    <fmt:message bundle = "${bundle}" key ="latest_deposit" var = "latest_deposit"/>
    <fmt:message bundle = "${bundle}" key ="obligation" var = "obligation"/>
    <fmt:message bundle = "${bundle}" key ="status" var = "status"/>
</head>
<body class="animsition">
<%@ include file="../WEB-INF/jsp/header.jsp"%>
<div class="shop-cart" id="page">
    <ctg:show-sub_header name="${credits}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="woocommerce">
                <c:if test="${empty credit_list}">
                    ${no_credits}.
                </c:if>
                <c:if test="${not empty credit_list}">
                    <form class="woocommerce-cart-form">
                        <table class="woocommerce-cart-table">
                            <thead>
                            <tr>
                                <th class="product-thumbnail"></th>
                                <th class="product-name">${start_date}</th>
                                <th class="product-name">${latest_deposit}</th>
                                <th class="product-weight">${obligation}</th>
                                <th class="product-quantity">${status}</th>
                            </tr>
                            </thead>
                            <tbody>
                            <ctg:show-credit credits="${credit_list}"/>
                            </tbody>
                        </table>
                    </form>
                </c:if>
            </div>
        </div>
    </section>
</div>
<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/footer.jsp"/>
<%@ include file="../WEB-INF/jsp/action.jsp"%>
</body>
</html>
