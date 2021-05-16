package controller;

import controller.exceptions.*;
import model.*;
import model.card.*;
import view.Menu;
import view.menu.HandleRequestType;

import java.io.IOException;
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
    protected AiBasicController aiBasicController;
    protected int playerOneLp;
    protected int playerTwoLp;
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

    public int getPlayerOneLp() {
        return playerOneLp;
    }

    public int getPlayerTwoLp() {
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

    public void startGame(String username, int numberOfRounds) throws UsernameNotExists, NoActiveDeck, InvalidDeck, InvalidRoundNumber, IOException {
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
        //currentPlayer = tossCoin() == 1 ? playerOne : playerTwo; //TODO : AI IS ALwAys player two
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
        if (numberOfRounds == 3) {
            playerOneWin = 0;
            playerTwoWin = 0;
        }
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
        aiBasicController = new AiBasicController(this);
        phaseController.setGamePhase(GamePhase.DRAW_PHASE);
        setup(playerOneDeck);
        setup(playerTwoDeck);
        state = State.NONE;
    }

    private void setup(Deck deck) {
        for (Card card : deck.getMainDeck()) {
            card.addCommandsToCard();
        }
    }

    private int tossCoin() {
        return new Random().nextInt(2) + 1;
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

    public void summon() throws Exception {             //fixme : hand limit
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
            setSummonedCard(selectedCard.getCard());
            selectedCard.reset();
            createChain();
            chainController.chain.run();
            state = State.NONE;
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
        createChain();
        chainController.chain.run();
        state = State.NONE;
    }

    public void tributeSummonLevel5(int indexOfCard) throws Exception {
        if (gameBoard.numberOfMonsterCards(getCurrentPlayerNumber()) == 0)
            throw new NotEnoughTribute();
        if (gameBoard.getCardFromMonsterZone(indexOfCard, getCurrentPlayerNumber()) == null)
            throw new NoMonsterInPosition();
        tribute(indexOfCard);
        createChain();
        chainController.chain.run();
        state = State.NONE;
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
        if (isGameFinished()) {
            finishGame();
            return String.format("%s won the game and the score is: %d-%d",
                    playerOneLp <= 0 ? playerTwo.getUsername() : playerOne.getUsername(), playerOne.getScore(), playerTwo.getScore());
        }
        return result;
    }

    public String directAttack() throws Exception {
        state = State.ATTACK;
        String result = attackController.directAttack();
        selectedCard.reset();
        state = State.NONE;
        if (isGameFinished()) {
            finishGame();
            return String.format("%s won the game and the score is: %d-%d",
                    playerOneLp <= 0 ? playerTwo.getUsername() : playerOne.getUsername(), playerOne.getScore(), playerTwo.getScore());
        }
        return result;
    }

    public void activateEffect() throws Exception {
        state = State.ACTIVE_SPELL;
        if (selectedCard.getCard() instanceof SpellCard) {
            SpellCard card = (SpellCard) selectedCard.getCard();
            if (!(card.getProperty() == Property.FIELD) && selectedCard.getCardLocation() == CardLocation.HAND) {
                gameBoard.setSpell(getCurrentPlayerNumber(), card);
                gameBoard.setSpellFaceUp(selectedCard.getCard());
            }
        }
        selectedCard.getCard().doActions();
        state = State.NONE;
    }

    public void ritualSummon() {

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
        destroyedCardsForPlayerOne.clear();
        destroyedCardsForPlayerTwo.clear();
    }

    public void cheater(String cardName) throws Exception {
        Card card = Card.getCardByName(cardName);
        if (card == null) throw new RuntimeException();
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
        return playerOneLp <= 0 || playerTwoLp <= 0;
    }

    public void finishGame() throws NoActiveDeck, InvalidDeck, UsernameNotExists, InvalidRoundNumber, IOException {
        if (rounds == 1) {
            if (playerOneLp <= 0) {
                playerOne.increaseLoseRate();
                playerTwo.increaseWinRate(1000);
                playerOne.increaseMoney(100);
                playerTwo.increaseMoney(1000 + playerTwoLp);
            } else {
                playerTwo.increaseLoseRate();
                playerOne.increaseWinRate(1000);
                playerTwo.increaseMoney(100);
                playerOne.increaseMoney(1000 + playerOneLp);
            }
            DatabaseController.updatePlayer(playerOne);
            DatabaseController.updatePlayer(playerTwo);
            HandleRequestType.currentMenu = Menu.MAIN_MENU;
        } else {
            if (playerOneWin == 2 || playerTwoWin == 2) {
                if (playerOneLp <= 0) {
                    playerOne.increaseLoseRate();
                    playerTwo.increaseWinRate(3000);
                    playerOne.increaseMoney(300);
                    playerTwo.increaseMoney(3000 + 3 * playerTwoLp);
                } else {
                    playerTwo.increaseLoseRate();
                    playerOne.increaseWinRate(3000);
                    playerTwo.increaseMoney(300);
                    playerOne.increaseMoney(3000 + 3 * playerOneLp);
                }
                HandleRequestType.currentMenu = Menu.MAIN_MENU;
            } else {
                if (playerOneLp <= 0)
                    playerTwoWin++;
                else
                    playerOneWin++;
                DatabaseController.updatePlayer(playerOne);
                DatabaseController.updatePlayer(playerTwo);
                startGame(playerTwo.getUsername(), 2);
            }
        }
    }

    public void createChain() throws Exception {
        chain = new Chain();
        chainController = new ChainController(chain);
        chainController.run();
    }
}
//    backup
//    public void createChain() throws Exception {
//        //fixme : test speed of instant traps
//        ArrayList<Card> opponentSpellZone = getGameBoard().getCardInSpellZone(getOpponentPlayerNumber());
//        chain = new Chain();
//        Card counterTrap;
//        GameView.showConsole("Its now " + getCurrentPlayer().getNickname() + "turn!");
//        for (Card card : opponentSpellZone) {
//            if (card.canActivate()) {
//                chain.setNext(card);
//                counterTrap = gameBoard.getCounterTraps(getCurrentPlayerNumber()); //TODO : MORE THAN 1 CARD
//                if (counterTrap != null && !chain.doesExistInChain(counterTrap)) {
//                    GameView.showConsole("do you want to activate" + counterTrap.getName());
//                    if (GameView.getValidResponse()) {
//                        GameView.showConsole("Its now " + getOpponent().getNickname() + "turn!");
//                        chain.setNext(counterTrap);
//                    }
//                    GameView.showConsole("Its now " + getCurrentPlayer().getNickname() + "turn!");
//                    gameBoard.showBoard();
//                }
//            }
//        }
//
//    }


