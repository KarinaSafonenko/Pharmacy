<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>${pay}</title>
    <%@ include file="../WEB-INF/jsp/style.jsp"%>

    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>

    <fmt:message bundle = "${bundle}" key ="recipes" var = "recipes"/>
    <fmt:message bundle = "${bundle}" key ="end_date" var = "end_date_message"/>
    <fmt:message bundle = "${bundle}" key ="number" var = "number_message"/>
    <fmt:message bundle = "${bundle}" key ="verify_data" var = "verify_data"/>
    <fmt:message bundle = "${bundle}" key ="save" var = "save"/>
</head>
<body class="animsition">
<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/doctor_header.jsp"/>
<div class="login" id="page">
    <ctg:show-sub_header name="${recipes}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="login-wrapper">
                <form name="extend_recipe" method="POST" action="/ControllerServlet">
                    <div class="row">
                        <input type="hidden" name="command" value="extend_recipe" />
                        <input type="hidden" name="recipe_id" value="${recipe_id}" />
                        <div class="form-group organic-form-2">
                            <label>${number_message}: </label>
                            <input class="form-control" type="number" step="1" min="1" name="number" value="${number}"/>
                        </div>
                        <div class="form-group organic-form-2">
                            <label>${end_date_message}: </label><br/>
                            <input type="date" name="end_date" value="${end_date}"/>
                        </div>
                        <c:if test="${requestScope.incorrect}"><label class="text-danger">${verify_data}</label></c:if>
                    </div>
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