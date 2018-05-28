package by.epam.safonenko.pharmacy.specification.impl.card.find;

import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.entity.CreditCard;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.specification.FindSpecification;
import by.epam.safonenko.pharmacy.specification.impl.card.CardParameter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FindCardByLogin implements FindSpecification<CreditCard> {

    private static String REQUEST = "SELECT card_id, card_number, card_code, money_amount FROM pharmacy.credit_card WHERE login = ?";
    private String login;

    public FindCardByLogin(String login) {
        this.login = login;
    }

    @Override
    public List<CreditCard> execute(Statement statement) throws RepositoryException {
        List<CreditCard> result = new ArrayList<>();
        try(PreparedStatement current = (PreparedStatement) statement) {
            current.setString(1, login);
            ResultSet resultSet = current.executeQuery();
            if (resultSet.next()){
                CreditCard creditCard = new CreditCard();
                creditCard.setId(resultSet.getInt(CardParameter.CARD_ID.name().toLowerCase()));
                creditCard.setNumber(resultSet.getString(CardParameter.CARD_NUMBER.name().toLowerCase()));
                creditCard.setCode(resultSet.getInt(CardParameter.CARD_CODE.name().toLowerCase()));
                creditCard.setMoneyAmount(resultSet.getBigDecimal(CardParameter.MONEY_AMOUNT.name().toLowerCase()));
                result.add(creditCard);
            }
        }catch (SQLException e){
            throw new RepositoryException(e);
        }
        return result;
    }

    @Override
    public String getRequest() {
        return REQUEST;
    }
}
