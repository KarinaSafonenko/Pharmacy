package by.epam.safonenko.pharmacy.tag;

import by.epam.safonenko.pharmacy.entity.Credit;
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

public class ShowCreditTag extends TagSupport {
    private static Logger logger = LogManager.getLogger(ShowCreditTag.class);
    private static final String SHOW_HREF  = "<a href=\"/ControllerServlet?command=show_order_content&order_id=%d\" rel=\"tag\">%s</a>\n";
    private static final String PAY_HREF  = "<a href=\"/ControllerServlet?command=prepare_pay_credit&credit_id=%d\" rel=\"tag\">%s</a>\n";
    private static final String CREDIT = "<tr>\n" +
            "                            <td class=\"product-name\">\n" +
            "                           %s\n" +
            "                            </td>\n" +
            "                            %s\n" +
            "                            %s\n" +
            "                            %s\n" +
            "                            %s\n" +
            "                            </tr>";
    private List<Credit> credits;

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    private enum Parameter{
        SHOW_CONTENT, PAY, CLOSED
    }

    @Override
    public int doStartTag() {
        if (credits == null || credits.isEmpty()){
            return SKIP_BODY;
        }
        String language = TagUtil.getLanguage(pageContext);
        ResourceBundle resourceBundle = BundleUtil.getResourceBundle(language);
        String showContent = resourceBundle.getString(Parameter.SHOW_CONTENT.name().toLowerCase());
        String pay = resourceBundle.getString(Parameter.PAY.name().toLowerCase());
        String closed = resourceBundle.getString(Parameter.CLOSED.name().toLowerCase());
        try {
            JspWriter out = pageContext.getOut();
            for (Credit credit: credits) {
                int orderId = credit.getOrderId();
                int creditId = credit.getCreditId();
                String showOrder = String.format(SHOW_HREF, orderId, showContent);
                String payCredit = String.format(PAY_HREF, creditId, pay);
                showOrder = TagUtil.formHref(showOrder);
                Date startDate = credit.getStartDate();
                Date depositDate = credit.getLatestDeposit();
                BigDecimal obligation = credit.getObligation();
                String date = TagUtil.formCell(TagUtil.formDateString(startDate));
                String deposit = depositDate == null ? TagUtil.formCell("") : TagUtil.formCell(TagUtil.formDateString(depositDate));
                String money = TagUtil.formCell(TagUtil.formSum(obligation));
                Credit.CreditStatus creditStatus = credit.getCreditStatus();
                String status;
                if (creditStatus != null){
                    switch (creditStatus){
                        case OPEN:
                            status = payCredit;
                            break;
                        case CLOSED:
                            status = closed;
                            break;
                        default:
                             status = "";
                    }
                }else{
                    status = "";
                }
                status = TagUtil.formCell(TagUtil.formHref(status));
                out.write(String.format(CREDIT, showOrder, date, deposit, money, status));
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
