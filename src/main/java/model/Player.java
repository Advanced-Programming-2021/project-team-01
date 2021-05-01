package model;

import com.google.gson.Gson;
import model.card.Card;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
    private String username;
    private String password;
    private String nickname;
    private int money;
    private int score;
    private String activeDeck;
    private ArrayList<String> playerDecks = new ArrayList<>();
    private ArrayList<String> playerCards = new ArrayList<>();  //TODO: string or card
    private int winRate;
    private int looseRate;

    public Player(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.winRate = 0;
        this.looseRate = 0;
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

    public void addDeck(String deckName) {
        playerDecks.add(deckName);
    }

    public void deleteDeck(String deckName) {
        playerDecks.removeIf(deck -> deck.equals(deckName));
        if (activeDeck.equals(deckName)) {
            activeDeck = null;
        }
    }

    public void decreaseMoney(int amount) {
        this.money = money - amount;
    }

    public void setActiveDeck(String deckName) {
        activeDeck = deckName;
    }

    public ArrayList<String> getPlayerCards() {
        return playerCards;
    }

    public Deck getDeckByName(String name) {
        return null;
    }

    public void addCardToPlayerCards(String cardName){
        playerCards.add(cardName);
    }
}
