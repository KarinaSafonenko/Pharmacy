<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="welcome" var = "welcome_message"/>
    <fmt:message bundle = "${bundle}" key ="recipes" var = "recipes"/>
    <fmt:message bundle = "${bundle}" key ="orders" var = "orders"/>
    <fmt:message bundle = "${bundle}" key ="credits" var = "credits"/>
    <fmt:message bundle = "${bundle}" key ="home" var = "home"/>
    <fmt:message bundle = "${bundle}" key ="profile" var = "profile"/>
    <fmt:message bundle = "${bundle}" key ="basket" var = "basket"/>
    <fmt:message bundle = "${bundle}" key ="logout" var = "logout"/>
</head>
<body>
<header class="header-style-4">
    <a class="brand-logo animsition-link">
        <img class="img-responsive" src="${pageContext.request.contextPath}/images/healthy.jpg" alt="logo" />
    </a>
    <div class="container">
        <div class="row">
            <div class="header-4-inner">
                <nav>
                    <ul class="menu hidden-xs">
                        <li>
                            <a href="/ControllerServlet?command=form_main_page">${home}</a>
                        </li>
                        <c:if test="${not empty sessionScope.name}">
                        <li>
                            <a href="/ControllerServlet?command=show_profile">${profile}</a>
                        </li>
                        <li>
                            <a href="/ControllerServlet?command=show_basket">${basket}</a>
                        </li>
                            <li>
                                <a href="/ControllerServlet?command=show_user_recipes">${recipes}</a>
                            </li>
                            <li>
                                <a href="/ControllerServlet?command=show_user_orders">${orders}</a>
                            </li>
                            <li>
                                <a href="/ControllerServlet?command=show_user_credits">${credits}</a>
                            </li>
                        <li>
                                ${welcome_message}, ${sessionScope.name} ${sessionScope.surname} !
                        </li>
                        <li>
                            <a href="/ControllerServlet?command=logout">${logout}</a>
                        </li>
                        </c:if>
                    </ul>
                </nav>
                <%@include file="language.jsp"%>
            </div>
        </div>
    </div>
</header>
</body>
</html>