package controller;

import controller.exceptions.CardNotInPosition;
import controller.exceptions.InvalidSelection;
import model.Board;
import model.Player;
import model.card.Card;

import java.util.ArrayList;

public class GameController {
    private static GameController instance = null;
    private static Player playerOne;
    private static Player playerTwo;
    private static Player currentPlayer;
    private Card selectedCard;
    private int playerOneLp;
    private int playerTwoLp;
    private ArrayList<Card> playerOneDeck;
    private ArrayList<Card> playerTwoDeck;
    private Board gameBoard;
    private String gamePhase;
    private boolean isAI;

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

    public void startGame(String username, int numberOfRounds) {
        //TODO: IMPORTANT
    }

    public void selectPlayerCard(String fieldType) {

    }

    public void selectPlayerCard(String fieldType, int fieldNumber) throws InvalidSelection, CardNotInPosition {
        if (fieldType.equals("monster")) {
            if (fieldNumber > 5 || fieldNumber < 1) {
                throw new InvalidSelection();
            } else if (gameBoard.isEmpty(currentPlayer == playerOne ? 1 : 2,"monster",fieldNumber)){
                throw new CardNotInPosition();
            }



        } else if (fieldType.equals("spell")) {

        } else if (fieldType.equals("hand")) {

        }
    }

    public void selectOpponentCard(String fieldType) {

    }

    public void selectOpponentCard(String fieldType, int fieldNumber) {

    }

    public void deselect() {

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

}
