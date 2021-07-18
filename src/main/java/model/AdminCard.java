package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.exceptions.CsvValidationException;
import controller.DatabaseController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AdminCard {
    int amount;
    boolean isAllowed;
    String cardName;
    public static ArrayList<AdminCard> adminCards = new ArrayList<>();

    public AdminCard(String cardName, boolean isAllowed, int amount) {
        this.cardName = cardName;
        this.isAllowed = isAllowed;
        this.amount = amount;
        adminCards.add(this);
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isAllowed() {
        return isAllowed;
    }

    public void setAllowed(boolean allowed) {
        isAllowed = allowed;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void loadAdminCards() {
        try {
            FileReader fileReader = new FileReader("src/resources/Creator/AdminDatabase.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
    }
}
