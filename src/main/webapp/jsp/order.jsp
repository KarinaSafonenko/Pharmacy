<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>${orders_label}</title>
    <%@ include file="../WEB-INF/jsp/style.jsp"%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="address" var = "address"/>
    <fmt:message bundle = "${bundle}" key ="no_orders" var = "no_orders"/>
    <fmt:message bundle = "${bundle}" key ="payment_method" var = "payment_method"/>
    <fmt:message bundle = "${bundle}" key ="date" var = "date"/>
    <fmt:message bundle = "${bundle}" key ="sum" var = "sum"/>
</head>
<body class="animsition">
<%@ include file="../WEB-INF/jsp/header.jsp"%>
<div class="shop-cart" id="page">
    <ctg:show-sub_header name="${orders_label}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="woocommerce">
                <c:if test="${empty order_list}">
                    ${no_orders}.
                </c:if>
                <c:if test="${not empty order_list}">
                    <form class="woocommerce-cart-form">
                        <table class="woocommerce-cart-table">
                            <thead>
                            <tr>
                                <th class="product-thumbnail"></th>
                                <th class="product-name">${address}</th>
                                <th class="product-name">${payment_method}</th>
                                <th class="product-weight">${date}</th>
                                <th class="product-quantity">${sum}</th>
                            </tr>
                            </thead>
                            <tbody>
                            <ctg:show-order orders="${order_list}"/>
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
