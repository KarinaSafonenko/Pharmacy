package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.entity.Recipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

public class ShowRecipeTag extends TagSupport {
    private static Logger logger = LogManager.getLogger(ShowRecipeTag.class);
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
//        try {
//            //show(pageContext, PRODUCT, products, productNumber);
//        } catch (IOException e) {
//            logger.catching(e);
//            throw new CustomTagException(e);
//        }
        return SKIP_BODY;
    }
    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}
