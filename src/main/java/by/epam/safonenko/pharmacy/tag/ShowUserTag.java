package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.exception.CustomTagException;
import by.epam.safonenko.pharmacy.util.BundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class ShowUserTag extends TagSupport {
    private static Logger logger = LogManager.getLogger(ShowUserTag.class);
    private static final String USER = "<tr>\n" +
            "                            %s\n" +
            "                            %s\n" +
            "                            %s\n" +
            "                            %s\n" +
            "                            %s\n" +
            "                            %s\n" +
            "                            </tr>";
    private List<User> users;

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public int doStartTag() {
        String language = TagUtil.getLanguage(pageContext);
        ResourceBundle resourceBundle = BundleUtil.getResourceBundle(language);
        if (users == null || users.isEmpty()){
            return SKIP_BODY;
        }
        try {
            JspWriter out = pageContext.getOut();
            for (User user: users){
                String login =user.getLogin();
                login = TagUtil.formCell(login);
                String surname = user.getSurname();
                String mail = user.getMail();
                mail = TagUtil.formCell(mail);
                String name = user.getName();
                String patronymic = user.getPatronymic();
                User.UserSex userSex = user.getSex();
                String sex = resourceBundle.getString(userSex.name().toLowerCase());
                sex = TagUtil.formCell(sex);
                User.UserRole userRole = user.getRole();
                String role = resourceBundle.getString(userRole.name().toLowerCase());
                role = TagUtil.formCell(role);
                String initials = TagUtil.formCell(surname + " " + name + " " + patronymic);
                String empty = TagUtil.formCell("");
                out.write(String.format(USER, login, mail, empty, initials, sex, role));
            }
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
