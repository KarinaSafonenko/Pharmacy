<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
  <head>
    <title>${login}</title>
      <%@ include file="../WEB-INF/jsp/style.jsp"%>
      <fmt:setLocale value="${sessionScope.locale}"/>
      <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
      <fmt:message bundle = "${bundle}" key ="wrong_login_or_password" var = "wrong_login_or_password"/>
      <fmt:message bundle = "${bundle}" key ="login" var = "login"/>
      <fmt:message bundle = "${bundle}" key ="password_message" var = "password"/>
      <fmt:message bundle = "${bundle}" key ="log_in" var = "log_in"/>
      <fmt:message bundle = "${bundle}" key ="account" var = "account"/>
      <fmt:message bundle = "${bundle}" key ="forgot_password" var = "forgot_password_message"/>
  </head>
  <body class="animsition">
  <jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/header.jsp"/>
  <div class="login" id="page">
        <ctg:show-sub_header name="${account}"/>
      <section class="boxed-sm">
        <div class="container">
          <div class="login-wrapper">
              <form name="login" method="POST" action="/ControllerServlet">
              <div class="row">
              <h3>${entry}</h3>
                  <input type="hidden" name="command" value="login" />
                  <div class="form-group organic-form-2">
                  <label>${login}:</label>
                  <input class="form-control" type="text" name="login">
                </div>
                <div class="form-group organic-form-2">
                  <label>${password}:</label>
                  <input class="form-control" type="Password" name="password">
                </div>
                  <input type="date"/>
                  <label for="file-upload" class="custom-file-upload">
                      <i class="fa fa-cloud-upload"></i> Custom Upload
                  </label>
                  <input id="file-upload" type="file"/>
                  <c:if test="${login_failed}"><label class="text-danger">${wrong_login_or_password}</label></c:if>
                  <a class="pull-right" href="/ControllerServlet?command=change_latest_path&path=/jsp/forgot_password.jsp">${forgot_password_message}</a>
                  <div class="form-group footer-form">
                  <button class="btn btn-brand pill" type="submit">${log_in}</button>
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
