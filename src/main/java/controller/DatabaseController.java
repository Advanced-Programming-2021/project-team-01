package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import model.Deck;
import model.Player;
import model.card.*;

import java.io.*;
import java.util.ArrayList;


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
                        Property.getProperty(spellTrapArray[2]), spellTrapArray[4]));
            } else {
                Card.addCardToDatabase(new SpellCard(spellTrapArray[0], spellTrapArray[3], Integer.parseInt(spellTrapArray[5]),
                        Property.getProperty(spellTrapArray[2]), spellTrapArray[4]));
            }
        }
        fileReader.close();
        reader.close();
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

    private static String getUserDirectory(String username) {
        return "src" + File.separator + "resources" + File.separator + "users" + File.separator + username + ".json";
    }

    private static String getDeckDirectory(String deck) {
        return "src" + File.separator + "resources" + File.separator + "deck" + File.separator + deck + ".json";
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

    public static void deleteDeckFile(String name) {
        File file = new File(getDeckDirectory(name));
        file.delete();
    }

    public static Player getUserByName(String username) {
        String directory = getUserDirectory(username);
        try {
            FileReader fileReader = new FileReader(directory);
            Gson gson = new Gson();
            Player player =  gson.fromJson(fileReader, Player.class);
            fileReader.close();
            return player;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean doesUserExists(String username) {
        String directory = getUserDirectory(username);
        File file = new File(directory);
        return file.exists();
    }

    public static boolean doesDeckExists(String deckName) {
        String directory = getDeckDirectory(deckName);
        File file = new File(directory);
        return file.exists();
    }

    public static Deck getDeckByName(String deck) throws IOException {
        String directory = getDeckDirectory(deck);
        try {
            FileReader fileReader = new FileReader(directory);
            GsonBuilder gsonBuilder = new GsonBuilder();
            RuntimeTypeAdapterFactory<Card> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
                    .of(Card.class, "type").
                            registerSubtype(MonsterCard.class, "monster").
                            registerSubtype(SpellCard.class, "spell").
                            registerSubtype(TrapCard.class, "trap");
            Deck deck1 =  gsonBuilder.registerTypeAdapterFactory(runtimeTypeAdapterFactory).create().fromJson(fileReader, Deck.class);
            fileReader.close();
            return deck1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateDeck(Deck deck) {
        ArrayList<Card> mainDeck = deck.getMainDeck();
        for (Card card : mainDeck) {
            if (card instanceof MonsterCard){
                card.setType("monster");
            }else if (card instanceof TrapCard){
                card.setType("trap");
            }else{
                card.setType("spell");
            }
        }
        String deckName = deck.getDeckName();
        try {
            FileWriter fileWriter = new FileWriter(getDeckDirectory(deckName));
            Gson gson = new Gson();
            gson.toJson(deck, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updatePlayer(Player player) {
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

    public static boolean doesNicknameExist(String nickname) {
        File folder = new File("src" + File.separator + "resources" + File.separator + "users");
        File[] files = folder.listFiles();

        assert files != null;
        for (File file : files) {
            if (file.isFile() && file.getAbsolutePath().endsWith(".json")) {
                if (getUserByName(file.getName().substring(0, file.getName().length() - 5)).getNickname().equals(nickname)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static ArrayList<Player> getAllPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        File folder = new File("src" + File.separator + "resources" + File.separator + "users");
        File[] files = folder.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isFile() && file.getAbsolutePath().endsWith(".json")) {
                players.add(getUserByName(file.getName().substring(0, file.getName().length() - 5)));
            }
        }
        return players;
    }

    public static void main(String[] args) throws IOException, CsvValidationException {
        doesNicknameExist("mo");
    }
}
