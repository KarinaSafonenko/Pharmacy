package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Recipe;
import by.epam.safonenko.pharmacy.entity.User;
import by.epam.safonenko.pharmacy.exception.CustomTagException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

public abstract class AbstractShowRecipe extends TagSupport {
    protected List<Recipe> recipes;
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public int doStartTag() {
        if (recipes == null || recipes.isEmpty()){
            return SKIP_BODY;
        }
        try {
            JspWriter out = pageContext.getOut();
            for (Recipe recipe: recipes) {
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
                    writeOut(out, imageWrapper, productName, packAmount, doctorInitials, startDate, endDate, recipe);
                }
            }
        } catch (IOException e) {
            logException(e);
            throw new CustomTagException(e);
        }
        return SKIP_BODY;
    }
    public abstract void writeOut(JspWriter out, String imageWrapper, String productName, String packAmount, String doctorInitials, String startDate, String endDate, Recipe recipe) throws IOException;

    protected abstract void logException(IOException e);

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
