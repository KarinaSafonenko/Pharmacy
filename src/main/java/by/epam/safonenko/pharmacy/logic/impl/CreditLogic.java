package by.epam.safonenko.pharmacy.logic.impl;

import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.entity.Credit;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.exception.TransactionException;
import by.epam.safonenko.pharmacy.logic.Logic;
import by.epam.safonenko.pharmacy.repository.impl.CreditRepository;
import by.epam.safonenko.pharmacy.specification.impl.credit.find.FindOpenCreditsByLogin;
import by.epam.safonenko.pharmacy.transaction.OrderCreditTransaction;
import by.epam.safonenko.pharmacy.validator.Validator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CreditLogic implements Logic {
    private CreditRepository creditRepository;
    private ClientBasketLogic clientBasketLogic;

    public CreditLogic(){
        creditRepository = new CreditRepository();
        clientBasketLogic = new ClientBasketLogic();
    }

    public boolean isAnyOpenCredit(String login) throws LogicException {
        if(!Validator.validateLogin(login)){
            throw new LogicException("Incorrect login while finding open user credits.");
        }
        try {
            List<Credit> openCredits = creditRepository.find(new FindOpenCreditsByLogin(login));
            return !openCredits.isEmpty();
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public boolean formCredit(String login, String street, String house, String flat, String sum) throws LogicException {
        if(!Validator.validateLogin(login) || !Validator.validateMoneyAmount(sum)){
            throw new LogicException("Incorrect login or sum while forming credit.");
        }
        if (!Validator.validateStreet(street) || !Validator.validateNumber(house) || !Validator.validateNumber(flat)){
            return false;
        }else{
            Basket basket = clientBasketLogic.findClientBasketContent(login);
            String address = formAddress(street, house, flat);
            BigDecimal money = new BigDecimal(sum);
            Date today = getCurrentDate();
            OrderCreditTransaction creditTransaction = new OrderCreditTransaction(login, basket, address, money, today);
            try {
                creditTransaction.execute();
            } catch (TransactionException e) {
                throw new LogicException(e);
            }
            return true;
        }
    }
}
