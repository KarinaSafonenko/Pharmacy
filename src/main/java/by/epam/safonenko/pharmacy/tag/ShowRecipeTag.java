package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Recipe;
import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.exception.CustomTagException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public class ShowRecipeTag extends TagSupport {
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
    private List<Recipe> recipes;
    private int number;

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int doStartTag() {
        if (recipes == null || recipes.isEmpty()){
            return SKIP_BODY;
        }
        int productNumber = recipes.size() < number ? recipes.size(): number;
        try {
            JspWriter out = pageContext.getOut();
            for (int index = 0; index < productNumber; index++) {
                Recipe recipe = recipes.get(index);
                Medicine medicine = recipe.getMedicine();
                User doctor = recipe.getDoctor();
                if (medicine != null && doctor != null){
                    String imageWrapper = TagUtil.formImageWrapper(medicine.getImagePath(), medicine.getId());
                    String productName = TagUtil.formProductName(medicine.getName());
                    int amount = recipe.getAmount();
                    String doctorInitials = doctor.getLogin() == null ? "": doctor.getSurname() + " " + doctor.getName() + " " + doctor.getPatronymic();
                    String packAmount = amount == 0 ? TagUtil.formCell("") : TagUtil.formCell(String.valueOf(amount));
                    doctorInitials = TagUtil.formProductName(doctorInitials);
                    String startDate = TagUtil.formDateString(recipe.getStartDate());
                    String endDate = TagUtil.formDateString(recipe.getEndDate());
                    startDate = TagUtil.formCell(startDate);
                    endDate = TagUtil.formCell(endDate);
                    out.write(String.format(RECIPE, imageWrapper, productName, packAmount, doctorInitials, startDate, endDate));
                }
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
