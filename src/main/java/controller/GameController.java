package controller;

import controller.exceptions.*;
import model.Board;
import model.Deck;
import model.Player;
import model.card.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameController {
    private static GameController instance = null;
    private static Player playerOne;
    private static Player playerTwo;
    private static Player currentPlayer;
    private Card selectedCard;
    private int playerOneLp;
    private int playerTwoLp;
    private Board gameBoard;
    private String gamePhase;
    private boolean isAI;
    private int rounds;

    private GameController() {

    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static Player getOpponent() {
        if (currentPlayer == playerOne) {
            return playerTwo;
        }
        return playerOne;
    }

    public void startGame(String username, int numberOfRounds) throws UsernameNotExists, NoActiveDeck, InvalidDeck, InvalidRoundNumber {
        Player playerTwo = DatabaseController.getUserByName(username);
        Player playerOne = RegisterController.onlineUser;
        if (playerTwo == null) {
            throw new UsernameNotExists();
        } else if (playerOne.getActiveDeck() == null) {
            throw new NoActiveDeck(playerOne.getUsername());
        } else if (playerTwo.getActiveDeck() == null) {
            throw new NoActiveDeck(playerTwo.getUsername());
        }
        currentPlayer = tossCoin() == 1 ? playerOne : playerTwo;
        Deck playerOneDeck = DatabaseController.getDeckByName(playerOne.getActiveDeck());
        Deck playerTwoDeck = DatabaseController.getDeckByName(playerTwo.getActiveDeck());
        if (!playerOneDeck.isDeckValid()) {
            throw new InvalidDeck(playerOne.getUsername());
        } else if (!playerTwoDeck.isDeckValid()) {
            throw new InvalidDeck(playerTwo.getUsername());
        }
        if (numberOfRounds != 1 && numberOfRounds != 3) {
            throw new InvalidRoundNumber();
        }
        Collections.shuffle((List<?>) playerOneDeck);
        Collections.shuffle((List<?>) playerTwoDeck);
        rounds = numberOfRounds;
        playerOneLp = 8000;
        playerTwoLp = 8000;
        gameBoard = new Board(playerOneDeck, playerTwoDeck, playerOne, playerTwo);
        isAI = username.equals("AI");
    }

    private int tossCoin() {
        return new Random().nextInt() % 2 + 1;
    }

    public void selectPlayerCard(String fieldType) throws CardNotInPosition {
        if (gameBoard.getCard(fieldType, currentPlayer == playerOne ? 1 : 2) == null)
            throw new CardNotInPosition();
        selectedCard = gameBoard.getCard(fieldType, currentPlayer == playerOne ? 1 : 2);
    }

    public void selectPlayerCard(String fieldType, int fieldNumber) throws InvalidSelection, CardNotInPosition {
        switch (fieldType) {
            case "monster":
                if (fieldNumber > 5 || fieldNumber < 1) {
                    throw new InvalidSelection();
                } else if (gameBoard.getCard("monster", currentPlayer == playerOne ? 1 : 2, fieldNumber) == null) {
                    throw new CardNotInPosition();
                }
                selectedCard = gameBoard.getCard("monster", currentPlayer == playerOne ? 1 : 2, fieldNumber);
                break;
            case "spell":
                if (fieldNumber > 5 || fieldNumber < 1) {
                    throw new InvalidSelection();
                } else if (gameBoard.getCard("spell", currentPlayer == playerOne ? 1 : 2, fieldNumber) == null) {
                    throw new CardNotInPosition();
                }
                selectedCard = gameBoard.getCard("spell", currentPlayer == playerOne ? 1 : 2, fieldNumber);
                break;
            case "hand":
                if (fieldNumber > gameBoard.getNumberOfCardsInHand(currentPlayer == playerOne ? 1 : 2))
                    throw new InvalidSelection();
                selectedCard = gameBoard.getCard(fieldType,fieldNumber,currentPlayer == playerOne ? 1 : 2);
                break;
        }
    }

    public void selectOpponentCard(String fieldType) throws CardNotInPosition {
        if (gameBoard.getCard(fieldType, currentPlayer == playerOne ? 2 : 1) == null)
            throw new CardNotInPosition();
        selectedCard = gameBoard.getCard(fieldType, currentPlayer == playerOne ? 2 : 1);
    }

    public void selectOpponentCard(String fieldType, int fieldNumber) throws InvalidSelection, CardNotInPosition {
        switch (fieldType) {
            case "monster":
                if (fieldNumber > 5 || fieldNumber < 1) {
                    throw new InvalidSelection();
                } else if (gameBoard.getCard("monster", currentPlayer == playerOne ? 2 : 1, fieldNumber) == null) {
                    throw new CardNotInPosition();
                }
                selectedCard = gameBoard.getCard("monster", currentPlayer == playerOne ? 2 : 1, fieldNumber);
                break;
            case "spell":
                if (fieldNumber > 5 || fieldNumber < 1) {
                    throw new InvalidSelection();
                } else if (gameBoard.getCard("spell", currentPlayer == playerOne ? 2 : 1, fieldNumber) == null) {
                    throw new CardNotInPosition();
                }
                selectedCard = gameBoard.getCard("spell", currentPlayer == playerOne ? 2 : 1, fieldNumber);
                break;
        }
    }

    public void deselect() throws CardNotSelected{
        if (selectedCard == null)
            throw new CardNotSelected();
        selectedCard = null;
    }

    public void setCard() {

    }

    public void setMonster() {

    }

    public void setSpell() {

    }

    public void goNextPhase() {

    }

    public void summon() {

    }

    public void changeCardPosition(String newPosition) {

    }

    public void flipSummon() {

    }

    public void attack(int number) {

    }

    public void directAttack() {

    }

    public void activateEffect() {

    }

    public void ritualSummon() {

    }

    public void showGraveyard() {

    }

    public void showSelectedCard() {

    }

    public void surrender() {

    }

    public int getOpponentLp() {
        if (getOpponent() == playerOne) {
            return playerOneLp;
        }
        return playerTwoLp;
    }

    public int getCurrentLp() {
        if (getCurrentPlayer() == playerTwo) {
            return playerTwoLp;
        }
        return playerOneLp;
    }

    public int getCurrentPlayerNumber() {
        if (getCurrentPlayer() == playerOne) {
            return 1;
        }
        return 2;
    }


}
