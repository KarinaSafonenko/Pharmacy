package by.epam.safonenko.pharmacy.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;

public class ShowProductTag extends AbstractShowProductTag {
    private static Logger logger = LogManager.getLogger(ShowProductTag.class);
    private static final String PRODUCT = "<figure class=\"item\">\n" +
            "                <div class=\"product product-style-1\">\n" +
            "                  %s\n" +
            "                  %s\n" +
            "                </div>\n" +
            "              </figure>";

    @Override
    public void writeOut(JspWriter out, String imageWrapper, String caption) throws IOException {
        out.write(String.format(PRODUCT, imageWrapper, caption));
    }

    @Override
    public void logException(IOException e) {
        logger.catching(e);
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
