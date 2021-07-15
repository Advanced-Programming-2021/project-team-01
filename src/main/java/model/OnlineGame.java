package model;

import Network.Server.Server;
import controller.DatabaseController;
import model.card.Card;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;


public class OnlineGame {
    private Player challenger;
    private Player opponent;
    Deck challengerDeck;
    Deck opponentDeck;
    boolean isChallengerSecond;
    private int noOfRounds;

    public OnlineGame(String challenger, String opponent, boolean isChallengerSecond, int noOfRounds) {
        this.challenger = DatabaseController.getUserByName(challenger);
        this.opponent = DatabaseController.getUserByName(opponent);
        this.noOfRounds = noOfRounds;
        this.isChallengerSecond = isChallengerSecond;
        try {
            challengerDeck = DatabaseController.getDeckByName(DatabaseController.getUserByName(challenger).getActiveDeck());
            opponentDeck = DatabaseController.getDeckByName(DatabaseController.getUserByName(opponent).getActiveDeck());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.shuffle(challengerDeck.getMainDeck());
        Collections.shuffle(opponentDeck.getMainDeck());
        HashMap<String, OnlineGame> games = Server.getOnlineGames();
        games.put(challenger ,this);

    }

    public Player getChallenger() {
        return challenger;
    }

    public Player getOpponent() {
        return opponent;
    }

    public Deck getChallengerDeck() {
        return challengerDeck;
    }

    public Deck getOpponentDeck() {
        return opponentDeck;
    }

    public boolean isChallengerSecond() {
        return isChallengerSecond;
    }

    public int getNoOfRounds() {
        return noOfRounds;
    }

    public void swapPlayers(){
        Player tempPlayer;
        Deck tempDeck;
        tempPlayer = challenger;
        tempDeck = challengerDeck;
        challengerDeck = opponentDeck;
        challenger = opponent;
        opponent = tempPlayer;
        opponentDeck = tempDeck;
    }

    public void setRounds(int i) {
        noOfRounds = i;
    }
}
