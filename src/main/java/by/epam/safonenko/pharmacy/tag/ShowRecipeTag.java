package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.entity.Recipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;

public class ShowRecipeTag extends AbstractShowRecipeTag {
    private static Logger logger = LogManager.getLogger(ShowRecipeTag.class);
    private static final String RECIPE = "<tr>\n" +
            "                            <td class=\"product-thumbnail\">\n" +
            "                                <figure>\n" +
            "                                    <div class=\"product product-style-5\">\n" +
            "                                        %s\n" +
            "                                    </div>\n" +
            "                                </figure>\n" +
            "                            </td>\n" +
            "                            %s\n" +
            "                            %s\n" +
            "                            %s\n" +
            "                            %s\n" +
            "                            %s\n" +
            "                            </tr>";

    @Override
    public void writeOut(JspWriter out, String imageWrapper, String productName, String packAmount, String doctorInitials, String startDate, String endDate, Recipe recipe) throws IOException {
        out.write(String.format(RECIPE, imageWrapper, productName, packAmount, doctorInitials, startDate, endDate));
    }

    @Override
    public void logException(IOException e) {
        logger.catching(e);
    }
}
