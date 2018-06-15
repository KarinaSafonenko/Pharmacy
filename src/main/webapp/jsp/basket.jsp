<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>${basket}</title>
    <%@ include file="../WEB-INF/jsp/style.jsp"%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="basket" var = "basket"/>
    <fmt:message bundle = "${bundle}" key ="product" var = "product"/>
    <fmt:message bundle = "${bundle}" key ="recipe" var = "recipe"/>
    <fmt:message bundle = "${bundle}" key ="quantity" var = "quantity"/>
    <fmt:message bundle = "${bundle}" key ="total" var = "total"/>
    <fmt:message bundle = "${bundle}" key ="price" var = "price"/>
    <fmt:message bundle = "${bundle}" key ="in_stock" var = "in_stock"/>
    <fmt:message bundle = "${bundle}" key ="recount" var = "recount"/>
    <fmt:message bundle = "${bundle}" key ="cart_total" var = "cart_total"/>
    <fmt:message bundle = "${bundle}" key ="empty_basket" var = "empty_basket"/>
    <fmt:message bundle = "${bundle}" key ="proceed_to_checkout" var = "proceed_to_checkout"/>
    <fmt:message bundle = "${bundle}" key ="card" var = "card"/>
    <fmt:message bundle = "${bundle}" key ="credit" var = "credit"/>
    <fmt:message bundle = "${bundle}" key ="basket_pack_amount_message" var = "basket_pack_amount_message"/>
    <fmt:message bundle = "${bundle}" key ="recipe_basket_message" var = "recipe_basket_message"/>
    <fmt:message bundle = "${bundle}" key ="payment_method" var = "payment_method"/>
    <fmt:message bundle = "${bundle}" key ="credit_message" var = "credit_message"/>
</head>
<body class="animsition">
<%@ include file="../WEB-INF/jsp/header.jsp"%>
<div class="shop-cart" id="page">
    <ctg:show-sub_header name="${basket}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="woocommerce">
                <c:if test="${empty client_basket.content}">
                    ${empty_basket}.
                </c:if>
                <c:if test="${not empty client_basket.content}">
                <form class="woocommerce-cart-form" method="POST" action="/ControllerServlet">
                    <input type="hidden" name="command" value="update_basket">
                    <table class="woocommerce-cart-table">
                        <thead>
                        <tr>
                            <th class="product-thumbnail">${product}</th>
                            <th class="product-name"></th>
                            <th class="product-weight">${recipe}</th>
                            <th class="product-quantity">${quantity}</th>
                            <th class="product-price">${price}</th>
                            <th class="product-subtotal">${total}</th>
                            <th class="product-subtotal">${in_stock}</th>
                            <th class="product-remove"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <ctg:basket-product basket="${client_basket}" recipeMap="${recipe_map}"/>
                        </tbody>
                        <tfoot>
                        <tr>
                            <td colspan="8">
                                <div class="form-coupon organic-form">
                                    <div class="form-group update-cart">
                                        <button class="btn btn-brand pill" type="submit">${recount}</button>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        </tfoot>
                    </table>
                </form>
                <form method="POST" action="/ControllerServlet">
                    <input type="hidden" name="command" value="checkout">
                    <input type="hidden" name="cart_sum" value="${cart_sum}">
                    <div class="cart_totals">
                    <h3 class="title">${cart_total}</h3>
                    <div class="row">
                        <div class="col-md-8">
                            <table class="woocommerce-cart-subtotal">
                                <tbody>
                                <tr>
                                    <td data-title="Total">${cart_sum}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                    <c:if test="${requestScope.pack_amount_message}"><label class="text-danger">${basket_pack_amount_message}.</label></c:if>
                    <br/>
                    <c:if test="${requestScope.recipe_message}"><label class="text-danger">${recipe_basket_message}.</label></c:if>
                    <br/>
                    <c:if test="${requestScope.credit_basket_message}"><label class="text-danger">${credit_message}</label></c:if>
                <div class="form-group organic-form-2">
                        <label>${payment_method}:</label>
                    <div class="widget widget-control-header">
                        <div class="select-custom-wrapper">
                            <select class="no-border" name="payment">
                                <option value="CARD">${card}</option>
                                <option value="CREDIT">${credit}</option>
                            </select>
                        </div>
                    </div>
                </div>
                    <div class="proceed-to-checkout">
                        <button class="btn btn-brand pill" type="submit">${proceed_to_checkout}</button>
                    </div>
                </form>
                </c:if>
            </div>
        </div>
    </section>
</div>
<c:if test="${not empty products}">
<ctg:show-dialog products="${products}" number="${products.size()}" buttons="false"/>
</c:if>
<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/footer.jsp"/>
<%@ include file="../WEB-INF/jsp/action.jsp"%>
</body>
</html>
