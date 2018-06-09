package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.CustomTagException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class ShowProductTag extends TagSupport implements ShowProduct {
    private static final int MAX_PRODUCT_NUMBER = 12;
    private static Logger logger = LogManager.getLogger(ShowProductTag.class);
    private List<Medicine> products;

    private static final String PRODUCT = "<figure class=\"item\">\n" +
            "                <div class=\"product product-style-1\">\n" +
            "                  <div class=\"img-wrapper\">\n" +
            "                    <a>\n" +
            "                      <img class=\"img-responsive\" src=\"%s\" alt=\"product thumbnail\" />\n" +
            "                    </a>\n" +
            "                    <div class=\"product-control-wrapper bottom-right\">\n" +
            "                      <div class=\"wrapper-control-item\">\n" +
            "                        <a class=\"js-quick-view\" type=\"button\" data-toggle=\"modal\" data-target=\"#%d\">\n" +
            "                          <span class=\"lnr lnr-eye\"></span>\n" +
            "                        </a>\n" +
            "                      </div>      \n" +
            "                    </div>\n" +
            "                  </div>\n" +
            "                  <figcaption class=\"desc text-center\">\n" +
            "                    <h3>\n" +
            "                      <a class=\"product-name\">%s</a>\n" +
            "                    </h3>\n" +
            "                  </figcaption>\n" +
            "                </div>\n" +
            "              </figure>";

    public void setProducts(List<Medicine> products) {
        this.products = products;
    }

    @Override
    public int doStartTag() {
        if (products == null || products.isEmpty()){
            return SKIP_BODY;
        }
        int productNumber = products.size() < MAX_PRODUCT_NUMBER ? products.size(): MAX_PRODUCT_NUMBER;
        try {
            show(pageContext, PRODUCT, products, productNumber);
        } catch (IOException e) {
            logger.catching(e);
            throw new CustomTagException(e);
        }
        return SKIP_BODY;
    }
    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
