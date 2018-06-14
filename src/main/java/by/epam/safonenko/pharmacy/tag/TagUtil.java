package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.util.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

final class TagUtil {
    private static final String IMAGE_WRAPPER = "<div class=\"img-wrapper\">\n" +
            "                                            <img class=\"img-responsive\" src=\"%s\" alt=\"product thumbnail\">\n" +
            "                                            <div class=\"product-control-wrapper top-right\">\n" +
            "                                                <div class=\"wrapper-control-item\">\n" +
            "                                                    <a class=\"js-quick-view\" type=\"button\" data-toggle=\"modal\" data-target=\"#%d\" >\n" +
            "                                                        <span class=\"lnr lnr-eye\"></span>\n" +
            "                                                    </a>\n" +
            "                                                </div>\n" +
            "                                            </div>\n" +
            "                                        </div>";

    private static final String CAPTION = "<figcaption class=\"desc text-center\">\n" +
            "                                <h3>\n" +
            "                                  <a class=\"product-name\">%s</a>\n" +
            "                                </h3>\n" +
            "                              </figcaption>";

    private static final String PRODUCT_NAME = "<td class=\"product-name\">\n" +
            "                                <a class=\"product-name\">%s</a>\n" +
            "                                   </td>";

    private static final String CELL = "<td>%s</td>";

    private TagUtil(){}

    static String formImageWrapper(String image, int packId){
        return String.format(IMAGE_WRAPPER, image, packId);
    }

    static String formCaption(String name){
        return String.format(CAPTION, name);
    }

    static String formCell(String text){
        return String.format(CELL, text);
    }

    static String formProductName(String name){
        return String.format(PRODUCT_NAME, name);
    }

    static String getLanguage(PageContext pageContext){
        HttpServletRequest request = ((HttpServletRequest) pageContext.getRequest());
        return (String) request.getSession().getAttribute(SessionAttribute.LOCALE.name().toLowerCase());
    }



}
