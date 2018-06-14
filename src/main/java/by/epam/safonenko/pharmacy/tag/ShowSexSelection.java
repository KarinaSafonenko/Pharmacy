package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.exception.CustomTagException;
import by.epam.safonenko.pharmacy.specification.impl.user.UserParameter;
import by.epam.safonenko.pharmacy.util.BundleUtil;
import by.epam.safonenko.pharmacy.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ShowSexSelection extends TagSupport {
    private static Logger logger = LogManager.getLogger(ShowSexSelection.class);
    private static final String SELECTED = "selected";
    private static final String SELECT = "<div class=\"form-group organic-form-2\">\n" +
            "                            <label>%s:</label>\n" +
            "                        <div class=\"widget widget-control-header\">\n" +
            "                            <div class=\"select-custom-wrapper\">\n" +
            "                                <select class=\"no-border\" name=\"sex\">\n" +
            "                                    <option value=\"MALE\" %s>%s</option>\n" +
            "                                    <option value=\"FEMALE\" %s>%s</option>\n" +
            "                                </select>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        </div>";
    private String sex;

    public void setSex(String sex) {
        this.sex = sex;
    }


    @Override
    public int doStartTag() {
        if (sex == null){
            return SKIP_BODY;
        }
        String language = TagUtil.getLanguage(pageContext);
        ResourceBundle resourceBundle = BundleUtil.getResourceBundle(language);
        String male = resourceBundle.getString(User.UserSex.MALE.name().toLowerCase());
        String female = resourceBundle.getString(User.UserSex.FEMALE.name().toLowerCase());
        String sexMessage = resourceBundle.getString(UserParameter.SEX.name().toLowerCase());
        User.UserSex userSex = User.UserSex.valueOf(sex);
        String maleSelected = (userSex == User.UserSex.MALE)? SELECTED : "";
        String femaleSelected = (userSex == User.UserSex.FEMALE)? SELECTED : "";
        try {
            JspWriter out = pageContext.getOut();
            out.write(String.format(SELECT, sexMessage, maleSelected, male, femaleSelected, female));
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
