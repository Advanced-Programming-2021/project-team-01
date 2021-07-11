package model;

import controller.DatabaseController;

import java.util.ArrayList;
import java.util.Comparator;

public class Player {
    private final String username;
    private final ArrayList<String> playerDecks = new ArrayList<>();
    private final ArrayList<String> playerCards = new ArrayList<>();
    private String password;
    private String nickname;
    private int money;
    private int score;
    private String activeDeck;
    private int profile;
    private int winRate;
    private int loseRate;
    private String authToken;

    public Player(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.winRate = 0;
        this.loseRate = 0;
        this.money = 1000000;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setPictureNumber(int pictureNumber) {
        this.profile = pictureNumber;
    }

    public int getProfile() {
        return profile;
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

    public void setActiveDeck(String deckName) {
        activeDeck = deckName;
    }

    public ArrayList<String> getPlayerDecks() {
        return playerDecks;
    }

    public void addDeck(String deckName) {
        playerDecks.add(deckName);
    }

    public void deleteDeck(String deckName) {
        playerDecks.removeIf(deck -> deck.equals(deckName));
        if (activeDeck != null && activeDeck.equals(deckName)) {
            activeDeck = null;
        }
    }

    public void decreaseMoney(int amount) {
        this.money = money - amount;
    }

    public void increaseMoney(int amount) {
        this.money = money + amount;
    }

    public ArrayList<String> getPlayerCards() {
        return playerCards;
    }

    public void addCardToPlayerCards(String cardName) {
        playerCards.add(cardName);
        DatabaseController.updatePlayer(this);
    }

    public void increaseLoseRate() {
        loseRate++;
    }

    public void increaseWinRate(int amount) {
        score += amount;
        winRate++;
    }

    public int getNumberOfCards(String cardName) {
        int counter = 0;
        for (String playerCard : playerCards) {
            if (playerCard.equals(cardName))
                counter++;
        }
        return counter;
    }

    public static class SortByScore implements Comparator<Player> {
        public int compare(Player player1, Player player2) {
            if (player1.getScore() != player2.getScore())
                return player2.getScore() - player1.getScore();
            else
                return player1.getUsername().compareTo(player2.getUsername());
        }
    }
}
