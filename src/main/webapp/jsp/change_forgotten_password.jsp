<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>${forgot_password_message}</title>
    <%@ include file="../WEB-INF/jsp/style.jsp"%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="forgot_password" var = "forgot_password"/>
    <fmt:message bundle = "${bundle}" key ="forgot_password_message" var = "forgot_password_message"/>
    <fmt:message bundle = "${bundle}" key ="wrong_password_message" var = "wrong_password_message"/>
    <fmt:message bundle = "${bundle}" key ="different_passwords_message" var = "different_passwords_message"/>
    <fmt:message bundle = "${bundle}" key ="new_password" var = "new_password"/>
    <fmt:message bundle = "${bundle}" key ="repeat_password_message" var = "repeat_password"/>
    <fmt:message bundle = "${bundle}" key ="wrong_confirmation_code" var = "wrong_confirmation_message"/>
    <fmt:message bundle = "${bundle}" key ="change_password" var = "change_password_message"/>
</head>
<body class="animsition">
<%@ include file="../WEB-INF/jsp/header.jsp"%>
<div class="login" id="page">
    <ctg:show-sub_header name="${forgot_password}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="login-wrapper">
                <form name="login" method="POST" action="/ControllerServlet">
                    <div class="row">
                        <input type="hidden" name="command" value="change_forgotten_password" />
                        <div class="form-group organic-form-2">
                            <label>${forgot_password_message}:</label>
                            <input class="form-control" type="text" name="confirmation_code" value="${requestScope.confirmation_code}">
                        </div>
                        <c:if test="${requestScope.confirmation_failed}"><label class="text-danger">${wrong_confirmation_message}</label></c:if>
                        <div class="form-group organic-form-2">
                            <label>${new_password}: </label>
                            <input class="form-control" type="Password" name="password">
                        </div>
                        <c:if test="${requestScope.wrong_password}"><label class="text-danger">${wrong_password_message}</label></c:if>
                        <div class="form-group organic-form-2">
                            <label>${repeat_password}: </label>
                            <input class="form-control" type="Password" name="repeat_password">
                        </div>
                        <c:if test="${requestScope.different_passwords}"><label class="text-danger">${different_passwords_message}</label></c:if>
                    </div>
                    <div class="form-group footer-form">
                        <button class="btn btn-brand pill" type="submit">${change_password_message}</button>
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