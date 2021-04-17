package model;

import model.card.Card;

import java.util.ArrayList;

public class Player {
    private String username;
    private String password;
    private String nickname;
    private int money;
    private int score;
    private Deck activeDeck;
    private ArrayList<Deck> playerDecks;
    private ArrayList<Card> playerCards;

    public Player(String username, String nickname, String password) {

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public int getMoney() {
        return money;
    }

    public void setActiveDeck(Deck activeDeck) {
        this.activeDeck = activeDeck;
    }

    public void addDeck(Deck deck) {

    }

    public void deleteDeck(Deck deck) {

    }

    public ArrayList<Deck> getAllDecks() {
        return playerDecks;
    }

    public Deck getDeckByName(String name) {

    }
}
