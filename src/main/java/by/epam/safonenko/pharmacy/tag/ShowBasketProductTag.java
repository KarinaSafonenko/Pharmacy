package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Pack;
import by.epam.safonenko.pharmacy.exception.CustomTagException;
import by.epam.safonenko.pharmacy.util.BundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ShowBasketProductTag extends TagSupport {
    private static Logger logger = LogManager.getLogger(ShowBasketProductTag.class);
    private static final String RECIPE_BUTTON = "<a href=\"/ControllerServlet?command=request_recipe&pack_id=%d\" rel=\"tag\">%s</a>\n";
    private static final String ITEM_ROW = "  <tr>\n" +
            "                            <input type=\"hidden\" name=\"pack_id\" value=\"%d\">\n" +
            "                            <td class=\"product-thumbnail\">\n" +
            "                                <figure>\n" +
            "                                    <div class=\"product product-style-5\">\n" +
            "                                        %s" +
            "                                    </div>\n" +
            "                                </figure>\n" +
            "                            </td>\n" +
            "                            %s" +
            "                            %s\n" +
            "                            <td class=\"product-quantity\" data-title=\"Quantity\">\n" +
            "                                <input class=\"qty\" step=\"1\" min=\"1\" max=\"\" name=\"quantity\" value=\"%d\" title=\"Qty\" size=\"4\" pattern=\"[0-9]*\" inputmode=\"numeric\" type=\"number\">\n" +
            "                            </td>\n" +
            "                            <td class=\"product-price\" data-title=\"Price\">$%3.2f</td>\n" +
            "                            <td class=\"product-subtotal\" data-title=\"Total\">$%5.2f</td>\n" +
            "                            <td>%s</td>\n" +
            "                            <td class=\"product-remove\">\n" +
            "                                <a class=\"remove\" href=\"/ControllerServlet?command=remove_pack&pack_id=%d\" aria-label=\"Remove this item\">×</a>\n" +
            "                            </td>\n" +
            "                        </tr>";
    private Basket basket;
    private Map<Medicine, Boolean> recipeMap;

    private enum Parameter{
        RECIPE_MESSAGE, REQUEST_RECIPE, NOT_IN_STOCK
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    public void setRecipeMap(Map<Medicine, Boolean> recipeMap) {
        this.recipeMap = recipeMap;
    }

    @Override
    public int doStartTag() {
        if (basket == null || recipeMap.isEmpty() || basket.getContent() == null ||
                basket.getContent().isEmpty()
                || basket.getContent().size() != recipeMap.size()) {
            return SKIP_BODY;
        }
        String language = TagUtil.getLanguage(pageContext);
        ResourceBundle resourceBundle = BundleUtil.getResourceBundle(language);
        String recipeMessage = resourceBundle.getString(Parameter.RECIPE_MESSAGE.name().toLowerCase());
        String requestRecipe = resourceBundle.getString(Parameter.REQUEST_RECIPE.name().toLowerCase());
        String notInStock = resourceBundle.getString(Parameter.NOT_IN_STOCK.name().toLowerCase());
        Map<Medicine, Integer> content = basket.getContent();
        try {
            JspWriter out = pageContext.getOut();
            for (Map.Entry<Medicine, Integer> entry : content.entrySet())
            {
                Medicine medicine = entry.getKey();
                int quantity = entry.getValue();
                BigDecimal number = BigDecimal.valueOf(quantity);
                List<Pack> packs = medicine.getMedicinePacks();
                if (!packs.isEmpty() && recipeMap.containsKey(medicine)){
                    Pack pack = packs.get(0);
                    int packId = pack.getPackId();
                    int medicineId = medicine.getId();
                    String image = medicine.getImagePath();
                    String name = medicine.getName();
                    StringBuilder builder = new StringBuilder();
                    String recipe = TagUtil.formHref(recipeMessage);
                    if (!recipeMap.get(medicine)){
                        builder.append(String.format(RECIPE_BUTTON, packId, requestRecipe));
                        recipe = TagUtil.formHref(builder.toString());
                    }
                    BigDecimal price = pack.getPrice();
                    BigDecimal total = price.multiply(number);
                    int amount = pack.getAmount();
                    String packAmount = amount == 0 ? notInStock: String.valueOf(amount);
                    String imageWrapper = TagUtil.formImageWrapper(image, medicineId);
                    String productName = TagUtil.formProductName(name);
                    String cell = TagUtil.formCell(recipe);
                    out.write(String.format(ITEM_ROW, packId, imageWrapper, productName, cell, quantity, price, total, packAmount, packId));
                }
            }
        } catch (IOException e) {
            logger.catching(e);
            throw new CustomTagException(e);
        }
        return SKIP_BODY;

    }

    @Override
    public int doEndTag () {
        return EVAL_PAGE;
    }
}
