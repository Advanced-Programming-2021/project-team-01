package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.opencsv.exceptions.CsvValidationException;
import controller.exceptions.CardNameNotExists;
import javafx.scene.control.ChoiceDialog;
import model.card.Card;
import model.card.MonsterCard;
import model.card.SpellCard;
import model.card.TrapCard;
import model.commands.*;
import view.CustomCard;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ImportExportController {
    public static void importCard(String cardName) throws IOException {
        File file = new File("src" + File.separator + "resources" + File.separator + "export" + File.separator + cardName + ".json");
        FileReader fileReader = new FileReader(file);
        Gson gson = new Gson();
        StringBuilder str = new StringBuilder();
        int character;
        while ((character = fileReader.read()) != -1)
            str.append((char) character);
        JsonObject jsonObject = new JsonParser().parse(str.toString()).getAsJsonObject();
        switch (jsonObject.get("type").getAsString()) {
            case "spell": {
                SpellCard card = gson.fromJson(str.toString(), SpellCard.class);
                Card.addCardToDatabase(card);
                break;
            }
            case "monster": {
                MonsterCard card = gson.fromJson(str.toString(), MonsterCard.class);
                Card.addCardToDatabase(card);
                break;
            }
            case "trap": {
                TrapCard card = gson.fromJson(str.toString(), TrapCard.class);
                Card.addCardToDatabase(card);
                break;
            }
        }
    }

    public static void exportCard(String cardName) throws CardNameNotExists, IOException {
        if (Card.getCardByName(cardName) == null)
            throw new CardNameNotExists(cardName);
        FileWriter fileWriter = new FileWriter("src" + File.separator + "resources" + File.separator + "export" + File.separator + cardName + ".json");
        Card card = Card.getCardByName(cardName);
        Gson gson = new Gson();
        gson.toJson(card, fileWriter);
        fileWriter.close();
    }

    public static void importCardCreators(String cardName) throws IOException {
        File file = new File("src" + File.separator + "resources" + File.separator + "Creator" + File.separator + cardName + ".json");
        FileReader fileReader = new FileReader(file);
        Gson gson = new Gson();
        CustomCard customCard = gson.fromJson(fileReader, CustomCard.class);
        List<Effect> effects = customCard.getEffects();
        List<MonsterCard> monsterCards = customCard.getMonsterCards();
        for (int i = 0; i < monsterCards.size(); i++) {
            switch (effects.get(i)) {
                case SUIJIN: {
                    monsterCards.get(i).addCommands(new Suijin(monsterCards.get(i)));
                    break;
                }
                case TERATIGER: {
                    monsterCards.get(i).addCommands(new TerraTiger(monsterCards.get(i)));
                    break;
                }
                case TRICKY: {
                    monsterCards.get(i).addCommands(new TheTricky(monsterCards.get(i)));
                    break;
                }
                case GATE_GUARDIAN: {
                    monsterCards.get(i).addCommands(new GateGuardian(monsterCards.get(i)));
                    break;
                }
            }
            monsterCards.get(i).addImage();
            Card.addCardToDatabase(monsterCards.get(i));
        }
    }
}
