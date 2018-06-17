package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.CustomTagException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public abstract class AbstractShowProductTag extends TagSupport {
    protected List<Medicine> products;
    protected int number;

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
            JspWriter out = pageContext.getOut();
            for (int index = 0; index < productNumber; index++) {
                Medicine current = products.get(index);
                String imageWrapper = TagUtil.formImageWrapper(current.getImagePath(), current.getId());
                String caption = TagUtil.formCaption(current.getName());
                writeOut(out, imageWrapper, caption);
            }
        } catch (IOException e) {
            logException(e);
            throw new CustomTagException(e);
        }
        return SKIP_BODY;
    }
    public abstract void writeOut(JspWriter out, String imageWrapper, String caption) throws IOException;

    public abstract void logException(IOException e);

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
