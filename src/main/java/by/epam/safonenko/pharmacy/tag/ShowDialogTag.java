package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Pack;
import by.epam.safonenko.pharmacy.exception.CustomTagException;
import by.epam.safonenko.pharmacy.specification.impl.medicine.ProducerParameter;
import by.epam.safonenko.pharmacy.util.BundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

public class ShowDialogTag extends TagSupport {
    private static Logger logger = LogManager.getLogger(ShowDialogTag.class);
    private static final String RADIO_BUTTON = "<input type='radio' name='pack_id' value='%d' checked>%d %s (%d %s) %s: %s  %s: %s <br/> $ %3.2f </input><br/>\n";
    private static final String DIALOG = " <div class=\"modal fade\" id=\"%d\" tabindex=\"-1\" role=\"dialog\">\n" +
            "        <div class=\"modal-dialog modal-lg modal-quickview woocommerce\" role=\"document\">\n" +
            "            <div class=\"modal-content\">\n" +
            "                <div class=\"modal-header\">\n" +
            "                    <button class=\"close\" type=\"button\" data-dismiss=\"modal\" aria-label=\"Close\">\n" +
            "                        <span aria-hidden=\"true\">×</span>\n" +
            "                    </button>\n" +
            "                </div>\n" +
            "                <div class=\"modal-body\">\n" +
            "                    <div class=\"row\">\n" +
            "                        <div class=\"col-md-6\">\n" +
            "                            <div class=\"woocommerce-product-gallery\">\n" +
            "                                <div class=\"main-carousel-product-quick-view\">\n" +
            "                                    <div class=\"item\">\n" +
            "                                        <img class=\"img-responsive\" src=\"%s\" alt=\"product thumbnail\" />\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                        <div class=\"col-md-6\">\n" +
            "                            <div class=\"summary\">\n" +
            "                                <div class=\"desc\">\n" +
            "                                    <div class=\"header-desc\">\n" +
            "                                        <h2 class=\"product-title\">%s</h2>\n" +
            "                                    </div>\n" +
            "                                    <div class=\"body-desc\">\n" +
            "                                        <div class=\"woocommerce-product-details-short-description\">\n" +
            "                                            <p>%s</p>\n" +
            "                                        </div>\n" +
            "                                    </div>\n" +
            "                                    <div class=\"footer-desc\">\n" +
            "                                        <form class=\"cart\" method=\"post\" action=\"/ControllerServlet\">\n" +
            "                                            <input type=\"hidden\" name=\"command\" value=\"add_to_cart\" />\n" +
            "                                            <div class=\"product-meta\">\n" +
            "                                                %s" +
            "                                                </p>\n" +
            "                                                <p class=\"posted-in\">%s:\n" +
            "                                                    %s\n" +
            "                                                </p>\n" +
            "                                                <p class=\"posted-in\">%s:\n" +
            "                                                    <a href=\"/ControllerServlet?command=show_category_products&category=%s\" rel=\"tag\">%s</a>\n" +
            "                                                </p>\n" +
            "                                            </div>\n" +
            "                                            %s" +
            "                                        </form>\n" +
            "                                    </div>\n" +
            "                                </div>\n" +
            "                            </div>\n" +
            "                        </div>\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    </div>";
    private static final String BUTTONS = "<div class=\"quantity buttons-added\">\n" +
            "                                  <input class=\"input-text qty text\" step=\"1\" min=\"1\" name=\"quantity\" value=\"1\" size=\"4\" pattern=\"[0-9]*\" inputmode=\"numeric\" type=\"number\"/>\n" +
            "                              </div>\n" +
            "                              <div class=\"group-btn-control-wrapper\">\n" +
            "                                   <button class=\"btn btn-brand no-radius\">%s</button>\n" +
            "                              </div>\n";

    private enum TagParameter{
        YES, NO, PRODUCER, ADD_TO_CART, RECIPE_NEED, CATEGORY, MG, PIECE
    }

    private List<Medicine> products;
    private boolean buttons;
    private int number;

    public void setProducts(List<Medicine> products) {
        this.products = products;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setButtons(boolean buttons) {
        this.buttons = buttons;
    }

    @Override
    public int doStartTag() {
        if (products == null || products.isEmpty()) {
            return SKIP_BODY;
        }
        String language = TagUtil.getLanguage(pageContext);
        ResourceBundle resourceBundle = BundleUtil.getResourceBundle(language);
        String producer = resourceBundle.getString(TagParameter.PRODUCER.name().toLowerCase());
        String piece = resourceBundle.getString(TagParameter.PIECE.name().toLowerCase());
        String mg = resourceBundle.getString(TagParameter.MG.name().toLowerCase());
        String category = resourceBundle.getString(TagParameter.CATEGORY.name().toLowerCase());
        String recipe = resourceBundle.getString(TagParameter.RECIPE_NEED.name().toLowerCase());
        String add = resourceBundle.getString(TagParameter.ADD_TO_CART.name().toLowerCase());
        String country = resourceBundle.getString(ProducerParameter.COUNTRY.name().toLowerCase());
        int productNumber = products.size() < number ? products.size() : number;
        try {
            JspWriter out = pageContext.getOut();
            for (int index = 0; index < productNumber; index++) {
                Medicine current = products.get(index);
                StringBuilder builder = new StringBuilder();
                for (Pack pack : current.getMedicinePacks()) {
                    builder.append(String.format(RADIO_BUTTON, pack.getPackId(), pack.getQuantity(),piece, pack.getDosage(), mg, producer, pack.getProducer().getName(), country, pack.getProducer().getCountry(), pack.getPrice()));
                }
                String recipeNeed = current.getRecipeNeed()? resourceBundle.getString(TagParameter.YES.name().toLowerCase()): resourceBundle.getString(TagParameter.NO.name().toLowerCase());
                String productCategory = resourceBundle.getString(current.getCategory().name().toLowerCase());
                String button = buttons? String.format(BUTTONS, add): "";
                out.write(String.format(DIALOG, current.getId(), current.getImagePath(), current.getName(), current.getDescription(), builder.toString(),recipe, recipeNeed, category, current.getCategory().name().toLowerCase(), productCategory, button));
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
