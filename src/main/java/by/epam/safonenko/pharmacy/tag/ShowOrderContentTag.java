package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.CustomTagException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Map;

public class ShowOrderContentTag extends TagSupport {
    private static Logger logger = LogManager.getLogger(ShowOrderContentTag.class);
    private static String ORDER_CONTENT = "<tr>\n" +
            "                            <td class=\"product-thumbnail\">\n" +
            "                                <figure>\n" +
            "                                    <div class=\"product product-style-5\">\n" +
            "                                        %s\n" +
            "                                    </div>\n" +
            "                                </figure>\n" +
            "                            </td>\n" +
            "                           %s\n" +
            "                            %s\n" +
            "                            <td></td>\n" +
            "                            <td></td>\n" +
            "                            <td></td>\n" +
            "                        </tr>";

    private Basket basket;

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    @Override
    public int doStartTag() {
        if (basket == null || basket.getContent() == null || basket.getContent().isEmpty()) {
            return SKIP_BODY;
        }
        Map<Medicine, Integer> content = basket.getContent();
        try {
            JspWriter out = pageContext.getOut();
            for (Map.Entry<Medicine, Integer> entry : content.entrySet())
            {
                Medicine medicine = entry.getKey();
                int medicineId = medicine.getId();
                int quantity = entry.getValue();
                String image = medicine.getImagePath();
                String name = medicine.getName();
                name = TagUtil.formProductName(name);
                String imageWrapper = TagUtil.formImageWrapper(image, medicineId);
                String number = TagUtil.formCell(String.valueOf(quantity));
                out.write(String.format(ORDER_CONTENT, imageWrapper, name, number));
            }
        } catch (IOException e) {
            logger.catching(e);
            throw new CustomTagException(e);
        }
        return SKIP_BODY;

    }

    @Override
    public int doEndTag () {
        return EVAL_PAGE;
    }
}
