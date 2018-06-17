package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.util.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import java.math.BigDecimal;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class TagUtil {
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

    private static final String HREF = "<div class=\"product-meta\">\n" +
            "                                 <p class=\"posted-in\">\n" +
            "                                   %s\n" +
            "                                 </p>\n" +
            "                            </div>";
    private static String SUM = "$%5.2f";
    private static final String CELL = "<td>%s</td>";
    private static final String DATE = "dd.MM.yyyy";

    private TagUtil(){}

    static String formImageWrapper(String image, int medicineId){
        return String.format(IMAGE_WRAPPER, image, medicineId);
    }

    static String formCaption(String name){
        return String.format(CAPTION, name);
    }

    static String formCell(String text){
        return String.format(CELL, text);
    }

    static String formHref(String text){
        return String.format(HREF, text);
    }

    static String formProductName(String name){
        return String.format(PRODUCT_NAME, name);
    }

    static String formSum(BigDecimal sum){
        return String.format(SUM, sum);
    }


    static String formDateString(Date date){
        DateFormat dateFormat = new SimpleDateFormat(DATE);
        return date == null ? "": dateFormat.format(date);

    }

    static String getLanguage(PageContext pageContext){
        HttpServletRequest request = ((HttpServletRequest) pageContext.getRequest());
        return (String) request.getSession().getAttribute(SessionAttribute.LOCALE.name().toLowerCase());
    }
}
