package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.opencsv.exceptions.CsvValidationException;
import controller.exceptions.CardNameNotExists;
import model.card.Card;
import model.card.MonsterCard;
import model.card.SpellCard;
import model.card.TrapCard;

import java.io.*;

public class ImportExportController {
    public static void importCard(String cardName) throws IOException {
        File file = new File("src" + File.separator + "resources" + File.separator + "export" + File.separator + cardName + ".json");
        FileReader fileReader = new FileReader(file);
        Gson gson = new Gson();
        String str = new String();
        int character;
        while ((character = fileReader.read()) != -1)
            str += (char) character;
        JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
        if (jsonObject.get("type").getAsString().equals("spell")) {
            SpellCard card = gson.fromJson(str, SpellCard.class);
            Card.addCardToDatabase(card);
        }
        else if (jsonObject.get("type").getAsString().equals("monster")) {
            MonsterCard card = gson.fromJson(str, MonsterCard.class);
            Card.addCardToDatabase(card);
        }
        else if (jsonObject.get("type").getAsString().equals("trap")) {
            TrapCard card = gson.fromJson(str, TrapCard.class);
            Card.addCardToDatabase(card);
        }
    }

    public static void exportCard(String cardName) throws CardNameNotExists, IOException {
        if (Card.getCardByName(cardName) == null)
            throw new CardNameNotExists(cardName);
        FileWriter fileWriter = new FileWriter("src" + File.separator +"resources" + File.separator + "export" + File.separator + cardName + ".json");
        Card card = Card.getCardByName(cardName);
        Gson gson = new Gson();
        gson.toJson(card, fileWriter);
        fileWriter.close();
    }

    public static void main(String[] args) throws IOException, CardNameNotExists, CsvValidationException {
        DatabaseController.loadGameCards();
        importCard("Twin Twisters");
    }
}
