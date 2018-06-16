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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        if (Validator.validateId(cardId)){
            throw new LogicException("Trying to bind user to invalid card id.");
        }
        try {
            creditCardRepository.update(new UpdateCardUser(login, Integer.parseInt(cardId)));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public BigDecimal findMoneyAmount(String login) throws LogicException {
        if (Validator.validateLogin(login)){
            throw new LogicException("Incorrect login while trying to find money amount.");
        }
        try {
            String money = creditCardRepository.find(new FindCreditCardMoneyAmount(login));
            return new BigDecimal(money);
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public Set<TopUpTheBalance.Parameter> topUpTheBalance(String login, String cardNumber, String cardCode, String moneyAmount) throws LogicException {
        Set<TopUpTheBalance.Parameter> incorrect = new HashSet<>();
        if (Validator.validateLogin(login)){
            throw new LogicException("Incorrect login while checking card parameters.");
        }
       if (!Validator.validateMoneyAmount(moneyAmount)){
            incorrect.add(TopUpTheBalance.Parameter.WRONG_SUM);
        }else{
            try {
                if (checkCardParameters(login, cardCode, cardNumber)){
                    incorrect.add(TopUpTheBalance.Parameter.WRONG_CARD_INFORMATION);
                }
                    BigDecimal sum = new BigDecimal(moneyAmount);
                    BigDecimal money = findMoneyAmount(login);
                    BigDecimal totalSum = sum.add(money);
                    if (incorrect.isEmpty() && Validator.validateMoneyAmount(totalSum.toString())) {
                        creditCardRepository.update(new UpdateMoneyAmount(totalSum, login));
                    } else {
                        incorrect.add(TopUpTheBalance.Parameter.WRONG_SUM);
                    }
            } catch (RepositoryException e) {
                throw new LogicException(e);
            }
        }
        return incorrect;
    }

    private CreditCard findCreditCard(String login) throws LogicException {
        if (Validator.validateLogin(login)){
            throw new LogicException("Incorrect login while finding credit card.");
        }
        List<CreditCard> creditCards;
        try {
            creditCards = creditCardRepository.find(new FindCardByLogin(login));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
        if (creditCards.isEmpty()){
            throw new LogicException("Credit card hasn't been found");
        }else{
            return creditCards.get(0);
        }
    }

    public boolean checkCardParameters(String login, String cardCode, String cardNumber) throws LogicException {
        if (!Validator.validateCardNumber(cardNumber) || !Validator.validateCardCode(cardCode)){
            return true;
        }
            CreditCard creditCard = findCreditCard(login);
            String code = String.valueOf(creditCard.getCode());
            String number = creditCard.getNumber();
            return !cardCode.equals(code) || !number.equals(cardNumber);
    }

    public boolean checkSumParameters(String obligation, String moneyAmount, String login) throws LogicException {
        if (!Validator.validateMoneyAmount(obligation) || !Validator.validateMoneyAmount(moneyAmount)){
            return false;
        }
        BigDecimal payment = new BigDecimal(moneyAmount);
        BigDecimal creditSum = new BigDecimal(obligation);
        if (payment.compareTo(creditSum) > 0){
            return false;
        }
        BigDecimal cardSum = findMoneyAmount(login);
        return cardSum.compareTo(payment) >= 0;
    }
}
