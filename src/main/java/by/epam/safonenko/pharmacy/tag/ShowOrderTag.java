package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.entity.Order;
import by.epam.safonenko.pharmacy.exception.CustomTagException;
import by.epam.safonenko.pharmacy.util.BundleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ShowOrderTag extends TagSupport {
    private static Logger logger = LogManager.getLogger(ShowOrderTag.class);
    private static String SHOW_BUTTON = "<a href=\"/ControllerServlet?command=show_order_content&order_id=%d\" rel=\"tag\">%s</a>";
    private static String ORDER = "<tr>\n" +
            "                            <td class=\"product-name\">\n" +
            "                            %s\n" +
            "                            </td>\n" +
            "                            %s\n" +
            "                            %s\n" +
            "                            %s\n" +
            "                            %s\n" +
            "                            </tr>";
    private List<Order> orders;

    private enum Parameter{
        CARD, CREDIT, SHOW_CONTENT
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public int doStartTag() {
        if (orders == null || orders.isEmpty()){
            return SKIP_BODY;
        }
        String language = TagUtil.getLanguage(pageContext);
        ResourceBundle resourceBundle = BundleUtil.getResourceBundle(language);
        String byCard = resourceBundle.getString(Parameter.CARD.name().toLowerCase());
        String credit = resourceBundle.getString(Parameter.CREDIT.name().toLowerCase());
        String showContent = resourceBundle.getString(Parameter.SHOW_CONTENT.name().toLowerCase());
        try {
            JspWriter out = pageContext.getOut();
            for (Order order: orders) {
                int orderId = order.getId();
                String showButton = String.format(SHOW_BUTTON, orderId, showContent);
                String address = order.getAddress();
                Order.PaymentType paymentType = order.getPaymentType();
                String payment;
                if (paymentType != null){
                    switch (paymentType){
                        case CREDIT:
                            payment = credit;
                            break;
                        case CARD:
                            payment = byCard;
                            break;
                         default:
                             payment = "";
                             break;
                    }
                }else{
                    payment = "";
                }
                Date date = order.getStartDate();
                BigDecimal sum = order.getSum();
                showButton = TagUtil.formHref(showButton);
                address = TagUtil.formCell(address);
                payment = TagUtil.formCell(payment);
                String startDate = TagUtil.formDateString(date);
                startDate = TagUtil.formCell(startDate);
                String money = sum == null ? TagUtil.formCell(""): TagUtil.formCell(TagUtil.formSum(sum));
                out.write(String.format(ORDER, showButton, address, payment, startDate, money));
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
