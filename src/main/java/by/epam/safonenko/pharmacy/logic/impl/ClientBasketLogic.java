package by.epam.safonenko.pharmacy.logic.impl;

import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.entity.Pack;
import by.epam.safonenko.pharmacy.entity.Recipe;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.exception.RepositoryException;
import by.epam.safonenko.pharmacy.logic.Logic;
import by.epam.safonenko.pharmacy.repository.impl.ClientBasketRepository;
import by.epam.safonenko.pharmacy.repository.impl.RecipeRepository;
import by.epam.safonenko.pharmacy.specification.impl.basket.find.FindBasketProductAmount;
import by.epam.safonenko.pharmacy.specification.impl.basket.find.FindClientBascketContent;
import by.epam.safonenko.pharmacy.specification.impl.basket.update.UpdateBasketProductAmount;
import by.epam.safonenko.pharmacy.specification.impl.recipe.find.FindRecipesByLogin;
import by.epam.safonenko.pharmacy.validator.Validator;

import java.math.BigDecimal;
import java.util.*;

public class ClientBasketLogic implements Logic {
    private ClientBasketRepository clientBasketRepository;
    private RecipeRepository recipeRepository;

    public ClientBasketLogic(){
        clientBasketRepository = new ClientBasketRepository();
        recipeRepository = new RecipeRepository();
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

    private int findPackAmount(String packId, String login) throws LogicException {
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

    public void updatePackAmount(String login, String amount, String packId) throws LogicException {
        if (!Validator.validateLogin(login) || !Validator.validateAmount(amount) || !Validator.validateId(packId)){
            throw new LogicException("Incorrect parameters while setting pack amount.");
        }
        try {
            clientBasketRepository.update(new UpdateBasketProductAmount(login, Integer.parseInt(amount), Integer.parseInt(packId)));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public Basket findClientBasketContent(String login) throws LogicException {
        if (!Validator.validateLogin(login)){
            throw new LogicException("Incorrect login while finding client basket content.");
        }
        List<Basket> baskets;
        try {
            baskets = clientBasketRepository.find(new FindClientBascketContent(login));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }

        return baskets.isEmpty()? new Basket(): baskets.get(0);
    }

    public Map<Medicine, Boolean> formRecipeMap(String login, Basket basket) throws LogicException {
        if (!Validator.validateLogin(login)){
            throw new LogicException("Incorrect login while forming recipe map.");
        }
        Map<Medicine, Boolean> result = new HashMap<>();
        List<Recipe> recipes;
        try {
            recipes = recipeRepository.find(new FindRecipesByLogin(login));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
        Map<Medicine, Integer> basketMap = basket.getContent();
        Set<Medicine> medicineSet = basketMap.keySet();
        for (Medicine medicine: medicineSet){
            if (medicine.getRecipeNeed()){
                List<Pack> packList = medicine.getMedicinePacks();
                if(!packList.isEmpty()){
                    Pack pack = packList.get(0);
                    result.put(medicine, findMedicineRecipe(recipes, pack.getPackId()));
                }else {
                    result.put(medicine, false);
                }
            }else{
                result.put(medicine, true);
            }
        }
        return result;
    }

    public void removeFromBasket(String login, String packId) throws LogicException {
        if (!Validator.validateLogin(login)|| ! Validator.validateId(packId)){
            throw new LogicException("Incorrect login or pack id while removing pack from client basket.");
        }
        try {
            clientBasketRepository.delete(login, Integer.parseInt(packId));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    private boolean findMedicineRecipe(List<Recipe> recipes, int packId){
        Date today = getCurrentDate();
        for (Recipe recipe: recipes){
            Date recipeDate = recipe.getEndDate();
            Medicine medicine = recipe.getMedicine();
            List<Pack> packs = medicine.getMedicinePacks();
            if (!packs.isEmpty()){
                Pack current = packs.get(0);
                int id = current.getPackId();
                if (id == packId){
                    return recipeDate == null || !recipeDate.before(today);
                }
            }
        }
        return false;
    }

    public int calculateProductAmount(String quantity, String packId, String login) throws LogicException {
        int amount = Integer.parseInt(quantity);
        int currentAmount = findPackAmount(packId, login);
        int resultAmount = amount + currentAmount;
        if (Validator.validateAmount(String.valueOf(resultAmount))){
            return resultAmount;
        }else{
            throw new LogicException("Incorrect product amount while adding to cart.");
        }
    }

    public void addToCart(String login, String packId, String amount) throws LogicException {
        if (!Validator.validateLogin(login) || !Validator.validateId(packId) || ! Validator.validateAmount(amount)){
            throw new LogicException("Incorrect parameters while adding to cart.");
        }
        try {
            clientBasketRepository.add(login, Integer.parseInt(packId), Integer.parseInt(amount));
        } catch (RepositoryException e) {
            throw new LogicException(e);
        }
    }

    public BigDecimal countBasketPrice(Basket basket){
        BigDecimal result = BigDecimal.ZERO;
        Map<Medicine, Integer>  content = basket.getContent();
        for (Map.Entry<Medicine, Integer> entry : content.entrySet()) {
            Medicine medicine = entry.getKey();
            int quantity = entry.getValue();
            BigDecimal number = BigDecimal.valueOf(quantity);
            List<Pack> packs = medicine.getMedicinePacks();
            if (!packs.isEmpty()) {
                Pack pack = packs.get(0);
                BigDecimal price = pack.getPrice();
                BigDecimal totalPackPrice = price.multiply(number);
                result = result.add(totalPackPrice);
            }
        }
        return result;
    }
}
