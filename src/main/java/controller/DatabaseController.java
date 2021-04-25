package controller;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import model.Deck;
import model.Player;


import java.io.*;


public class DatabaseController {

    public static void loadGameCards() {
        //todo : CSV HANDLE
        //Card.allCards.add();
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
