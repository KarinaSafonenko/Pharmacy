<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>${users}</title>
    <%@ include file="../WEB-INF/jsp/style.jsp"%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="login" var = "login"/>
    <fmt:message bundle = "${bundle}" key ="no_users" var = "no_users"/>
    <fmt:message bundle = "${bundle}" key ="email" var = "email"/>
    <fmt:message bundle = "${bundle}" key ="surname" var = "surname"/>
    <fmt:message bundle = "${bundle}" key ="name" var = "name"/>
    <fmt:message bundle = "${bundle}" key ="patronymic" var = "patronymic"/>
    <fmt:message bundle = "${bundle}" key ="sex" var = "sex"/>
    <fmt:message bundle = "${bundle}" key ="role" var = "role"/>
    <fmt:message bundle = "${bundle}" key ="add_user" var = "add_user"/>
</head>
<body class="animsition">
<%@include file="../WEB-INF/jsp/admin_header.jsp"%>
<div class="shop-cart" id="page">
    <ctg:show-sub_header name="${users}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="woocommerce">
                <c:if test="${empty user_list}">
                    ${no_users}.
                </c:if>
                <c:if test="${not empty user_list}">
                    <form class="woocommerce-cart-form" method="GET" action="/jsp/add_user.jsp">
                        <table class="woocommerce-cart-table">
                            <thead>
                            <tr>
                                <th>${login}</th>
                                <th>${email}</th>
                                <th></th>
                                <th>${surname} ${name} ${patronymic}</th>
                                <th>${sex}</th>
                                <th>${role}</th>
                            </tr>
                            </thead>
                            <tbody>
                            <ctg:show-user users="${user_list}"/>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6">
                                    <div class="form-coupon organic-form">
                                        <div class="form-group update-cart">
                                            <button class="btn btn-brand pill" type="submit">${add_user}</button>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </form>
                </c:if>
            </div>
        </div>
    </section>
</div>
<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/footer.jsp"/>
<%@ include file="../WEB-INF/jsp/action.jsp"%>
</body>
</html>
