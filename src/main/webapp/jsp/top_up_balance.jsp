<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>${registration}</title>
    <%@ include file="../WEB-INF/jsp/style.jsp"%>

    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>

    <fmt:message bundle = "${bundle}" key ="top_up_the_balance" var = "top_up_the_balance"/>
    <fmt:message bundle = "${bundle}" key ="card_code" var = "code"/>
    <fmt:message bundle = "${bundle}" key ="card_number" var = "number"/>
    <fmt:message bundle = "${bundle}" key ="sum" var = "sum"/>
    <fmt:message bundle = "${bundle}" key ="incorrect_card_information" var = "incorrect_card_information"/>
    <fmt:message bundle = "${bundle}" key ="incorrect_sum" var = "incorrect_sum"/>
</head>
<body class="animsition">
<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/header.jsp"/>
<div class="login" id="page">
    <ctg:show-sub_header name="${top_up_the_balance}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="login-wrapper">
                <form name="top_up_the_balance" method="POST" action="/ControllerServlet">
                    <div class="row">
                        <input type="hidden" name="command" value="top_up_the_balance" />
                        <div class="form-group organic-form-2">
                            <label>${number}: </label>
                            <input class="form-control" type="number" name="card_number" min="0" value="${card_number}">
                        </div>
                        <div class="form-group organic-form-2">
                            <label>${code}: </label>
                            <input class="form-control" type="number" name="card_code" min="0" value="${card_code}">
                        </div>
                        <c:if test="${requestScope.wrong_card_information}"><label class="text-danger">${incorrect_card_information}</label></c:if>
                        <div class="form-group organic-form-2">
                            <label>${sum}: </label>
                            <input class="form-control" type="number" step="0.01" min="0" name="money_amount" value="${money_amount}">
                        </div>
                        <c:if test="${requestScope.wrong_sum}"><label class="text-danger">${incorrect_sum}</label></c:if>
                    </div>
                    <div class="form-group footer-form">
                        <button class="btn btn-brand pill" type="submit">${top_up_the_balance}</button>
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
