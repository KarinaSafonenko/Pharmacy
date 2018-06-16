<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>${order}</title>
    <%@ include file="../WEB-INF/jsp/style.jsp"%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="order" var = "order"/>
    <fmt:message bundle = "${bundle}" key ="product" var = "product"/>
    <fmt:message bundle = "${bundle}" key ="quantity" var = "quantity"/>

</head>
<body class="animsition">
<%@ include file="../WEB-INF/jsp/header.jsp"%>
<div class="shop-cart" id="page">
    <ctg:show-sub_header name="${order}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="woocommerce">
                <c:if test="${not empty orders}">
                    <form class="woocommerce-cart-form">
                        <table class="woocommerce-cart-table">
                            <thead>
                            <tr>
                                <th class="product-thumbnail">${product}</th>
                                <th class="product-name"></th>
                                <th class="product-name">${quantity}</th>
                                <th></th>
                                <th></th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <ctg:show-order-content basket="${basket_content}"/>
                            </tbody>
                        </table>
                    </form>
                </c:if>
            </div>
        </div>
    </section>
</div>
<c:if test="${not empty medicine_list}">
    <ctg:show-dialog products="${medicine_list}" number="${medicine_list.size()}" buttons="false"/>
</c:if>
<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/footer.jsp"/>
<%@ include file="../WEB-INF/jsp/action.jsp"%>
</body>
</html>
