package by.epam.safonenko.pharmacy.command.util;

import by.epam.safonenko.pharmacy.command.impl.ShowBasketProducts;
import by.epam.safonenko.pharmacy.entity.Basket;
import by.epam.safonenko.pharmacy.entity.Medicine;
import by.epam.safonenko.pharmacy.exception.LogicException;
import by.epam.safonenko.pharmacy.logic.impl.ClientBasketLogic;
import by.epam.safonenko.pharmacy.util.RequestContent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ShowBasket {
    default void addBasketParameters(RequestContent requestContent, String login, Basket basket) throws LogicException {
        ClientBasketLogic clientBasketLogic = new ClientBasketLogic();
        Map<Medicine, Boolean> recipeList = clientBasketLogic.formRecipeMap(login, basket);
        if (basket.getContent() != null) {
            Set<Medicine> medicineSet = basket.getContent().keySet();
            List<Medicine> medicineList = new ArrayList<>(medicineSet);
            requestContent.addRequestAttribute(ShowBasketProducts.Parameter.PRODUCTS.name().toLowerCase(), medicineList);
        }
        BigDecimal totalPrice = clientBasketLogic.countBasketPrice(basket);
        requestContent.addRequestAttribute(ShowBasketProducts.Parameter.CLIENT_BASKET.name().toLowerCase(), basket);
        requestContent.addRequestAttribute(ShowBasketProducts.Parameter.RECIPE_MAP.name().toLowerCase(), recipeList);
        requestContent.addRequestAttribute(ShowBasketProducts.Parameter.CART_SUM.name().toLowerCase(), totalPrice);
    }
}
