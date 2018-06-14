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

    <fmt:message bundle = "${bundle}" key ="wrong_confirmation_code" var = "wrong_confirmation_message"/>
    <fmt:message bundle = "${bundle}" key ="confirmation_message" var = "confirmation_message"/>
    <fmt:message bundle = "${bundle}" key ="next" var = "next"/>
    <fmt:message bundle = "${bundle}" key ="forgot_password" var = "forgot_password"/>
</head>
<body class="animsition">
<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/header.jsp"/>
<div class="login" id="page">
    <ctg:show-sub_header name="${forgot_password}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="login-wrapper">
                <form name="confirmation" method="POST" action="/ControllerServlet">
                    <div class="row">
                        <input type="hidden" name="command" value="confirmation" />
                        <div class="form-group organic-form-2">
                            <label>${confirmation_message}: </label>
                            <input class="form-control" type="text" name="confirmation_code">
                        </div>
                        <c:if test="${confirmation_failed}"><label class="text-danger">${wrong_confirmation_message}</label></c:if>
                    </div>
                    <div class="form-group footer-form">
                        <button class="btn btn-brand pill" type="submit">${next}</button>
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