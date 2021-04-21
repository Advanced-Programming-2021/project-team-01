package controller;

import java.util.HashMap;

public class ShopController {
    private static ShopController instance = null;

    public static ShopController getInstance() {
        if (instance == null) {
            instance = new ShopController();
        }
        return instance;
    }

    public void buyCard(String name) {

    }

    public void shopShowAll(String name) {

    }

    public HashMap<String, Integer> getCards() {
        return null;
    }
}
