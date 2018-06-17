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
    <fmt:message bundle = "${bundle}" key ="add_product" var = "add_product"/>
    <fmt:message bundle = "${bundle}" key ="add_producer" var = "add_producer"/>
</head>
<body class="animsition">
<%@ include file="../WEB-INF/jsp/pharmacist_header.jsp"%>
<div class="shop-layout-1" id="page">
    <ctg:show-sub_header name="${shop}"/>
    <div class="woocommerce-top-control wrapper">
        <section class="boxed-sm">
            <div class="container">
                <div class="row">
                    <div class="woocommerce-top-control">
                        <c:if test="${not empty requestScope.products}"><p class="woocommerce-result-count">${left_border} - ${right_border} ${of} ${result_count}</p></c:if>
                        <form method="post" action="/ControllerServlet">
                            <input type="hidden" name="command" value="prepare_add_product" />
                            <div class="form-group footer-form">
                            <button class="btn btn-brand pill" type="submit">${add_product}</button>
                        </div>
                        </form>
                        <form method="get" action="/jsp/add_producer.jsp">
                        <div class="form-group footer-form">
                            <button class="btn btn-brand pill" type="submit">${add_producer}</button>
                        </div>
                        </form>
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
                                    <a class="prev page-numbers" href="/ControllerServlet?command=show_all_products_previous_page&page=${page}">
                                        <i class="fa fa-angle-left"></i>
                                    </a>
                                </li>
                                <li>
                                    <a class="page-numbers">${page}</a>
                                </li>
                                <li>
                                    <a class="next page-numbers" href="/ControllerServlet?command=show_all_products_next_page&page=${page}">
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
<ctg:show-dialog products="${products}" number="${product_number_on_page}" buttons="false"/>
<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/footer.jsp"/>
<%@ include file="../WEB-INF/jsp/action.jsp"%>
</body>
</html>
