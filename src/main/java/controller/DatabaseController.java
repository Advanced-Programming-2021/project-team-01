package controller;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import model.Deck;
import model.Player;
import model.card.*;


import java.io.*;


public class DatabaseController {

    public static void loadGameCards() throws IOException, CsvValidationException {
        File file = new File(System.getProperty("user.dir") + "/src/resources/Monster.csv");
        FileReader fileReader = new FileReader(file);
        CSVReader reader = new CSVReader(fileReader);

        String[] monsterArray = reader.readNext();
        while ((monsterArray = reader.readNext()) != null) {
            Card.addCardToDatabase(new MonsterCard(monsterArray[0], monsterArray[7], Integer.parseInt(monsterArray[8]),
                    Integer.parseInt(monsterArray[5]), Integer.parseInt(monsterArray[6]), CardType.getCardType(monsterArray[4]),
                    MonsterType.getMonsterTypesArray(monsterArray[3]), Attribute.getAttribute(monsterArray[2]), Integer.parseInt(monsterArray[1])));
        }

        file = new File(System.getProperty("user.dir") + "/src/resources/SpellTrap.csv");
        fileReader = new FileReader(file);
        reader = new CSVReader(fileReader);

        String[] spellTrapArray = reader.readNext();
        while ((spellTrapArray = reader.readNext()) != null) {
            if (spellTrapArray[1].equals("Trap")) {
                Card.addCardToDatabase(new TrapCard(spellTrapArray[0], spellTrapArray[3], Integer.parseInt(spellTrapArray[5]),
                        Property.getProperty(spellTrapArray[2])));
            } else {
                Card.addCardToDatabase(new SpellCard(spellTrapArray[0], spellTrapArray[3], Integer.parseInt(spellTrapArray[5]),
                        Property.getProperty(spellTrapArray[2])));
            }
        }
    }

    private static void write(String data, String directory) {
        try {
            FileWriter fileWriter = new FileWriter(directory);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(data);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUserDirectory(String username) {
        return "src" + File.separator + "resources" + File.separator + "users" + File.separator + username + ".json";
    }

    public static String getDeckDirectory(String deck) {
        return "src" + File.separator +"resources" + File.separator + "deck" + File.separator + deck + ".json";
    }

    public static String read(String directory) {
        try {
            FileReader fileReader = new FileReader(directory);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String data = bufferedReader.readLine();
            bufferedReader.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Player getUserFromDirectory(String username) {
        String directory = getUserDirectory(username);
        try {
            FileReader fileReader = new FileReader(directory);
            Gson gson = new Gson();
            return gson.fromJson(fileReader,Player.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Deck getDeckByName(String deck) {
        String directory = getDeckDirectory(deck);
        try {
            FileReader fileReader = new FileReader(directory);
            Gson gson = new Gson();
            return gson.fromJson(fileReader,Deck.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void updateDeck(Deck deck){
        String deckName = deck.getDeckName();
        try {
            FileWriter fileWriter = new FileWriter(getUserDirectory(deckName));
            Gson gson = new Gson();
            gson.toJson(deck, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void updatePlayer(Player player){
        String username = player.getUsername();
        try {
            FileWriter fileWriter = new FileWriter(getUserDirectory(username));
            Gson gson = new Gson();
            gson.toJson(player, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Player player = new Player("ali","123","hoy");
        updatePlayer(player);
    }
}
