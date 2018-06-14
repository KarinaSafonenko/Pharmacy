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
    <fmt:message bundle = "${bundle}" key ="registration_login" var = "registration_login_message"/>
    <fmt:message bundle = "${bundle}" key ="next" var = "next_message"/>
    <fmt:message bundle = "${bundle}" key ="no_such_login" var = "no_such_login_message"/>
    <fmt:message bundle = "${bundle}" key ="forgot_password" var = "forgot_password_message"/>
</head>
<body class="animsition">
<%@ include file="../WEB-INF/jsp/header.jsp"%>
<div class="login" id="page">
    <ctg:show-sub_header name="${forgot_password_message}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="login-wrapper">
                <form name="login" method="POST" action="/ControllerServlet">
                    <div class="row">
                        <input type="hidden" name="command" value="forgot_password" />
                        <div class="form-group organic-form-2">
                            <label>${registration_login_message}:</label>
                            <input class="form-control" type="text" name="login">
                        </div>
                        <c:if test="${requestScope.no_such_login}"><label class="text-danger">${no_such_login_message}</label></c:if>
                    </div>
                    <div class="form-group footer-form">
                        <button class="btn btn-brand pill" type="submit">${next_message}</button>
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

