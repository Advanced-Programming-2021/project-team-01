package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public static void loadAdminCards() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("src/resources/Creator/AdminDatabase.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        adminCards = gsonBuilder.create().fromJson(fileReader, new TypeToken<List<AdminCard>>(){}.getType());
    }

    public static AdminCard getCardByName(String cardName) {
        for (AdminCard adminCard : adminCards) {
            if (adminCard.cardName.equals(cardName)) return adminCard;
        }
        return null;
    }

    public static void decreaseCardAmount(String cardName, int amount) {
        for (AdminCard adminCard : adminCards) {
            if (adminCard.cardName.equals(cardName)) {
                adminCard.amount -= amount;
                FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter("src/resources/Creator/AdminDatabase.json");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Gson gson = new Gson();
                gson.toJson(adminCards, fileWriter);
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
    }
}
