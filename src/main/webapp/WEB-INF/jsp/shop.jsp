<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>${shop}</title>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="shop" var = "shop"/>
    <fmt:message bundle = "${bundle}" key ="of" var = "of"/>
    <fmt:message bundle = "${bundle}" key ="no_products" var = "no_products"/>
</head>
<body class="animsition">
<div class="shop-layout-1" id="page">
    <ctg:show-sub_header name="${shop}"/>
    <div class="woocommerce-top-control wrapper">
        <section class="boxed-sm">
            <div class="container">
                <div class="row">
                    <div class="woocommerce-top-control">
                        <c:if test="${not empty requestScope.products}"><p class="woocommerce-result-count">${left_border} - ${right_border} ${of} ${result_count}</p></c:if>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <section class="box-sm">
        <div class="container">
            <div class="row main">
                <div class="row product-grid-equal-height-wrapper product-equal-height-4-columns flex multi-row">
                    <c:if test="${empty requestScope.products}"><p class="woocommerce-result-count">${no_products}</p></c:if>
                    <ctg:show-product products="${products}" number="${product_number_on_page}"/>
                </div>
                <div class="row">
                    <div class="col-md-12 text-right">
                        <nav>
                            <ul class="pagination pagination-style-1">
                                <li>
                                    <a class="prev page-numbers" href="/ControllerServlet?command=show_previous_product_page&page=${page}&category=${category}">
                                        <i class="fa fa-angle-left"></i>
                                    </a>
                                </li>
                                <li>
                                    <a class="page-numbers">${page}</a>
                                </li>
                                <li>
                                    <a class="next page-numbers" href="/ControllerServlet?command=show_next_product_page&page=${page}&category=${category}">
                                        <i class="fa fa-angle-right"></i>
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
</body>
</html>
