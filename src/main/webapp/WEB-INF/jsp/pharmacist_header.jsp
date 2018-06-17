<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="welcome" var = "welcome_message"/>
    <fmt:message bundle = "${bundle}" key ="profile" var = "profile"/>
    <fmt:message bundle = "${bundle}" key ="logout" var = "logout"/>
    <fmt:message bundle = "${bundle}" key ="shop" var = "shop"/>
</head>
<body>
<header class="header-style-4">
    <div class="container">
        <div class="row">
            <div class="header-4-inner">
                <nav>
                    <ul class="menu hidden-xs">
                        <li>
                            <a href="/ControllerServlet?command=show_profile">${profile}</a>
                        </li>
                        <li>
                            <a href="/ControllerServlet?command=show_all_products">${shop}</a>
                        </li>
                        <li>
                            ${welcome_message}, ${sessionScope.name} ${sessionScope.surname} !
                        </li>
                        <li>
                            <a href="/ControllerServlet?command=logout">${logout}</a>
                        </li>
                    </ul>
                </nav>
                <%@include file="language.jsp"%>
            </div>
        </div>
    </div>
</header>
</body>
</html>