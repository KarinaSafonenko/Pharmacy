<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags" %>

<html>
  <head>
      <title>${home}</title>
      <%@ include file="../WEB-INF/jsp/style.jsp"%>
      <fmt:setLocale value="${sessionScope.locale}"/>
      <fmt:setBundle basename="property.message" var="bundle" scope="request"/>
      <fmt:message bundle = "${bundle}" key ="our_products" var = "our_products"/>
      <fmt:message bundle = "${bundle}" key ="vitamins" var = "vitamins"/>
      <fmt:message bundle = "${bundle}" key ="allergy" var = "allergy"/>
      <fmt:message bundle = "${bundle}" key ="heart" var = "heart"/>
      <fmt:message bundle = "${bundle}" key ="antibiotics" var = "antibiotics"/>
      <fmt:message bundle = "${bundle}" key ="cold" var = "cold"/>
      <fmt:message bundle = "${bundle}" key ="cosmetics" var = "cosmetics"/>
      <fmt:message bundle = "${bundle}" key ="popular" var = "popular"/>
      <fmt:message bundle = "${bundle}" key ="copyright" var = "copyright"/>
      <fmt:message bundle = "${bundle}" key ="trust" var = "trust"/>
      <fmt:message bundle = "${bundle}" key ="care" var = "care"/>
      <fmt:message bundle = "${bundle}" key ="log_in" var = "log_in"/>
      <fmt:message bundle = "${bundle}" key ="register" var = "register"/>
  </head>
  <body class="animsition">
  <%@ include file="../WEB-INF/jsp/header.jsp"%>
  <div class="home-4" id="page">
      <section class="boxed u-mb60">
        <div class="banner-slider-4 rev_slider" id="slider-4">
          <ul>
            <li data-transition="fade">
              <img src="../images/slider/slider.jpg" alt="" data-bgposition="center center" data-bgfit="cover" data-bgrepeat="no-repeat" data-bgparallax="10">
              <div class="tp-caption" data-x="left" data-y="center" data-voffset="-144" data-width="['800','600','400','320']" data-transform_in="y:-80px;opacity:0;s:800;e:easeInOutCubic;" data-transform_out="y:-80px;opacity:0;s:300;" data-start="1000">
                <h4>Healthy life</h4>
              </div>
              <div class="tp-caption" data-x="left" data-y="center" data-voffset="-60" data-width="['800','600','400','320']" data-whitespace="normal" data-transform_in="y:80px;opacity:0;s:800;e:easeInOutCubic;" data-transform_out="y:80px;opacity:0;s:300;" data-start="1400">
                <h2>${trust}</h2>
              </div>
              <div class="tp-caption" data-x="left" data-y="center" data-voffset="35" data-width="['800','600','400','320']" data-whitespace="normal" data-transform_in="y:80px;opacity:0;s:800;e:easeInOutCubic;" data-transform_out="y:80px;opacity:0;s:300;" data-start="1600">
                <p>${care}</p>
              </div>
                <c:if test="${empty sessionScope.name}">
                <div class="tp-caption" data-x="left" data-y="center" data-voffset="120" data-width="['800','600','400','320']" data-transform_in="y:100px;opacity:0;s:800;e:easeInOutCubic;" data-transform_out="y:200px;opacity:0;s:300;" data-start="1800">
                 <div class="text-center">
                   <a class="btn btn-brand-pink pill" href="${pageContext.request.contextPath}/jsp/login.jsp">${log_in}</a>
                 </div>
                </div>
                </c:if>
            <c:if test="${empty sessionScope.name}">
                <div class="tp-caption" data-x="center" data-y="center" data-voffset="120" data-width="['800','600','400','320']" data-transform_in="y:100px;opacity:0;s:800;e:easeInOutCubic;" data-transform_out="y:200px;opacity:0;s:300;" data-start="1800">
                <div class="text-center">
                      <a class="btn btn-brand-pink pill" href="${pageContext.request.contextPath}/jsp/registration.jsp">${register}</a>
                </div>
                </div>
            </c:if>
            </li>
          </ul>
        </div>
      </section>
      <section class="boxed">
	  <div class="heading-wrapper text-center">
          <div class="heading-style-4">
            <h3>${our_products}</h3>
              <img src="../images/icons/seperator.png" alt="seperator">
          </div>
        </div>
        <div class="product-category-grid-style-3">
          <div class="row row-mg-20">
            <div class="col-lg-3 col-md-6 col-sm-6">
              <a href="/ControllerServlet?command=show_category_products&category=vitamins">
                <figure class="product-category-item-3">
                  <div class="thumbnail">
                    <img src="../images/category-product/vitamin.jpg" alt="" />
                  </div>
                  <figcaption>
                    <h3>#${vitamins}</h3>
                  </figcaption>
                </figure>
              </a>
            </div>
            <div class="col-lg-3 col-md-6 col-sm-6">
              <a href="/ControllerServlet?command=show_category_products&category=allergy">
                <figure class="product-category-item-3">
                  <div class="thumbnail">
                    <img src="../images/category-product/allergy.jpg" alt="" />
                  </div>
                  <figcaption>
                    <h3>#${allergy}</h3>
                  </figcaption>
                </figure>
              </a>
            </div>
			<div class="col-lg-3 col-md-6 col-sm-6">
              <a href="/ControllerServlet?command=show_category_products&category=heart">
                <figure class="product-category-item-3">
                  <div class="thumbnail">
                    <img src="../images/category-product/heart.jpg" alt="" />
                  </div>
                  <figcaption>
                    <h3>#${heart}</h3>
                  </figcaption>
                </figure>
              </a>
            </div>
            <div class="col-lg-3 col-md-6 col-sm-6">
              <a href="/ControllerServlet?command=show_category_products&category=antibiotics">
                <figure class="product-category-item-3">
                  <div class="thumbnail">
                    <img src="../images/category-product/anti.jpg" alt="" />
                  </div>
                  <figcaption>
                    <h3>#${antibiotics}</h3>
                  </figcaption>
                </figure>
              </a>
            </div>
          </div>
        </div>
        <div class="product-category-grid-style-3">
          <div class="row row-mg-20">
           <div class="col-lg-3 col-md-6 col-sm-6">  
            </div>
            <div class="col-lg-3 col-md-6 col-sm-6">
              <a href="/ControllerServlet?command=show_category_products&category=cold">
                <figure class="product-category-item-3">
                  <div class="thumbnail">
                    <img src="../images/category-product/cold.jpg" alt="" />
                  </div>
                  <figcaption>
                    <h3>#${cold}</h3>
                  </figcaption>
                </figure>
              </a>
            </div>
			<div class="col-lg-3 col-md-6 col-sm-6">
              <a href="/ControllerServlet?command=show_category_products&category=cosmetics">
                <figure class="product-category-item-3">
                  <div class="thumbnail">
                    <img src="../images/category-product/cosmetics.jpg" alt="" />
                  </div>
                  <figcaption>
                    <h3>#${cosmetics}</h3>
                  </figcaption>
                </figure>
              </a>
            </div>
			<div class="col-lg-3 col-md-6 col-sm-6">
            </div>
          </div>
        </div>
      </section>
      <section class="boxed">
		<div class="heading-wrapper text-center">
          <div class="heading-style-4">
            <img src="../images/icons/seperator.png" alt="seperator">
          </div>
        </div>
        <div class="row">
          <div class="product-category-item-4">
            <div class="col-md-6">
              <div class="banner-category">
                <img src="../images/product/popular.jpg">
                <div class="inner text-center">
                  <h3>${popular}</h3>
                </div>
              </div>
            </div>
            <div class="col-md-7">
              <div class="row">
                  <ctg:show-popular products="${products}" number="${popular_product_number}"/>
              </div>
            </div>
          </div>
        </div>
      </section>
  </div>
  <ctg:show-dialog products="${products}" number="${popular_product_number}" buttons="true"/>
  <jsp:include page="${pageContext.request.contextPath}/WEB-INF/jsp/footer.jsp"/>
  <%@ include file="../WEB-INF/jsp/action.jsp"%>
  <%@ include file="../WEB-INF/jsp/main_style.jsp"%>
  </body>
</html>
