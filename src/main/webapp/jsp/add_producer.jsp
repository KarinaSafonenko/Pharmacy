<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>${add_producer}</title>
    <%@ include file="../WEB-INF/jsp/style.jsp"%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="country" var = "country_label"/>
    <fmt:message bundle = "${bundle}" key ="add_producer" var = "add_producer"/>
    <fmt:message bundle = "${bundle}" key ="producer_name" var = "producer_name"/>
    <fmt:message bundle = "${bundle}" key ="verify_data" var = "verify_data"/>
</head>
<body class="animsition">
<%@ include file="../WEB-INF/jsp/pharmacist_header.jsp"%>
<div class="login" id="page">
    <ctg:show-sub_header name="${add_producer}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="login-wrapper">
                <form name="login" method="POST" action="/ControllerServlet">
                    <div class="row">
                        <input type="hidden" name="command" value="add_producer" />
                        <div class="form-group organic-form-2">
                            <label>${producer_name}:</label>
                            <input class="form-control" type="text" name="name">
                        </div>
                        <div class="form-group organic-form-2">
                            <label>${country_label}:</label>
                            <input class="form-control" type="text" name="country">
                        </div>
                        <c:if test="${incorrect}"><label class="text-danger">${verify_data}</label></c:if>
                        <div class="form-group footer-form">
                            <button class="btn btn-brand pill" type="submit">${add_producer}</button>
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