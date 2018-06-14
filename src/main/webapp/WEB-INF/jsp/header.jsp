<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>

    <fmt:message bundle = "${bundle}" key ="en" var = "en"/>
    <fmt:message bundle = "${bundle}" key ="ru" var = "ru"/>
    <fmt:message bundle = "${bundle}" key ="welcome" var = "welcome_message"/>
    <fmt:message bundle = "${bundle}" key ="home" var = "home"/>
    <fmt:message bundle = "${bundle}" key ="profile" var = "profile"/>
    <fmt:message bundle = "${bundle}" key ="basket" var = "basket"/>
    <fmt:message bundle = "${bundle}" key ="language" var = "language"/>
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
                            <a href="/ControllerServlet?command=change_latest_path&path=index.jsp">${home}</a>
                        </li>
                        <c:if test="${not empty sessionScope.name}">
                        <li>
                            <a href="/ControllerServlet?command=show_profile">${profile}</a>
                        </li>
                        <li>
                            <a href="/ControllerServlet?command=show_basket">${basket}</a>
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
                <aside class="right">
                    <div class="widget widget-control-header">
                    <form name="login" method="POST" action="/ControllerServlet">
                            <input type="hidden" name="command" value="change_locale"/>
                            <input type="hidden" name="locale" value="ru_RU" />
                            <input type="hidden" name="url" value="${pageContext.request.requestURL}" />
                            <input type="hidden" name="query" value="${pageContext.request.queryString.toString()}" />
                            <button class="btn-icon btn-icon-brand" type="submit">
                               ${ru}
                            </button>
                        </form>
                    </div>
                    <div class="widget widget-control-header">
                    <form name="login" method="POST" action="/ControllerServlet">
                            <input type="hidden" name="command" value="change_locale"/>
                            <input type="hidden" name="locale" value="en_EN" />
                            <input type="hidden" name="url" value="${pageContext.request.requestURL}" />
                            <input type="hidden" name="query" value="${pageContext.request.queryString.toString()}" />
                            <button class="btn-icon btn-icon-brand" type="submit">
                                ${en}
                            </button>
                        </form>
                    </div>
                </aside>
            </div>
        </div>
    </div>
</header>
</body>
</html>