package by.epam.safonenko.pharmacy.logic.impl;

import by.epam.safonenko.pharmacy.command.impl.TopUpTheBalance;
import by.epam.safonenko.pharmacy.entity.CreditCard;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.logic.Logic;
import by.epam.safonenko.pharmacy.repository.impl.CreditCardRepository;
import by.epam.safonenko.pharmacy.specification.impl.card.find.FindCardByLogin;
import by.epam.safonenko.pharmacy.specification.impl.card.find.FindCardIdWithoutUser;
import by.epam.safonenko.pharmacy.specification.impl.card.find.FindCreditCardMoneyAmount;
import by.epam.safonenko.pharmacy.specification.impl.card.update.UpdateMoneyAmount;
import by.epam.safonenko.pharmacy.specification.impl.user.update.UpdateCardUser;
import by.epam.safonenko.pharmacy.validator.Validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CreditCardLogic implements Logic {
    private CreditCardRepository creditCardRepository;

    public CreditCardLogic(){
        creditCardRepository = new CreditCardRepository();
    }

    public String findCardId() throws LogicException {
        try {
            return creditCardRepository.find(new FindCardIdWithoutUser());
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public void bindUserToCard(String login, String cardId) throws LogicException {
        if (!Validator.validateId(cardId)){
            throw new LogicException("Trying to bind user to invalid card id.");
        }
        try {
            creditCardRepository.update(new UpdateCardUser(login, Integer.parseInt(cardId)));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public int findMoneyAmount(String login) throws LogicException {
        if (!Validator.validateLogin(login)){
            throw new LogicException("Incorrect login while trying to find money amount.");
        }
        try {
            String money = creditCardRepository.find(new FindCreditCardMoneyAmount(login));
            return Integer.valueOf(money);
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public List<TopUpTheBalance.Parameter> topUpTheBalance(String login, String cardNumber, String cardCode, String moneyAmount) throws LogicException {
        List<TopUpTheBalance.Parameter> incorrect = new ArrayList();
        if (! Validator.validateLogin(login)){
            throw new LogicException("Incorrect login while checking card parameters.");
        }
        if (!Validator.validateCardNumber(cardNumber) || !Validator.validateCardCode(cardCode)){
            incorrect.add(TopUpTheBalance.Parameter.WRONG_CARD_INFORMATION);
        }else if (!Validator.validateMoneyAmount(moneyAmount)){
            incorrect.add(TopUpTheBalance.Parameter.WRONG_SUM);
        }else{
            try {
                CreditCard creditCard = creditCardRepository.find(new FindCardByLogin(login)).get(0);
                String code = String.valueOf(creditCard.getCode());
                if (! cardCode.equals(code) || ! cardNumber.equals(creditCard.getNumber())){
                    incorrect.add(TopUpTheBalance.Parameter.WRONG_CARD_INFORMATION);
                }
                BigDecimal sum = new BigDecimal(moneyAmount);
                BigDecimal totalSum = sum.add(creditCard.getMoneyAmount());
                if (incorrect.isEmpty() && Validator.validateMoneyAmount(totalSum.toString())){
                    creditCardRepository.update(new UpdateMoneyAmount(totalSum, login));
                }else{
                    incorrect.add(TopUpTheBalance.Parameter.WRONG_SUM);
                }
            } catch (RepositoryException e) {
                throw new LogicException(e);
            }
        }
        return incorrect;
    }
}
