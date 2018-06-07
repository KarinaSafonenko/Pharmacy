package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.exception.CustomTagException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class ShowSubHeader extends TagSupport{
    private static Logger logger = LogManager.getLogger(ShowDialogTag.class);
    private String name;

    private static final String SUB_HEADER = "<section class=\"sub-header shop-layout-1\">\n" +
            "        <img class=\"rellax bg-overlay\" src=\"../images/sub-header/sky.jpg\" alt=\"\">\n" +
            "        <div class=\"overlay-call-to-action\"></div>\n" +
            "        <h3 class=\"heading-style-3\">%s</h3>\n" +
            "    </section>";

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public int doStartTag() {
        if (name.isEmpty()){
            return SKIP_BODY;
        }
        try {
            JspWriter out = pageContext.getOut();
                out.write(String.format(SUB_HEADER, name));
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
