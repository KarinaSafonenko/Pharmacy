package by.epam.safonenko.pharmacy.logic;

import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.repository.ClientBasketRepository;
import by.epam.safonenko.pharmacy.specification.impl.FindBasketProductAmount;
import by.epam.safonenko.pharmacy.specification.impl.UpdateBasketProductAmount;
import by.epam.safonenko.pharmacy.validator.Validator;
import sun.rmi.runtime.Log;

public class ClientBasketLogic {
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

    public void setPackAmount(String login, String amount) throws LogicException {
        if (!Validator.validateLogin(login) || ! Validator.validateAmount(amount)){
            throw new LogicException("Incorrect parameters while setting pack amount.");
        }
        try {
            clientBasketRepository.update(new UpdateBasketProductAmount(login, Integer.parseInt(amount)));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }
}
