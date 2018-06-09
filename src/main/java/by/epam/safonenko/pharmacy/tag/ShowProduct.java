package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.entity.Medicine;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.List;

public interface ShowProduct{
    default void show(PageContext pageContext, String productTemplate, List<Medicine> products, int productNumber) throws IOException {
        JspWriter out = pageContext.getOut();
        for (int index = 0; index < productNumber; index++) {
            Medicine current = products.get(index);
            out.write(String.format(productTemplate, current.getImagePath(), current.getId(), current.getName()));
        }
    }
}
