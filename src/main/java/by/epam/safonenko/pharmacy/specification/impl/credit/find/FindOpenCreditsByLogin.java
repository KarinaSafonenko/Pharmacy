package by.epam.safonenko.pharmacy.specification.impl.credit.find;

public class FindOpenCreditsByLogin extends AbstractFindCredits{
    private static String REQUEST = "SELECT credit_id, order_id, start_date, latest_deposit, obligation, status FROM pharmacy.credit WHERE status = 'OPEN' AND client = ?";

    public FindOpenCreditsByLogin(String login){
        this.login = login;
    }

    @Override
    public String getRequest() {
        return REQUEST;
    }
}
