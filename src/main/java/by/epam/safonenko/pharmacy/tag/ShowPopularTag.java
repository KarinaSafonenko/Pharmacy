package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.CustomTagException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class ShowPopularTag extends TagSupport implements ShowProduct{
    private static Logger logger = LogManager.getLogger(ShowPopularTag.class);
    private List<Medicine> products;
    private int number;

    private static final String POPULAR_PRODUCT = "<div class=\"col-md-4\">\n" +
            "                                      <figure>\n" +
            "                                              <div class=\"product product-style-5\">\n" +
            "                                               <div class=\"img-wrapper\">\n" +
            "                                                        <img class=\"img-responsive\" src=\"%s\" alt=\"product thumbnail\">\n" +
            "                                                      <div class=\"product-control-wrapper top-right\">\n" +
            "                                                        <div class=\"wrapper-control-item\">\n" +
            "                                                              <a class=\"js-quick-view\" type=\"button\" data-toggle=\"modal\" data-target=\"#%d\" >\n" +
            "                                                                    <span class=\"lnr lnr-eye\"></span>\n" +
            "                                                                  </a>\n" +
            "                                                            </div>\n" +
            "                                                      </div>\n" +
            "                                                </div>\n" +
            "                                        <figcaption class=\"desc\">\n" +
            "                                         <h3>\n" +
            "                                                <a class=\"product-name\">%s</a>\n" +
            "                                              </h3>\n" +
            "                                        </figcaption>\n" +
            "                                      </div>\n" +
            "                                    </figure>\n" +
            "                                </div>";

    public void setProducts(List<Medicine> products) {
        this.products = products;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int doStartTag() {
        if (products == null || products.isEmpty()){
            return SKIP_BODY;
        }
        int productNumber = products.size() < number ? products.size(): number;
        try {
            show(pageContext, POPULAR_PRODUCT, products, productNumber);
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
