package by.epam.safonenko.pharmacy.logic.impl;

import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.logic.Logic;
import by.epam.safonenko.pharmacy.repository.impl.ClientBasketRepository;
import by.epam.safonenko.pharmacy.specification.impl.basket.find.FindBasketProductAmount;
import by.epam.safonenko.pharmacy.specification.impl.basket.update.UpdateBasketProductAmount;
import by.epam.safonenko.pharmacy.validator.Validator;

public class ClientBasketLogic implements Logic {
    private ClientBasketRepository clientBasketRepository;

    public ClientBasketLogic(){
        clientBasketRepository = new ClientBasketRepository();
    }

    public boolean checkPackIdInClientBasket(String packId, String login) throws LogicException {
        if (!Validator.validateId(packId) || !Validator.validateLogin(login)){
            return false;
        }
        try {
            String result = clientBasketRepository.find(new FindBasketProductAmount(Integer.valueOf(packId), login));
            return result != null;
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public int findPackAmount(String packId, String login) throws LogicException {
        if (!Validator.validateId(packId) || !Validator.validateLogin(login)){
            throw new LogicException("Incorrect parameters while finding pack amount");
        }
        try {
            String result = clientBasketRepository.find(new FindBasketProductAmount(Integer.valueOf(packId), login));
            if (result == null || !Validator.validateAmount(result)){
                throw new LogicException("Something went wrong while finding pack amount.");
            }
            return Integer.parseInt(result);
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public void setPackAmount(String login, int amount, int packId) throws LogicException {
        if (!Validator.validateLogin(login)){
            throw new LogicException("Incorrect login while setting pack amount.");
        }
        try {
            clientBasketRepository.update(new UpdateBasketProductAmount(login, amount, packId));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }
}
