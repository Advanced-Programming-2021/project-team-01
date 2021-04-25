package controller;

import com.google.gson.Gson;
import model.Deck;
import model.Player;

import java.io.*;

public class DatabaseController {

    public static void loadGameCards() {
        //todo : CSV HANDLE
        //Card.allCards.add();
    }

    public static void write(String data, String directory) {
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
        return "resources" + File.separator + "users" + File.separator + username;
    }

    public static String getDeckDirectory(String deck) {
        return "resources" + File.separator + "deck" + File.separator + deck;
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

    public static void main(String[] args) {
        try {
            Gson gson = new Gson();
            FileWriter fileWriter = new FileWriter("x.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            gson.toJson("\"name\":\"ali\"", bufferedWriter);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player getUserFromDirectory(String username) {
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
}
