package by.epam.safonenko.pharmacy.logic.impl;

import by.epam.safonenko.pharmacy.command.impl.FormOrder;
import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.entity.Order;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.exception.TransactionException;
import by.epam.safonenko.pharmacy.logic.Logic;
import by.epam.safonenko.pharmacy.repository.impl.OrderRepository;
import by.epam.safonenko.pharmacy.specification.impl.order.find.FindOrdersByLogin;
import by.epam.safonenko.pharmacy.transaction.OrderCardTransaction;
import by.epam.safonenko.pharmacy.validator.Validator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrderLogic implements Logic {
    private CreditCardLogic creditCardLogic;
    private ClientBasketLogic clientBasketLogic;
    private OrderRepository orderRepository;

    public OrderLogic(){
        creditCardLogic = new CreditCardLogic();
        clientBasketLogic = new ClientBasketLogic();
        orderRepository = new OrderRepository();
    }

    public List<Order> findOrders(String login) throws LogicException {
        if(!Validator.validateLogin(login)){
            throw new LogicException("Incorrect login while finding client orders.");
        }
        try {
            return orderRepository.find(new FindOrdersByLogin(login));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public Set<FormOrder.Parameter> formOrder(String login, String street, String house, String flat, String sum, String cardCode, String cardNumber) throws LogicException {
        if(!Validator.validateLogin(login) || !Validator.validateMoneyAmount(sum)){
            throw new LogicException("Incorrect login or sum while forming credit.");
        }
        Set<FormOrder.Parameter> result = new HashSet<>();
        if (!Validator.validateStreet(street) || !Validator.validateNumber(house) || !Validator.validateNumber(flat)){
            result.add(FormOrder.Parameter.INCORRECT_ADDRESS);
        }
        if (creditCardLogic.checkCardParameters(login, cardCode, cardNumber)){
            result.add(FormOrder.Parameter.WRONG_CARD_INFORMATION);
        }
        BigDecimal money = creditCardLogic.findMoneyAmount(login);
        BigDecimal neededSum = new BigDecimal(sum);
        if (neededSum.compareTo(money) > 0){
            result.add(FormOrder.Parameter.NOT_ENOUGH_MONEY);
        }
        if (result.isEmpty()) {
                Basket basket = clientBasketLogic.findClientBasketContent(login);
                String address = formAddress(street, house, flat);
                Date today = getCurrentDate();
                try {
                    new OrderCardTransaction(login, basket, address, neededSum, today).execute();
                } catch (TransactionException e) {
                    throw new LogicException(e);
                }
            }
        return result;
    }
}
