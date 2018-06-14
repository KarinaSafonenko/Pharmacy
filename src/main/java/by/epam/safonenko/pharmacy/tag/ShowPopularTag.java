package by.epam.safonenko.pharmacy.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;

public class ShowPopularTag extends AbstractShowProduct{
    private static Logger logger = LogManager.getLogger(ShowPopularTag.class);
    private static final String POPULAR_PRODUCT = "<div class=\"col-md-4\">\n" +
            "                                      <figure>\n" +
            "                                       <div class=\"product product-style-5\">\n" +
            "                                        %s\n" +
            "                                        %s\n" +
            "                                       </div>\n" +
            "                                      </figure>\n" +
            "                                      </div>";

    @Override
    public void writeOut(JspWriter out, String imageWrapper, String caption) throws IOException {
        out.write(String.format(POPULAR_PRODUCT, imageWrapper, caption));
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
