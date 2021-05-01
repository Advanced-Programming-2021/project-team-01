package controller;

import controller.exceptions.CardNameNotExists;
import controller.exceptions.NotEnoughMoney;
import model.card.Card;

import java.util.HashMap;


import static controller.RegisterController.onlineUser;

public class ShopController {
    private static ShopController instance = null;

    public static ShopController getInstance() {
        if (instance == null) {
            instance = new ShopController();
        }
        return instance;
    }

    public void buyCard(String name) throws Exception {
        Card card = Card.getCardByName(name);
        if (card == null)
            throw new CardNameNotExists(name);
        if (card.getPrice() > onlineUser.getMoney())
            throw new NotEnoughMoney();
        onlineUser.decreaseMoney(card.getPrice());
        onlineUser.addCardToPlayerCards(card.getName());
    }

}
