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
    <fmt:message bundle = "${bundle}" key ="wrong_name_message" var = "wrong_name_message"/>
    <fmt:message bundle = "${bundle}" key ="duplicate_login_message" var = "duplicate_login_message"/>
    <fmt:message bundle = "${bundle}" key ="wrong_surname_message" var = "wrong_surname_message"/>
    <fmt:message bundle = "${bundle}" key ="wrong_patronymic_message" var = "wrong_patronymic_message"/>
    <fmt:message bundle = "${bundle}" key ="wrong_email_message" var = "wrong_email_message"/>
    <fmt:message bundle = "${bundle}" key ="wrong_login_message" var = "wrong_login_message"/>
    <fmt:message bundle = "${bundle}" key ="wrong_password_message" var = "wrong_password_message"/>
    <fmt:message bundle = "${bundle}" key ="different_passwords_message" var = "different_passwords_message"/>
    <fmt:message bundle = "${bundle}" key ="name" var = "name_message"/>
    <fmt:message bundle = "${bundle}" key ="surname" var = "surname_message"/>
    <fmt:message bundle = "${bundle}" key ="patronymic" var = "patronymic_message"/>
    <fmt:message bundle = "${bundle}" key ="sex" var = "sex_message"/>
    <fmt:message bundle = "${bundle}" key ="male" var = "male_message"/>
    <fmt:message bundle = "${bundle}" key ="female" var = "female_message"/>
    <fmt:message bundle = "${bundle}" key ="email" var = "email_message"/>
    <fmt:message bundle = "${bundle}" key ="repeat_password_message" var = "repeat_password"/>
    <fmt:message bundle = "${bundle}" key ="register" var = "register"/>
    <fmt:message bundle = "${bundle}" key ="login" var = "login_message"/>
    <fmt:message bundle = "${bundle}" key ="password_message" var = "password_message"/>
    <fmt:message bundle = "${bundle}" key ="registration" var = "registration"/>
</head>
<body class="animsition">
<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/header.jsp"/>
<div class="login" id="page">
    <ctg:show-sub_header name="${registration}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="login-wrapper">
                <form name="registration" method="POST" action="/ControllerServlet">
                    <div class="row">
                        <input type="hidden" name="command" value="registration" />
                        <div class="form-group organic-form-2">
                            <label>${surname_message}:</label>
                            <input class="form-control" type="text" name="surname" value="${requestScope.surname}">
                        </div>
                        <c:if test="${requestScope.wrong_surname}"><span style="color: red">${wrong_surname_message}</span></c:if>
                        <div class="form-group organic-form-2">
                            <label>${name_message}:</label>
                            <input class="form-control" type="text" name="name" value="${requestScope.name}">
                        </div>
                        <c:if test="${requestScope.wrong_name}"><span style="color: red">${wrong_name_message}</span></c:if>
                        <div class="form-group organic-form-2">
                            <label>${patronymic_message}:</label>
                            <input class="form-control" type="text" name="patronymic" value="${requestScope.patronymic}">
                        </div>
                        <c:if test="${requestScope.wrong_patronymic}"><span style="color: red">${wrong_patronymic_message}</span></c:if>
                        <div class="form-group organic-form-2">
                            <label>${sex_message}:</label>
                        <div class="widget widget-control-header">
                            <div class="select-custom-wrapper">
                                <select class="no-border" name="sex">
                                    <option value="male">${male_message}</option>
                                    <option value="female">${female_message}</option>
                                </select>
                            </div>
                        </div>
                        </div>
                        <div class="form-group organic-form-2">
                            <label>${email_message}:</label>
                            <input class="form-control" type="email" name="mail" value="${requestScope.mail}">
                        </div>
                        <c:if test="${requestScope.wrong_email}"><span style="color: red">${wrong_email_message}</span></c:if>
                        <div class="form-group organic-form-2">
                            <label>${login_message}:</label>
                            <input class="form-control" type="text" name="login" value="${requestScope.login}">
                        </div>
                        <c:if test="${requestScope.wrong_login}"><span style="color: red">${wrong_login_message}</span></c:if>
                        <c:if test="${requestScope.duplicate_login}"><span style="color: red">${duplicate_login_message}</span></c:if>
                        <div class="form-group organic-form-2">
                            <label>${password_message}:</label>
                            <input class="form-control" type="password" name="password">
                        </div>
                        <c:if test="${requestScope.wrong_password}"><span style="color: red">${wrong_password_message}</span></c:if>
                        <div class="form-group organic-form-2">
                            <label>${repeat_password}:</label>
                            <input class="form-control" type="password" name="repeat_password">
                        </div>
                        <c:if test="${requestScope.different_passwords}"><span style="color: red">${different_passwords_message}</span></c:if>
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
