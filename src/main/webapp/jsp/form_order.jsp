<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>${login}</title>
    <%@ include file="../WEB-INF/jsp/style.jsp"%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="checkout" var = "checkout"/>
    <fmt:message bundle = "${bundle}" key ="cart_total" var = "cart_total"/>
    <fmt:message bundle = "${bundle}" key ="verify_data" var = "verify_data"/>
    <fmt:message bundle = "${bundle}" key ="not_enough_money_message" var = "not_enought_money_message"/>
</head>
<body class="animsition">
<%@ include file="../WEB-INF/jsp/header.jsp"%>
<div class="login" id="page">
    <ctg:show-sub_header name="${checkout}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="login-wrapper">
                <form method="POST" action="/ControllerServlet">
                    <input type="hidden" name="command" value="form_order" />
                    <input type="hidden" name="cart_sum" value="${cart_sum}" />
                    <div class="row">
                        <div class="form-group organic-form-2">
                            <label>${cart_total}: ${cart_sum} $</label>
                        </div>
                        <%@include file="../WEB-INF/jsp/card.jsp"%>
                        <%@include file="../WEB-INF/jsp/address.jsp"%>
                        <c:if test="${incorrect_address}"><label class="text-danger">${verify_data}</label></c:if>
                        <br/>
                        <c:if test="${not_enough_money}"><label class="text-danger">${not_enought_money_message}</label></c:if>
                        <div class="proceed-to-checkout">
                            <button class="btn btn-brand pill" type="submit">${checkout}</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </section>
</div>
<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/footer.jsp"/>
<%@ include file="../WEB-INF/jsp/action.jsp"%>
</body>
</html>
