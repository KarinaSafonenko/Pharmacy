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
    <fmt:message bundle = "${bundle}" key ="register" var = "register"/>
    <fmt:message bundle = "${bundle}" key ="registration" var = "registration"/>
</head>
<body class="animsition">
<%@ include file="../WEB-INF/jsp/header.jsp"%>
<div class="login" id="page">
    <ctg:show-sub_header name="${registration}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="login-wrapper">
                <form name="registration" method="POST" action="/ControllerServlet">
                    <div class="row">
                        <input type="hidden" name="command" value="registration" />
                        <%@include file="../WEB-INF/jsp/user.jsp"%>
                    </div>
                    <div class="form-group footer-form">
                        <button class="btn btn-brand pill" type="submit">${register}</button>
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
