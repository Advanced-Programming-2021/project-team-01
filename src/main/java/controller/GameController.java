package controller;

import console.Menu;
import console.menu.HandleRequestType;
import controller.exceptions.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import model.*;
import model.card.*;
import view.GameView;
import view.MyAlert;
import view.View;
import view.ViewSwitcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameController {
    protected static GameController instance = null;
    protected static Player playerOne;
    protected static Player playerTwo;
    protected static Player currentPlayer;
    protected PhaseController phaseController;
    protected AttackController attackController;
    protected EffectController effectController;
    protected AiBasicController aiBasicController;
    protected IntegerProperty playerOneLp;
    protected IntegerProperty playerTwoLp;
    protected Board gameBoard;
    protected boolean isAI;
    protected Card summonedCard;
    protected int rounds;
    protected int playerOneWin;
    protected int playerTwoWin;
    protected SelectedCard selectedCard;
    protected ArrayList<Card> changedPositionCards = new ArrayList<>();
    protected ArrayList<Card> destroyedCardsForPlayerOne = new ArrayList<>();
    protected ArrayList<Card> destroyedCardsForPlayerTwo = new ArrayList<>();
    protected State state;
    protected Chain chain;
    protected ChainController chainController;
    public boolean isReversed = false;
    private OnlineGame game;
    public int controllerNumber;
    public boolean done = true;

    public void setControllerNumber(int controllerNumber) {
        this.controllerNumber = controllerNumber;
    }

    private GameController() {

    }

    public static Player getPlayerOne() {
        return playerOne;
    }

    public static Player getPlayerTwo() {
        return playerTwo;
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

    public void resetChainController() {
        this.chainController = null;
    }

    public ChainController getChainController() {
        return chainController;
    }

    public void changeTurn() {
        currentPlayer = getOpponent();
    }

    public ArrayList<Card> getDestroyedCardsForPlayerOne() {
        return destroyedCardsForPlayerOne;
    }

    public ArrayList<Card> getDestroyedCardsForPlayerTwo() {
        return destroyedCardsForPlayerTwo;
    }

    public ArrayList<Card> getDestroyedCardsPlayer(int player) {
        if (player == 1)
            return destroyedCardsForPlayerOne;
        if (player == 2)
            return destroyedCardsForPlayerTwo;
        return null;
    }

    public PhaseController getPhaseController() {
        return phaseController;
    }

    public AttackController getAttackController() {
        return attackController;
    }

    public IntegerProperty getPlayerOneLp() {
        return playerOneLp;
    }

    public IntegerProperty getPlayerTwoLp() {
        return playerTwoLp;
    }

    public boolean isAI() {
        return isAI;
    }

    public int getRounds() {
        return rounds;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public SelectedCard getSelectedCard() {
        return selectedCard;
    }

    public ArrayList<Card> getChangedPositionCards() {
        return changedPositionCards;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    protected void increaseOpponentLp(int amount) {
        if (currentPlayer == playerOne) {
            playerTwoLp.set(playerTwoLp.getValue() + amount);
            return;
        }
        playerOneLp.set(playerOneLp.getValue() + amount);
    }

    public void decreasePlayerLP(int playerNum, int amount) {
        if (playerNum == 1) {
            playerOneLp.set(playerOneLp.getValue() - amount);
        } else {
            playerTwoLp.set(playerTwoLp.getValue() - amount);
        }
    }

    public void increasePlayerLp(int amount) {
        if (currentPlayer == playerOne) {
            playerOneLp.set(playerOneLp.getValue() + amount);
            return;
        }
        playerTwoLp.set(playerTwoLp.getValue() + amount);
    }

    public void startGame(OnlineGame game) throws UsernameNotExists, NoActiveDeck, InvalidDeck, InvalidRoundNumber, IOException {
        playerTwo = game.getOpponent();
        playerOne = game.getChallenger();
        this.game = game;
//        if (isReversed)
//            swapPlayer();
        if (playerTwo == null) {
            throw new UsernameNotExists();
        } else if (playerOne.getActiveDeck() == null) {
            throw new NoActiveDeck(playerOne.getUsername());
        } else if (playerTwo.getActiveDeck() == null) {
            throw new NoActiveDeck(playerTwo.getUsername());
        }
        currentPlayer = playerOne;
        if (game.getStarterPlayer() == 2){ //fixme : what the hell
            currentPlayer = playerTwo;
        }
        Deck playerOneDeck = game.getChallengerDeck();
        Deck playerTwoDeck = game.getOpponentDeck();
        if (!playerOneDeck.isDeckValid()) {
            throw new InvalidDeck(playerOne.getUsername());
        } else if (!playerTwoDeck.isDeckValid()) {
            throw new InvalidDeck(playerTwo.getUsername());
        }
        setup(playerOneDeck);
        setup(playerTwoDeck);
        int numberOfRounds = game.getNoOfRounds();
        if (numberOfRounds == 3) {
            playerOneWin = 0;
            playerTwoWin = 0;
        }
        playerOneLp = new SimpleIntegerProperty();
        playerTwoLp = new SimpleIntegerProperty();
        playerOneLp.set(8000);
        playerTwoLp.set(8000);
        gameBoard = new Board(playerOneDeck, playerTwoDeck);
        isAI = game.getOpponent().getUsername().equals("AI");
        gameBoard.showBoard();
        setSummonedCard(null);
        selectedCard = new SelectedCard();
        phaseController = new PhaseController(this);
        effectController = new EffectController(this);
        attackController = new AttackController(this);
        aiBasicController = new AiBasicController(this);
        phaseController.setGamePhase(GamePhase.DRAW_PHASE);
        state = State.NONE;
    }

    private void setup(Deck deck) {
        for (int i = 0; i < deck.getMainDeck().size(); i++) {
            Card card = deck.getMainDeck().get(i);
            card.addCommandsToCard();
        }
    }

    public Card getSummonedCard() {
        return summonedCard;
    }

    public void setSummonedCard(Card card) {
        summonedCard = card;
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

    public AiBasicController getAiBasicController() {
        return aiBasicController;
    }

    public void deselect() throws CardNotSelected {
        if (selectedCard.getCard() == null)
            throw new CardNotSelected();
        selectedCard.reset();
    }

    public void setCard() throws Exception {
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

    public void setMonster() throws Exception {
        if (gameBoard.numberOfMonsterCards(getCurrentPlayerNumber()) == 5)
            throw new MonsterZoneFull();
        if (isSummoned())
            throw new AlreadySummonedError();
        gameBoard.setMonster(getCurrentPlayerNumber(), (MonsterCard) selectedCard.getCard());
        if (selectedCard.getCard().getName().equals(Effect.SCANNER.toString()))
            selectedCard.getCard().doActions();
        setSummonedCard(selectedCard.getCard());
    }

    public void summon() throws Exception {//fixme : hand limit
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
        state = State.SUMMON;
        if (((MonsterCard) selectedCard.getCard()).getLevel() <= 4) {
            gameBoard.summonCard((MonsterCard) selectedCard.getCard(), getCurrentPlayerNumber());
            if (selectedCard.getCard().getName().equals(Effect.SCANNER.toString()) ||
                    selectedCard.getCard().getName().equals(Effect.HERALD_OF_CREATION.toString()))
                selectedCard.getCard().doActions();

            setSummonedCard(selectedCard.getCard());
            if (selectedCard.getCard().getName().equals(Effect.TERATIGER.toString())) {
                createChain(selectedCard.getCard());
            } else {
                createChain();
            }
            chainController.chain.run();
            selectedCard.reset();
            state = State.NONE;


        } else if (((MonsterCard) selectedCard.getCard()).getLevel() == 5 ||
                ((MonsterCard) selectedCard.getCard()).getLevel() == 6) {
            throw new LevelFiveException();
        } else if (((MonsterCard) selectedCard.getCard()).getLevel() == 7 ||
                ((MonsterCard) selectedCard.getCard()).getLevel() == 8) {
            throw new LevelSevenException();
        }
    }

    public void tributeSummonLevel7(Card card1, Card card2) throws Exception {
        tribute(card1, card2);
        createChain();
        chainController.chain.run();
        state = State.NONE;
    }

    public void tributeSummonLevel5(Card card) throws Exception {
        tribute(card);
        createChain();
        chain.run();
        state = State.NONE;
    }

    public void tribute(Card card) throws Exception {
        gameBoard.sendCardFromMonsterZoneToGraveyard(card);
        gameBoard.summonCard((MonsterCard) selectedCard.getCard(), getCurrentPlayerNumber());
        setSummonedCard(selectedCard.getCard());
        selectedCard.reset();
    }

    public void tribute(Card card1, Card card2) throws Exception {
        gameBoard.sendCardFromMonsterZoneToGraveyard(card1);
        gameBoard.sendCardFromMonsterZoneToGraveyard(card2);
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
            getZoneSlotSelectedCard().setHidden(false);
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
        state = State.FLIP_SUMMON;
        Card temp = getSummonedCard();
        setSummonedCard(selectedCard.getCard());
        getZoneSlotSelectedCard().setDefending(false);
        getZoneSlotSelectedCard().setHidden(false);
        createChain();
        chain.setNext(summonedCard);
        chain.run();
        setSummonedCard(temp);
        selectedCard.reset();
    }

    public EffectController getEffectController() {
        return effectController;
    }

    public String attack(int number) throws Exception {
        state = State.ATTACK;
        String result = attackController.attack(number);
        selectedCard.reset();
        state = State.NONE;
        return result;
    }

    public String directAttack() throws Exception {
        state = State.ATTACK;
        String result = attackController.directAttack();
        selectedCard.reset();
        state = State.NONE;
        return result;
    }

    public void activateEffect() throws Exception {
        state = State.ACTIVE_SPELL;
        if (selectedCard.getCard() instanceof SpellCard) {
            if (!selectedCard.getCard().canActivate()) {
                state = State.NONE;
                throw new Exception("You cant activate this card");
            }
            SpellCard card = (SpellCard) selectedCard.getCard();
            if (card.getProperty() == Property.RITUAL) {
                System.out.println("hi");
            } else if (!(card.getProperty() == Property.FIELD) && selectedCard.getCardLocation() == CardLocation.HAND) {
                gameBoard.setSpell(getCurrentPlayerNumber(), card);
                gameBoard.setSpellFaceUp(selectedCard.getCard());
            }
            for (int i = 0; i < gameBoard.getCardInSpellZone(getOpponentPlayerNumber()).size(); i++) {
                if (gameBoard.getCardInSpellZone(getOpponentPlayerNumber()).get(i).getName().equals("Spell Absorption"))
                    decreasePlayerLP(getOpponentPlayerNumber(), -500);
            }
        } else if (selectedCard.getCard() instanceof TrapCard) {
            TrapCard trapCard = (TrapCard) selectedCard.getCard();
            if (!trapCard.getName().equals((Effect.MIND_CRUSH).toString())) {
                state = State.NONE;
                throw new Exception("You cant activate this card");
            }
        }
        createChain(selectedCard.getCard());
        chain.run();
        state = State.NONE;
    }

    private void getRitualCard() throws Exception {
        ArrayList<Card> temp = new ArrayList<>();
        List<Card> hand;
        ArrayList<Card> monsterCards;
        if (getCurrentPlayerNumber() == 1) {
            hand = gameBoard.getPlayerOneHand();
            monsterCards = gameBoard.getCardInMonsterZone(1);
        } else {
            hand = gameBoard.getPlayerTwoHand();
            monsterCards = gameBoard.getCardInMonsterZone(2);
        }
        for (Card card : hand) {
            if (card instanceof MonsterCard) {
                if (((MonsterCard) card).getCardType().equals(CardType.RITUAL)) {
                    temp.add(card);
                }
            }
        }
        checkRitualLevel(temp, monsterCards);
        MonsterCard dedicated = (MonsterCard) view.GameView.getNeededCards(temp, 1).get(0);
        List<Card> selectedMonsters = GameView.selectedCardsWithSelectableDialog(monsterCards);
        int levelOfMonsters = 0;
        for (Card selectedMonster : selectedMonsters) {
            levelOfMonsters += ((MonsterCard) selectedMonster).getLevel();
        }
        if (dedicated.getLevel() > levelOfMonsters)
            throw new Exception("selected monsters dont match with ritual monsters");
        for (Card monster : selectedMonsters) {
            gameBoard.sendCardFromMonsterZoneToGraveyard(monster);
        }
        gameBoard.setSpell(getCurrentPlayerNumber(), (SpellCard) selectedCard.getCard());
        gameBoard.setSpellFaceUp(selectedCard.getCard());
        gameBoard.summonCard(dedicated, getCurrentPlayerNumber());
        gameBoard.sendCardFromSpellZoneToGraveyard(selectedCard.getCard());
    }

    private void checkRitualLevel(ArrayList<Card> temp, ArrayList<Card> monsterCards) throws Exception {
        if (temp.size() == 0) {
            throw new Exception("There is no way you could ritual summon a monster");
        }
        int totalLevel = 0;
        for (Card monsterCard : monsterCards) {
            if (monsterCard instanceof MonsterCard) {
                totalLevel += ((MonsterCard) monsterCard).getLevel();
            }
        }
        int minimumLevel = 80;
        for (Card card : temp) {
            if (card instanceof MonsterCard) {
                if (((MonsterCard) card).getLevel() < minimumLevel) {
                    minimumLevel = ((MonsterCard) card).getLevel();
                }
            }
        }
        if (minimumLevel > totalLevel) {
            throw new Exception("There is no way you could ritual summon a monster");
        }
    }

    public void ritualSummon() throws Exception {
        getRitualCard();
    }

    public void setWinner(String nickname) throws Exception {
        if (playerOne.getNickname().equals(nickname)) {
            //playerOneWin = 2;
            playerTwoLp.set(0);
        } else if (playerTwo.getNickname().equals(nickname)) {
            //playerTwoWin = 2;
            playerOneLp.set(0);
        } else
            throw new Exception("Nickname is invalid!");
        finishGame();
    }

    public String showGraveyard() {
        return gameBoard.showGraveyard(getCurrentPlayerNumber());
    }

    public String showSelectedCard() throws CardNotSelected, HiddenCardError {//TODO : Ino dorost konid attack o def neshon nade
        if (selectedCard.getCard() == null)
            throw new CardNotSelected();
        if (selectedCard.getPlayer() == getOpponent()) {
            if (gameBoard.isOpponentCardHidden(selectedCard.getCard(), getOpponentPlayerNumber()))
                throw new HiddenCardError();
            else if (selectedCard.getCard() instanceof MonsterCard)
                if (gameBoard.isCardInHand(selectedCard.getCard()))
                    return selectedCard.getCard().getName() + "- attack = " + ((MonsterCard) selectedCard.getCard()).getAttack() + " defense = " +
                            ((MonsterCard) selectedCard.getCard()).getDefense() + " : " + selectedCard.getCard().getDescription();
                else
                    return selectedCard.getCard().getName() + "- attack = " + getZoneSlotSelectedCard().getAttack() + " defense = " +
                            getZoneSlotSelectedCard().getDefence() + " : " + selectedCard.getCard().getDescription();
            else
                return selectedCard.getCard().getName() + ":" + selectedCard.getCard().getDescription();
        } else {
            if (selectedCard.getCard() instanceof MonsterCard)
                if (gameBoard.isCardInHand(selectedCard.getCard()))
                    return selectedCard.getCard().getName() + "- attack = " + ((MonsterCard) selectedCard.getCard()).getAttack() + " defense = " +
                            ((MonsterCard) selectedCard.getCard()).getDefense() + " : " + selectedCard.getCard().getDescription();
                else
                    return selectedCard.getCard().getName() + "- attack = " + getZoneSlotSelectedCard().getAttack() + " defense = " +
                            getZoneSlotSelectedCard().getDefence() + " : " + selectedCard.getCard().getDescription();
            else
                return selectedCard.getCard().getName() + ":" + selectedCard.getCard().getDescription();

        }
    }

    public void surrender() {
        currentPlayer.increaseLoseRate();
    }

    public int getOpponentLp() {
        if (getOpponent() == playerOne) {
            return playerOneLp.getValue();
        }
        return playerTwoLp.getValue();
    }

    public GamePhase getGamePhase() {
        return phaseController.getGamePhase();
    }

    public int getCurrentLp() {
        if (getCurrentPlayer() == playerTwo) {
            return playerTwoLp.getValue();
        }
        return playerOneLp.getValue();
    }

    public String nextPhase() throws Exception {
        return phaseController.nextPhase();
    }

    public int getCurrentPlayerNumber() {
        return currentPlayer == playerOne ? 1 : 2;
    }

    public int getOpponentPlayerNumber() {
        return currentPlayer == playerOne ? 2 : 1;
    }

    public ZoneSlot getZoneSlotSelectedCard() {
        return gameBoard.getZoneSlotByCard(selectedCard.getCard());
    }

    public boolean isCardChangedBefore(Card card) {
        for (Card changedPositionCard : changedPositionCards) {
            if (changedPositionCard == card)
                return true;
        }
        return false;
    }


    public void resetChangedCard() {
        changedPositionCards.clear();
        attackController.attackedCards.clear();
        destroyedCardsForPlayerOne.clear();
        destroyedCardsForPlayerTwo.clear();
    }

    public void cheater(String cardName) throws Exception {
        Card card = Card.getCardByName(cardName);
        if (card == null) {
            System.err.println("TYPO DETECTED");
            return;
        }
        Card newCard;
        if (card instanceof MonsterCard) {
            newCard = (Card) ((MonsterCard) card).clone();
        } else if (card instanceof SpellCard)
            newCard = (Card) ((SpellCard) card).clone();
        else if (card instanceof TrapCard)
            newCard = (Card) ((TrapCard) card).clone();
        else
            throw new Exception("HAHAHA");
        newCard.addCommandsToCard();
        gameBoard.addCardCheatToHand(newCard, getCurrentPlayerNumber());
    }

    public boolean isGameFinished() {
        return playerOneLp.getValue() <= 0 || playerTwoLp.getValue() <= 0;
    }

    public String finishGame() throws NoActiveDeck, InvalidDeck, UsernameNotExists, InvalidRoundNumber, IOException {
        if (rounds == 1) {
            if (playerOneLp.getValue() <= 0) {
                playerOne.increaseLoseRate();
                playerTwo.increaseWinRate(1000);
                playerOne.increaseMoney(100);
                playerTwo.increaseMoney(1000 + playerTwoLp.getValue());
                DatabaseController.updatePlayer(playerOne);
                DatabaseController.updatePlayer(playerTwo);
                GameView.getInstance().reset();
                new MyAlert(Alert.AlertType.CONFIRMATION, "player one win!").show();
                ViewSwitcher.switchTo(View.MAIN);
                return String.format("%s won the game and the score is: %d-%d",
                        playerOneLp.getValue() <= 0 ? playerTwo.getUsername() : playerOne.getUsername(), 100, 1000 + playerTwoLp.getValue());
            } else {
                playerTwo.increaseLoseRate();
                playerOne.increaseWinRate(1000);
                playerTwo.increaseMoney(100);
                playerOne.increaseMoney(1000 + playerOneLp.getValue());
                DatabaseController.updatePlayer(playerOne);
                DatabaseController.updatePlayer(playerTwo);
                GameView.getInstance().reset();
                new MyAlert(Alert.AlertType.CONFIRMATION, "player two win!").show();
                ViewSwitcher.switchTo(View.MAIN);
                return String.format("%s won the game and the score is: %d-%d",
                        playerOneLp.getValue() <= 0 ? playerTwo.getUsername() : playerOne.getUsername(), 1000 + playerOneLp.getValue(), 100);
            }
        } else {
            if ((playerOneWin == 1 && playerTwoWin == 1) || (playerOneWin == 2 || playerTwoWin == 2)){
                if (playerOneLp.getValue() <= 0) {
                    playerOne.increaseLoseRate();
                    playerTwo.increaseWinRate(3000);
                    playerOne.increaseMoney(300);
                    playerTwo.increaseMoney(3000 + 3 * playerTwoLp.getValue());
                    DatabaseController.updatePlayer(playerOne);
                    DatabaseController.updatePlayer(playerTwo);
                    GameView.getInstance().reset();
                    new MyAlert(Alert.AlertType.CONFIRMATION, "player two win!").show();
                    ViewSwitcher.switchTo(View.MAIN);
                    return String.format("%s won the game and the score is: %d-%d",
                            playerOneLp.getValue() <= 0 ? playerTwo.getUsername() : playerOne.getUsername(), 300, 3000 + playerTwoLp.getValue());
                } else {
                    playerTwo.increaseLoseRate();
                    playerOne.increaseWinRate(3000);
                    playerTwo.increaseMoney(300);
                    playerOne.increaseMoney(3000 + 3 * playerOneLp.getValue());
                    DatabaseController.updatePlayer(playerOne);
                    DatabaseController.updatePlayer(playerTwo);
                    HandleRequestType.currentMenu = Menu.MAIN_MENU;
                    GameView.getInstance().reset();
                    new MyAlert(Alert.AlertType.CONFIRMATION, "player one win!").show();
                    ViewSwitcher.switchTo(View.MAIN);
                    return String.format("%s won the game and the score is: %d-%d",
                            playerOneLp.getValue() <= 0 ? playerTwo.getUsername() : playerOne.getUsername(), 3000 + playerOneLp.getValue(), 300);
                }
            }  else {
                if (playerOneLp.getValue() <= 0)
                    playerTwoWin++;
                else
                    playerOneWin++;
                if (isReversed) swapPlayer();
                startGame(game); //fixme : buggy maybe
                game.setRounds(2);
                ViewSwitcher.switchTo(View.GAME_VIEW);
            }
        }
        return "";
    }

    public void createChain(Card... cards) throws Exception {
        chain = new Chain(cards);
        chainController = new ChainController(chain);
        chainController.run();
    }

    public void endJunit() {
        HandleRequestType.currentMenu = Menu.REGISTER_MENU;
        RegisterController.onlineUser = null;
    }

    public void doAction() {
        System.out.println(Thread.currentThread().getName());
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("hello");
    }

    public void reverse() {
        isReversed = true;

    }

    public void swapPlayer(){
        int winTemp = playerOneWin;
        playerOneWin = playerTwoWin;
        playerTwoWin = winTemp;
        Player temp = playerOne;
        playerOne = playerTwo;
        playerTwo = temp;
    }

    public void resetSummon() {
        summonedCard = null;
    }

    public void swap() {
        Player temp = playerOne;
        playerOne = playerTwo;
        playerTwo = temp;
        IntegerProperty tempLIfe = playerOneLp;
        playerOneLp = playerTwoLp;
        playerTwoLp = tempLIfe;
        int winTemp = playerOneWin;
        playerOneWin = playerTwoWin;
        playerTwoWin = winTemp;
        gameBoard.swap();
    }

    public boolean isDone() {
        return done;
    }
}


