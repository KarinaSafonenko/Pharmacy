<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>${add_user}</title>
    <%@ include file="../WEB-INF/jsp/style.jsp"%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="add_user" var = "add_user"/>
    <fmt:message bundle = "${bundle}" key ="role" var = "role"/>
    <fmt:message bundle = "${bundle}" key ="client" var = "client"/>
    <fmt:message bundle = "${bundle}" key ="pharmacist" var = "pharmacist"/>
    <fmt:message bundle = "${bundle}" key ="doctor" var = "doctor"/>
    <fmt:message bundle = "${bundle}" key ="admin" var = "admin"/>
</head>
<body class="animsition">
<%@ include file="../WEB-INF/jsp/admin_header.jsp"%>
<div class="login" id="page">
    <ctg:show-sub_header name="${registration}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="login-wrapper">
                <form name="registration" method="POST" action="/ControllerServlet">
                    <div class="row">
                        <input type="hidden" name="command" value="add_user" />
                        <%@include file="../WEB-INF/jsp/user.jsp"%>
                        <div class="form-group organic-form-2">
                            <label>${role}:</label>
                            <div class="widget widget-control-header">
                                <div class="select-custom-wrapper">
                                    <select class="no-border" name="role">
                                        <option value="admin">${admin}</option>
                                        <option value="doctor">${doctor}</option>
                                        <option value="pharmacist">${pharmacist}</option>
                                        <option value="client">${client}</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group footer-form">
                        <button class="btn btn-brand pill" type="submit">${add_user}</button>
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
