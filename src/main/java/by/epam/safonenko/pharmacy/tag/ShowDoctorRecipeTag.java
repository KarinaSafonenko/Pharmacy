package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.entity.Recipe;
import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.util.BundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.util.ResourceBundle;

public class ShowDoctorRecipeTag extends AbstractShowRecipe {
    private static Logger logger = LogManager.getLogger(ShowDoctorRecipeTag.class);
    private static final String SHOW_HREF  = "<a href=\"/ControllerServlet?command=prepare_extend_recipe&recipe_id=%d\" rel=\"tag\">%s</a>\n";
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
            "                            %s\n" +
            "                            %s\n" +
            "                            </tr>";

    private enum Parameter{
        EDIT
    }

    @Override
    public void writeOut(JspWriter out, String imageWrapper, String productName, String packAmount, String doctorInitials, String startDate, String endDate, Recipe recipe) throws IOException {
        String language = TagUtil.getLanguage(pageContext);
        ResourceBundle resourceBundle = BundleUtil.getResourceBundle(language);
        String edit = resourceBundle.getString(Parameter.EDIT.name().toLowerCase());
        int recipeId = recipe.getRecipeId();
        edit = String.format(SHOW_HREF, recipeId, edit);
        edit = TagUtil.formHref(edit);
        edit = TagUtil.formCell(edit);
        User client = recipe.getClient();
        String clientInitials = client == null ? "" : client.getSurname() + " " + client.getName() + " " + client.getPatronymic();
        clientInitials = TagUtil.formCell(clientInitials);
        out.write(String.format(RECIPE, imageWrapper, productName, packAmount, doctorInitials, clientInitials, startDate, endDate, edit));
    }

    @Override
    public void logException(IOException e) {
        logger.catching(e);
    }
}
