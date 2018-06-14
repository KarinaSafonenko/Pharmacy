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
    <fmt:message bundle = "${bundle}" key ="wrong_login_or_password" var = "wrong_login_or_password"/>
    <fmt:message bundle = "${bundle}" key ="login" var = "login"/>
    <fmt:message bundle = "${bundle}" key ="password_message" var = "password"/>
    <fmt:message bundle = "${bundle}" key ="log_in" var = "log_in"/>
    <fmt:message bundle = "${bundle}" key ="account" var = "account"/>
    <fmt:message bundle = "${bundle}" key ="forgot_password" var = "forgot_password_message"/>
</head>
<body class="animsition">
<%@ include file="../WEB-INF/jsp/header.jsp"%>
<div class="login" id="page">
    <ctg:show-sub_header name="${account}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="login-wrapper">
                <form name="login" method="POST" action="/ControllerServlet">
                    <div class="row">
                        <div class="form-group organic-form-2">
                            <label>Улица: </label>
                            <input class="form-control" type="text" name="street">
                        </div>
                        <div class="form-group organic-form-2">
                            <label>Дом: </label>
                            <input class="form-control" type="number" name="house" min="1">
                        </div>
                        <div class="form-group organic-form-2">
                            <label>Квартира: </label>
                            <input class="form-control" type="number" name="flat" min="1">
                        </div>
                        <div class="form-group organic-form-2">
                            <label>Оплата:</label>
                            <div class="widget widget-control-header">
                                <div class="select-custom-wrapper">
                                    <select class="no-border" name="sex">
                                        <option value="CARD">Карта</option>
                                        <option value="CREDIT">Кредит</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group organic-form-2">
                            <label>Номер карточки: </label>
                            <input class="form-control" type="number" name="card_number" min="0">
                        </div>
                        <div class="form-group organic-form-2">
                            <label>Код карточки: </label>
                            <input class="form-control" type="number" name="card_code" min="0">
                        </div>
                        <div class="proceed-to-checkout">
                            <a class="btn btn-brand pill" href="#">Оформить заказ</a>
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
