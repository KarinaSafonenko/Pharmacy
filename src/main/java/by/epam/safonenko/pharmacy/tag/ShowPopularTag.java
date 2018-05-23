package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Pack;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class ShowPopularTag extends TagSupport {
    private List<Medicine> products;
    private static final int MAX_PRODUCT_NUMBER = 3;

    public void setProducts(List<Medicine> products) {
        this.products = products;
    }

    @Override
    public int doStartTag() throws JspException {
        if (products == null || products.isEmpty()){
            return SKIP_BODY;
        }
        try {
            JspWriter out = pageContext.getOut();
            for (Medicine current: products) {
                out.write("<br> Name:" + current.getName() + "<br/>");
                out.write("<br> Description: " + current.getDescription() + "<br/>");
                out.write("<br> Category: " + current.getCategory() + "<br/>");
                for (Pack pack: current.getMedicinePacks()){
                    out.write("<br> -------<br/>");
                    out.write("<br> ImagePath" + pack.getImagePath() + "<br/>");
                    out.write("<br> Price: " + pack.getPrice() + "<br/>");
                    out.write("<br> Amount: " + pack.getAmount() + "<br/>");
                    out.write("<br> Dosage: " + pack.getDosage() + "<br/>");
                    out.write("<br> Quantity: " + pack.getQuantity() + "<br/>");
                    out.write("<br> Producer: " + pack.getProducer().getName() + "<br/>");
                    out.write("<br> Country: " + pack.getProducer().getCountry() + "<br/>");
                }
            }
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
