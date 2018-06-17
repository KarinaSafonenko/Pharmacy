<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
<head>
    <title>${add_product}</title>
    <%@ include file="../WEB-INF/jsp/style.jsp"%>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
    <fmt:message bundle = "${bundle}" key ="add_product" var = "add_product"/>
    <fmt:message bundle = "${bundle}" key ="product_name" var = "product_name"/>
    <fmt:message bundle = "${bundle}" key ="verify_data" var = "verify_data"/>
    <fmt:message bundle = "${bundle}" key ="category" var = "category_label"/>
    <fmt:message bundle = "${bundle}" key ="vitamins" var = "vitamins"/>
    <fmt:message bundle = "${bundle}" key ="allergy" var = "allergy"/>
    <fmt:message bundle = "${bundle}" key ="antibiotics" var = "antibiotics"/>
    <fmt:message bundle = "${bundle}" key ="heart" var = "heart"/>
    <fmt:message bundle = "${bundle}" key ="cold" var = "cold"/>
    <fmt:message bundle = "${bundle}" key ="cosmetics" var = "cosmetics"/>
    <fmt:message bundle = "${bundle}" key ="recipe_need" var = "recipe_need_label"/>
    <fmt:message bundle = "${bundle}" key ="yes" var = "yes"/>
    <fmt:message bundle = "${bundle}" key ="no" var = "no"/>
    <fmt:message bundle = "${bundle}" key ="description" var = "description_label"/>
    <fmt:message bundle = "${bundle}" key ="load_image" var = "load_image"/>
    <fmt:message bundle = "${bundle}" key ="in_pack" var = "in_pack"/>
    <fmt:message bundle = "${bundle}" key ="dosage" var = "dosage_label"/>
    <fmt:message bundle = "${bundle}" key ="mg" var = "mg"/>
    <fmt:message bundle = "${bundle}" key ="price" var = "price_label"/>
    <fmt:message bundle = "${bundle}" key ="in_stock" var = "in_stock"/>
    <fmt:message bundle = "${bundle}" key ="producer" var = "producer"/>
</head>
<body class="animsition">
<%@ include file="../WEB-INF/jsp/pharmacist_header.jsp"%>
<div class="login" id="page">
    <ctg:show-sub_header name="${add_product}"/>
    <section class="boxed-sm">
        <div class="container">
            <div class="login-wrapper">
                <form action="/FileUploadServlet" method="POST" enctype="multipart/form-data">
                    <div class="row">
                        <input type="hidden" name="command" value="add_product" />
                        <div class="form-group organic-form-2">
                            <label>${product_name}:</label>
                            <input class="form-control" type="text" name="name" value="${requestScope.name}">
                        </div>
                        <div class="form-group organic-form-2">
                            <label>${category_label}:</label>
                            <div class="widget widget-control-header">
                                <div class="select-custom-wrapper">
                                    <select class="no-border" name="category">
                                        <option value="vitamins">${vitamins}</option>
                                        <option value="allergy">${allergy}</option>
                                        <option value="antibiotics">${antibiotics}</option>
                                        <option value="heart">${heart}</option>
                                        <option value="cold">${cold}</option>
                                        <option value="cosmetics">${cosmetics}</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group organic-form-2">
                            <label>${recipe_need_label}:</label>
                            <div class="widget widget-control-header">
                                <div class="select-custom-wrapper">
                                    <select class="no-border" name="recipe_need">
                                        <option value="true">${yes}</option>
                                        <option value="false">${no}</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group organic-form-2">
                            <label>${in_pack}:</label>
                            <input class="form-control" type="number" name="quantity" min="1" value="${quantity}">
                        </div>
                        <div class="form-group organic-form-2">
                            <label>${dosage_label}:</label>
                            <input class="form-control" type="number" name="dosage" min="0" value="${dosage}">
                        </div>
                        <div class="form-group organic-form-2">
                            <label>${price_label}:</label>
                            <input class="form-control" type="number" step="0.01" min="0" name="price" value="${price}">
                        </div>
                        <div class="form-group organic-form-2">
                            <label>${in_stock}:</label>
                            <input class="form-control" type="number" name="amount" min="1" value="${amount}">
                        </div>
                        <div class="form-group organic-form-2">
                            <label>${producer}:</label>
                            <div class="widget widget-control-header">
                                <div class="select-custom-wrapper">
                                    <select class="no-border" name="producer_id">
                                        <c:forEach items="${producers}" var="current">
                                        <option value="${current.id}">${current.name} [${current.country}]</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-group organic-form-2">
                            <label>${description_label}:</label>
                        <textarea class="form-control" type="text" name="description" rows="5" cols="45">${description}</textarea>
                        </div>
                        <label for="file-upload" class="custom-file-upload">
                            <i class="fa fa-cloud-upload"></i> ${load_image}
                        </label>
                        <input id="file-upload" type="file" name="image_path"/>
                            <c:if test="${incorrect}"><label class="text-danger">${verify_data}</label></c:if>
                        <div class="form-group footer-form">
                            <button class="btn btn-brand pill" type="submit">${add_product}</button>
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