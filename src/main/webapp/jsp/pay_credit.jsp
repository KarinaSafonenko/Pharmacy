<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>${pay}</title>
    <%@ include file="../WEB-INF/jsp/style.jsp"%>

    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>

    <fmt:message bundle = "${bundle}" key ="pay" var = "pay"/>
    <fmt:message bundle = "${bundle}" key ="sum" var = "sum"/>
    <fmt:message bundle = "${bundle}" key ="sum_problem" var = "sum_problem"/>
    <fmt:message bundle = "${bundle}" key ="obligation" var = "obligation_message"/>
</head>
<body class="animsition">
<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/header.jsp"/>
<div class="login" id="page">
    <ctg:show-sub_header name="${pay}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="login-wrapper">
                <form name="top_up_the_balance" method="POST" action="/ControllerServlet">
                    <div class="row">
                        <input type="hidden" name="command" value="pay_credit" />
                        <input type="hidden" name="credit_id" value="${credit_id}" />
                        <input type="hidden" name="obligation" value="${obligation}" />
                        <div class="form-group organic-form-2">
                            <label>${obligation_message}: ${obligation} $</label>
                        </div>
                        <%@include file="../WEB-INF/jsp/card.jsp"%>
                        <div class="form-group organic-form-2">
                            <label>${sum}: </label>
                            <input class="form-control" type="number" step="0.01" min="0" name="money_amount" value="${money_amount}">
                        </div>
                        <c:if test="${requestScope.wrong_sum}"><label class="text-danger">${sum_problem}</label></c:if>
                    </div>
                    <div class="form-group footer-form">
                        <button class="btn btn-brand pill" type="submit">${pay}</button>
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