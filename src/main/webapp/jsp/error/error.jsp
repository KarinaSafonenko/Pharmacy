<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="property.message" var="bundle" scope="request"/>

<fmt:message bundle = "${bundle}" key ="error_message" var = "error_message"/>
<fmt:message bundle = "${bundle}" key ="oops" var = "oops"/>


<html>
<head>
    <title>${oops}</title>
    <%@ include file="../../WEB-INF/jsp/style.jsp"%>
</head>
<body class="animsition">
<div class="page-404" id="page">
    <jsp:include page="../../WEB-INF/jsp/header.jsp"/>
    <section class="boxed-sm section-404-wrapper">
        <div class="container">
            <div class="page-404-wrapper">
                <h3>${oops}</h3>
                <hr class="style-one">
                <p>${error_message}</p>
            </div>
        </div>
    </section>
</div>
<jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/footer.jsp"/>
<%@ include file="../../WEB-INF/jsp/action.jsp"%></body>
</html>
