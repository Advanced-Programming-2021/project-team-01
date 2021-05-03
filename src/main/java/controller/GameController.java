package controller;

import controller.exceptions.*;
import model.Board;
import model.Deck;
import model.GamePhase;
import model.Player;
import model.card.Card;
import model.card.CardType;
import model.card.MonsterCard;

import java.util.Random;

public class GameController {
    protected static GameController instance = null;
    protected static Player playerOne;
    protected static Player playerTwo;
    protected static Player currentPlayer;
    protected final PhaseController phaseController = new PhaseController(this);
    protected Card selectedCard;
    protected int playerOneLp;
    protected int playerTwoLp;
    protected Board gameBoard;
    protected boolean isAI;
    protected int rounds;
    protected boolean isSummoned;

    private GameController() {

    }


    public Board getGameBoard() {
        return gameBoard;
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
        playerTwo = DatabaseController.getUserByName(username);
        playerOne = RegisterController.onlineUser;
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
        rounds = numberOfRounds;
        playerOneLp = 8000;
        playerTwoLp = 8000;
        gameBoard = new Board(playerOneDeck, playerTwoDeck);
        isAI = username.equals("AI");
        gameBoard.showBoard();
        phaseController.setGamePhase(GamePhase.DRAW_PHASE);
        isSummoned = false;
    }

    private int tossCoin() {
        return new Random().nextInt() % 2 + 1;
    }

    public void selectPlayerCard(String fieldType) throws CardNotInPosition {
        if (gameBoard.getCard(fieldType, getCurrentPlayerNumber()) == null)
            throw new CardNotInPosition();
        selectedCard = gameBoard.getCard(fieldType, getCurrentPlayerNumber());
    }

    public void selectPlayerCard(String fieldType, int fieldNumber) throws InvalidSelection, CardNotInPosition {
        switch (fieldType) {
            case "monster":
                if (fieldNumber > 5 || fieldNumber < 1) {
                    throw new InvalidSelection();
                } else if (gameBoard.getCard("monster", getCurrentPlayerNumber(), fieldNumber) == null) {
                    throw new CardNotInPosition();
                }
                selectedCard = gameBoard.getCard("monster", getCurrentPlayerNumber(), fieldNumber);
                break;
            case "spell":
                if (fieldNumber > 5 || fieldNumber < 1) {
                    throw new InvalidSelection();
                } else if (gameBoard.getCard("spell", getCurrentPlayerNumber(), fieldNumber) == null) {
                    throw new CardNotInPosition();
                }
                selectedCard = gameBoard.getCard("spell", getCurrentPlayerNumber(), fieldNumber);
                break;
            case "hand":
                if (fieldNumber > gameBoard.getNumberOfCardsInHand(getCurrentPlayerNumber()))
                    throw new InvalidSelection();
                selectedCard = gameBoard.getCard(fieldType, fieldNumber, getCurrentPlayerNumber());
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

    public void deselect() throws CardNotSelected {
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

    public void summon() throws CardNotSelected, NotSummonCard, ActivationPhaseError, MonsterZoneFull, AlreadySummonedError {
        if (selectedCard == null) {
            throw new CardNotSelected();
        }
        if ((!gameBoard.isCardInHand(selectedCard, getCurrentPlayerNumber())) ||
                !(selectedCard instanceof MonsterCard) ||
                ((MonsterCard) selectedCard).getCardType() == CardType.RITUAL) {
            throw new NotSummonCard();
        }
        if (phaseController.getGamePhase() == GamePhase.MAIN_PHASE1 ||
                phaseController.getGamePhase() == GamePhase.MAIN_PHASE2)
            throw new ActivationPhaseError();
        if (gameBoard.numberOfMonsterInZone(getCurrentPlayerNumber()) == 5)
            throw new MonsterZoneFull();
        if (isSummoned)
            throw new AlreadySummonedError();



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

    public void setSummoned(boolean summoned) {
        isSummoned = summoned;
    }

    public String nextPhase() {
        return phaseController.nextPhase();
    }

    public int getCurrentPlayerNumber() {
        return currentPlayer == playerOne ? 1 : 2;
    }
}
