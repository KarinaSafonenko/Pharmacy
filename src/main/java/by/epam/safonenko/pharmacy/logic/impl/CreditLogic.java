package by.epam.safonenko.pharmacy.logic.impl;

import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.entity.Credit;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.exception.TransactionException;
import by.epam.safonenko.pharmacy.logic.Logic;
import by.epam.safonenko.pharmacy.repository.impl.CreditRepository;
import by.epam.safonenko.pharmacy.specification.impl.credit.find.FindCreditsByLogin;
import by.epam.safonenko.pharmacy.specification.impl.credit.find.FindObligationByCreditId;
import by.epam.safonenko.pharmacy.specification.impl.credit.find.FindOpenCreditsByLogin;
import by.epam.safonenko.pharmacy.transaction.CloseCreditTransaction;
import by.epam.safonenko.pharmacy.transaction.DecreaseCreditObligation;
import by.epam.safonenko.pharmacy.transaction.OrderCreditTransaction;
import by.epam.safonenko.pharmacy.transaction.PayCreditTransaction;
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

    public BigDecimal findObligation(String creditId) throws LogicException {
        if(!Validator.validateId(creditId)){
            throw new LogicException("Incorrect credit id while finding credit obligation.");
        }
        try {
            String obligation = creditRepository.find(new FindObligationByCreditId(Integer.parseInt(creditId)));
            return new BigDecimal(obligation);
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public List<Credit> findUserCredits(String login) throws LogicException {
        if(!Validator.validateLogin(login)){
            throw new LogicException("Incorrect login while finding user credits.");
        }
        try {
            return creditRepository.find(new FindCreditsByLogin(login));
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
            try {
                new OrderCreditTransaction(login, basket, address, money, today).execute();
            } catch (TransactionException e) {
                throw new LogicException(e);
            }
            return true;
        }
    }

    public void payCredit(String moneyAmount, String obligation, String creditId, String login) throws LogicException {
        if (!Validator.validateMoneyAmount(moneyAmount) || !Validator.validateMoneyAmount(obligation)
                || !Validator.validateId(creditId) || !Validator.validateLogin(login)){
            throw new LogicException("Incorrect parameters while paying credit.");
        }
        BigDecimal sum = new BigDecimal(moneyAmount);
        BigDecimal creditSum = new BigDecimal(obligation);
        int credit = Integer.parseInt(creditId);
        Date today = getCurrentDate();
        PayCreditTransaction transaction;
        try {
        if (sum.compareTo(creditSum) == 0) {
            transaction = new CloseCreditTransaction(credit, login, today, sum);
        } else{
            transaction = new DecreaseCreditObligation(credit, login, today, sum);
        }
        transaction.execute();
        }catch (TransactionException e) {
            throw new LogicException(e);
        }
    }
}
