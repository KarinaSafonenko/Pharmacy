<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="en" var = "en"/>
    <fmt:message bundle = "${bundle}" key ="ru" var = "ru"/>
</head>
<body>
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
</body>
</html>
