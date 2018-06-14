<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>${profile}</title>
    <%@ include file="../WEB-INF/jsp/style.jsp"%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="profile" var = "profile"/>
    <fmt:message bundle = "${bundle}" key ="save" var = "save"/>
    <fmt:message bundle = "${bundle}" key ="balance" var = "balance"/>
    <fmt:message bundle = "${bundle}" key ="login" var = "login"/>
    <fmt:message bundle = "${bundle}" key ="top_up_the_balance" var = "top_up_the_balance"/>
</head>
<body class="animsition">
<%@ include file="../WEB-INF/jsp/header.jsp"%>
<div class="login" id="page">
    <ctg:show-sub_header name="${profile}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="login-wrapper">
                <form name="change_user_info" method="POST" action="/ControllerServlet">
                    <div class="row">
                        <input type="hidden" name="command" value="change_user_info" />
                        <div class="form-group organic-form-2">
                            <label>${login}: ${sessionScope.login} [${role}]</label>
                        </div>
                        <div class="form-group organic-form-2">
                            <label>${balance}: ${money_amount} $</label>
                        </div>
                        <a class="pull-right" href="${pageContext.request.contextPath}/jsp/top_up_balance.jsp">${top_up_the_balance}</a>
                        <%@ include file="../WEB-INF/jsp/initials.jsp"%>
                        <ctg:show-sex sex="${sex}"/>
                        <div class="form-group footer-form">
                            <button class="btn btn-brand pill" type="submit">${save}</button>
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
