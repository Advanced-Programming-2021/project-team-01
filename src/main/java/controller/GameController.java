package controller;

import controller.exceptions.*;
import model.*;
import model.card.*;

import javax.swing.tree.ExpandVetoException;
import java.util.ArrayList;
import java.util.Random;

public class GameController {
    protected static GameController instance = null;
    protected static Player playerOne;
    protected static Player playerTwo;
    protected static Player currentPlayer;
    protected PhaseController phaseController;
    protected AttackController attackController;
    protected EffectController effectController;
    protected int playerOneLp;
    protected int playerTwoLp;
    protected Board gameBoard;
    protected boolean isAI;
    protected Card summonedCard;
    protected int rounds;
    protected SelectedCard selectedCard;
    protected ArrayList<Card> changedPositionCards = new ArrayList<>();

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

    protected void increaseOpponentLp(int amount) {
        if (currentPlayer == playerOne) {
            playerTwoLp += amount;
            return;
        }
        playerOneLp += amount;
    }

    protected void increasePlayerLp(int amount) {
        if (currentPlayer == playerOne) {
            playerOneLp += amount;
            return;
        }
        playerTwoLp += amount;
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
        currentPlayer = playerOne;//TODO: SHASMAGHZ NABASHIM DAR SHAFEL
        //currentPlayer = tossCoin() == 1 ? playerOne : playerTwo;
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
        setSummonedCard(null);
        selectedCard = new SelectedCard();
        phaseController = new PhaseController(this);
        effectController = new EffectController(this);
        attackController = new AttackController(this);
        phaseController.setGamePhase(GamePhase.DRAW_PHASE);
    }

    private int tossCoin() {
        return new Random().nextInt(2) + 1;
    }

    public void selectPlayerCard(String fieldType) throws CardNotInPosition {
        if (gameBoard.getCard(fieldType, getCurrentPlayerNumber()) == null)
            throw new CardNotInPosition();
        selectedCard.set(gameBoard.getCard(fieldType, getCurrentPlayerNumber()),
                getCurrentPlayer(), 1, CardLocation.FIELD);
    }

    public void selectPlayerCard(String fieldType, int fieldNumber) throws InvalidSelection, CardNotInPosition {
        switch (fieldType) {
            case "monster":
                if (fieldNumber > 5 || fieldNumber < 1) {
                    throw new InvalidSelection();
                } else if (gameBoard.getCard("monster", getCurrentPlayerNumber(), fieldNumber) == null) {
                    throw new CardNotInPosition();
                }
                selectedCard.set(gameBoard.getCard("monster", getCurrentPlayerNumber(), fieldNumber),
                        getCurrentPlayer(), fieldNumber, CardLocation.MONSTER);
                break;
            case "spell":
                if (fieldNumber > 5 || fieldNumber < 1) {
                    throw new InvalidSelection();
                } else if (gameBoard.getCard("spell", getCurrentPlayerNumber(), fieldNumber) == null) {
                    throw new CardNotInPosition();
                }
                selectedCard.set(gameBoard.getCard("spell", getCurrentPlayerNumber(), fieldNumber),
                        getCurrentPlayer(), fieldNumber, CardLocation.SPELL);
                break;
            case "hand":
                if (fieldNumber > gameBoard.getNumberOfCardsInHand(getCurrentPlayerNumber()) ||
                        fieldNumber <= 0)
                    throw new InvalidSelection();
                selectedCard.set(gameBoard.getCard(fieldType, getCurrentPlayerNumber(), fieldNumber),
                        getCurrentPlayer(), fieldNumber, CardLocation.HAND);
                break;
        }
    }

    public void selectOpponentCard(String fieldType) throws CardNotInPosition {
        if (gameBoard.getCard(fieldType, getOpponentPlayerNumber()) == null)
            throw new CardNotInPosition();
        selectedCard.set(gameBoard.getCard(fieldType, getOpponentPlayerNumber()),
                getOpponent(), 1, CardLocation.FIELD);
    }

    public void selectOpponentCard(String fieldType, int fieldNumber) throws InvalidSelection, CardNotInPosition {
        switch (fieldType) {
            case "monster":
                if (fieldNumber > 5 || fieldNumber < 1) {
                    throw new InvalidSelection();
                } else if (gameBoard.getCard("monster", getOpponentPlayerNumber(), fieldNumber) == null) {
                    throw new CardNotInPosition();
                }
                selectedCard.set(gameBoard.getCard("monster", getOpponentPlayerNumber(), fieldNumber),
                        getOpponent(), fieldNumber, CardLocation.MONSTER);
                break;
            case "spell":
                if (fieldNumber > 5 || fieldNumber < 1) {
                    throw new InvalidSelection();
                } else if (gameBoard.getCard("spell", getOpponentPlayerNumber(), fieldNumber) == null) {
                    throw new CardNotInPosition();
                }
                selectedCard.set(gameBoard.getCard("spell", getOpponentPlayerNumber(), fieldNumber),
                        getOpponent(), fieldNumber, CardLocation.SPELL);
                break;
        }
    }

    public void deselect() throws CardNotSelected {
        if (selectedCard.getCard() == null)
            throw new CardNotSelected();
        selectedCard.reset();
    }

    public void setCard() throws CardNotSelected, CardNotInHand, ActivationPhaseError, MonsterZoneFull, AlreadySummonedError, SpellZoneFullError {
        if (selectedCard.getCard() == null)
            throw new CardNotSelected();
        if (selectedCard.getCardLocation() != CardLocation.HAND)
            throw new CardNotInHand();
        if (phaseController.getGamePhase() != GamePhase.MAIN_PHASE1 &&
                phaseController.getGamePhase() != GamePhase.MAIN_PHASE2)
            throw new ActivationPhaseError();
        if (selectedCard.getCard() instanceof MonsterCard)
            setMonster();
        else if (selectedCard.getCard() instanceof SpellCard)
            gameBoard.setSpell(getCurrentPlayerNumber(), (SpellCard) selectedCard.getCard());
        else
            gameBoard.setTrap(getCurrentPlayerNumber(), (TrapCard) selectedCard.getCard());
        selectedCard.reset();
    }

    public void setMonster() throws MonsterZoneFull, AlreadySummonedError {
        if (gameBoard.numberOfMonsterCards(getCurrentPlayerNumber()) == 5)
            throw new MonsterZoneFull();
        if (isSummoned())
            throw new AlreadySummonedError();
        gameBoard.setMonster(getCurrentPlayerNumber(), (MonsterCard) selectedCard.getCard());
        setSummonedCard(selectedCard.getCard());
    }

    public void summon() throws Exception {
        if (selectedCard.getCard() == null) {
            throw new CardNotSelected();
        }
        if ((selectedCard.getCardLocation() != CardLocation.HAND) ||
                !(selectedCard.getCard() instanceof MonsterCard) ||
                ((MonsterCard) selectedCard.getCard()).getCardType() == CardType.RITUAL) {
            throw new NotSummonCard();
        }
        if (phaseController.getGamePhase() != GamePhase.MAIN_PHASE1 &&
                phaseController.getGamePhase() != GamePhase.MAIN_PHASE2)
            throw new ActivationPhaseError();
        if (gameBoard.numberOfMonsterCards(getCurrentPlayerNumber()) == 5) {
            throw new MonsterZoneFull();
        }
        if (isSummoned()) {
            throw new AlreadySummonedError();
        }
        if (((MonsterCard) selectedCard.getCard()).getLevel() <= 4) {
            gameBoard.summonCard((MonsterCard) selectedCard.getCard(), getCurrentPlayerNumber());
            setSummonedCard(selectedCard.getCard());
            selectedCard.reset();
        } else if (((MonsterCard) selectedCard.getCard()).getLevel() == 5 ||
                ((MonsterCard) selectedCard.getCard()).getLevel() == 6) {
            throw new LevelFiveException();
        } else if (((MonsterCard) selectedCard.getCard()).getLevel() == 7 ||
                ((MonsterCard) selectedCard.getCard()).getLevel() == 8) {
            throw new LevelSevenException();
        }

    }

    public void tributeSummonLevel7(int indexOfCard1, int indexOfCard2) throws Exception {
        if (gameBoard.numberOfMonsterCards(getCurrentPlayerNumber()) < 2)
            throw new NotEnoughTribute();
        if (gameBoard.getCardFromMonsterZone(indexOfCard1, getCurrentPlayerNumber()) == null ||
                gameBoard.getCardFromMonsterZone(indexOfCard2, getCurrentPlayerNumber()) == null)
            throw new NoMonsterInMultiplePositions();
        tribute(indexOfCard1, indexOfCard2);
    }

    public void tributeSummonLevel5(int indexOfCard) throws Exception {
        if (gameBoard.numberOfMonsterCards(getCurrentPlayerNumber()) == 0)
            throw new NotEnoughTribute();
        if (gameBoard.getCardFromMonsterZone(indexOfCard, getCurrentPlayerNumber()) == null)
            throw new NoMonsterInPosition();
        tribute(indexOfCard);
    }

    public void tribute(int indexOfCard) throws Exception {
        gameBoard.sendCardFromMonsterZoneToGraveyard(indexOfCard, getCurrentPlayerNumber());
        gameBoard.summonCard((MonsterCard) selectedCard.getCard(), getCurrentPlayerNumber());
        setSummonedCard(selectedCard.getCard());
        selectedCard.reset();
    }

    public void tribute(int indexOfCard1, int indexOfCard2) throws Exception {
        gameBoard.sendCardFromMonsterZoneToGraveyard(indexOfCard1, getCurrentPlayerNumber());
        gameBoard.sendCardFromMonsterZoneToGraveyard(indexOfCard2, getCurrentPlayerNumber());
        gameBoard.summonCard((MonsterCard) selectedCard.getCard(), getCurrentPlayerNumber());
        setSummonedCard(selectedCard.getCard());
        selectedCard.reset();
    }

    public boolean isSummoned() {
        return summonedCard != null;
    }

    public void changeCardPosition(String newPosition) throws Exception {
        if (selectedCard.getCard() == null)
            throw new CardNotSelected();
        if (selectedCard.getCardLocation() != CardLocation.MONSTER)
            throw new NotChangablePosition();
        if (phaseController.getGamePhase() != GamePhase.MAIN_PHASE1 &&
                phaseController.getGamePhase() != GamePhase.MAIN_PHASE2)
            throw new ActivationPhaseError();
        if ((!getZoneSlotSelectedCard().toString().equals("OO") && newPosition.equals("defense")) ||
                (!getZoneSlotSelectedCard().toString().equals("DO") && newPosition.equals("attack")))
            throw new AlreadyInWantedPosition();
        if (isCardChangedBefore(selectedCard.getCard()))
            throw new AlreadyChangedPosition();

        changedPositionCards.add(selectedCard.getCard());
        if (newPosition.equals("defense")) {
            getZoneSlotSelectedCard().setDefending(true);
        } else if (newPosition.equals("attack")) {
            getZoneSlotSelectedCard().setDefending(false);
            getZoneSlotSelectedCard().setHidden(false);
        }
    }

    public void flipSummon() throws Exception {
        if (selectedCard.getCard() == null)
            throw new CardNotSelected();
        if (selectedCard.getCardLocation() != CardLocation.MONSTER)
            throw new NotChangablePosition();
        if (phaseController.getGamePhase() != GamePhase.MAIN_PHASE1 &&
                phaseController.getGamePhase() != GamePhase.MAIN_PHASE2)
            throw new ActivationPhaseError();
        if (!getZoneSlotSelectedCard().toString().equals("DH") || summonedCard == selectedCard.getCard())
            throw new NotFlippSummon();
        getZoneSlotSelectedCard().setDefending(false);
        getZoneSlotSelectedCard().setHidden(false);
        selectedCard.reset();
    }

    public String attack(int number) throws CardNotSelected, NotMonsterCard, NotAllowedAction, AlreadyAttacked, NoCardToAttack {
        return attackController.attack(number);
    }

    public String directAttack() throws AlreadyAttacked, CardNotSelected, NotAllowedAction, NotMonsterCard, DirectAttackError {
        return attackController.directAttack();
    }

    public void activateEffect() throws PromptException, Exception {
        effectController.run(Spell.getSpellByName(selectedCard.getCard().getName()));
    }

    public void ritualSummon() {

    }

    public String showGraveyard() {
        return gameBoard.showGraveyard(getCurrentPlayerNumber());
    }

    public String showSelectedCard() throws CardNotSelected, HiddenCardError {
        if (selectedCard.getCard() == null)
            throw new CardNotSelected();
        if (selectedCard.getPlayer() == getOpponent()) {
            if (gameBoard.isOpponentCardHidden(selectedCard.getCard(), getOpponentPlayerNumber()))
                throw new HiddenCardError();
            else
                return selectedCard.getCard().getName() + ":" + selectedCard.getCard().getDescription();
        } else {
            return selectedCard.getCard().getName() + ":" + selectedCard.getCard().getDescription();
        }
    }

    public void surrender() {
        currentPlayer.increaseLoseRate();
    }

    public void sendCardFromHandToSpellZone() throws SpellZoneFullError {
        if (selectedCard.getCardLocation() == CardLocation.HAND) {
            SpellCard card = (SpellCard) selectedCard.getCard();
            gameBoard.setSpell(getCurrentPlayerNumber(), card);
            gameBoard.getZoneSlotByCard(card).setHidden(false);
        }
    }

    public int getOpponentLp() {
        if (getOpponent() == playerOne) {
            return playerOneLp;
        }
        return playerTwoLp;
    }

    public GamePhase getGamePhase() {
        return phaseController.getGamePhase();
    }

    public int getCurrentLp() {
        if (getCurrentPlayer() == playerTwo) {
            return playerTwoLp;
        }
        return playerOneLp;
    }

    public void setSummonedCard(Card card) {
        summonedCard = card;
    }

    public String nextPhase() {
        return phaseController.nextPhase();
    }

    public int getCurrentPlayerNumber() {
        return currentPlayer == playerOne ? 1 : 2;
    }

    public int getOpponentPlayerNumber() {
        return currentPlayer == playerOne ? 2 : 1;
    }

    public ZoneSlot getZoneSlotSelectedCard() {
        return gameBoard.getZoneSlotByLocation(selectedCard.getCardLocation(), selectedCard.getIndex(), getCurrentPlayerNumber());
    }

    public boolean isCardChangedBefore(Card card) {
        for (Card changedPositionCard : changedPositionCards) {
            if (changedPositionCard == card)
                return true;
        }
        return false;
    }

    public void decreasePlayerLP(int playerNum, int amount) {
        if (playerNum == 1) {
            playerOneLp -= amount;
        } else {
            playerTwoLp -= amount;
        }
    }

    public void resetChangedCard() {
        changedPositionCards.clear();
        attackController.attackedCards.clear();
    }

    public void cheater(String cardName) {
        Card card = Card.getCardByName(cardName);
        if (card == null) throw new RuntimeException();
        gameBoard.addCardCheatToHand(card, getCurrentPlayerNumber());
    }

    public boolean isGameFinished() {
        return playerOneLp <= 0 || playerTwoLp <= 0;
    }

    public void finishGame() {
        if (playerOneLp <= 0) {
            playerOne.increaseLoseRate();
            playerTwo.increaseWinRate();
            //TODO: if game is 3 taei bayad game jadid start kone
        } else {
            playerTwo.increaseLoseRate();
            playerOne.increaseWinRate();
        }
    }
}
