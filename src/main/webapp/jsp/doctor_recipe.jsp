<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>${recipes}</title>
    <%@ include file="../WEB-INF/jsp/style.jsp"%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="product" var = "product"/>
    <fmt:message bundle = "${bundle}" key ="quantity" var = "quantity"/>
    <fmt:message bundle = "${bundle}" key ="doctor" var = "doctor"/>
    <fmt:message bundle = "${bundle}" key ="no_recipes" var = "no_recipes"/>
    <fmt:message bundle = "${bundle}" key ="recipe" var = "recipe"/>
    <fmt:message bundle = "${bundle}" key ="start_date" var = "start_date"/>
    <fmt:message bundle = "${bundle}" key ="end_date" var = "end_date"/>
    <fmt:message bundle = "${bundle}" key ="edit" var = "edit"/>
    <fmt:message bundle = "${bundle}" key ="client" var = "client"/>
</head>
<body class="animsition">
<%@ include file="../WEB-INF/jsp/header.jsp"%>
<div class="shop-cart" id="page">
    <ctg:show-sub_header name="${recipes}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="woocommerce">
                <c:if test="${empty recipe_list}">
                    ${no_recipes}.
                </c:if>
                <c:if test="${not empty recipe_list}">
                    <form class="woocommerce-cart-form">
                        <table class="woocommerce-cart-table">
                            <thead>
                            <tr>
                                <th class="product-thumbnail">${product}</th>
                                <th class="product-name"></th>
                                <th>${quantity}</th>
                                <th>${doctor}</th>
                                <th>${client}</th>
                                <th>${start_date}</th>
                                <th>${end_date}</th>
                                <th>${edit}</th>
                            </tr>
                            </thead>
                            <tbody>
                            <ctg:show-doctor-recipe recipes="${recipe_list}"/>
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