package by.epam.safonenko.pharmacy.logic;

import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.repository.CreditCardRepository;
import by.epam.safonenko.pharmacy.specification.impl.FindCardIdWithoutUser;
import by.epam.safonenko.pharmacy.specification.impl.UpdateCardUser;
import by.epam.safonenko.pharmacy.validator.Validator;

public class CreditCardLogic {
    private CreditCardRepository creditCardRepository;

    public CreditCardLogic(){
        creditCardRepository = new CreditCardRepository();
    }

    public String findCardId() throws LogicException {
        try {
            String result = creditCardRepository.find(new FindCardIdWithoutUser());
            return result;
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
}
