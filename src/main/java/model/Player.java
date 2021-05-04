package model;

import com.google.gson.Gson;
import model.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    private int loseRate;

    public Player(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.winRate = 0;
        this.loseRate = 0;
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

    public String getActiveDeck() {
        return activeDeck;
    }

    public ArrayList<String> getPlayerDecks() {
        return playerDecks;
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

    public void addCardToPlayerCards(String cardName) {
        playerCards.add(cardName);
    }

    public void increaseLoseRate() {
        loseRate++;
    }

    public void increaseWinRate() {
        winRate++;
    }

    public static class SortByScore implements Comparator<Player> {
        public int compare(Player player1, Player player2) {
            if (player1.getScore() != player2.getScore())
                return player1.getScore() - player2.getScore();
            else
                return player1.getUsername().compareTo(player2.getUsername());
        }
    }
}
