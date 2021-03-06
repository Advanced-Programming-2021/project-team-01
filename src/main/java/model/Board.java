package model;

import controller.GameController;
import controller.exceptions.AlreadySummonedError;
import controller.exceptions.MonsterZoneFull;
import controller.exceptions.SpellZoneFullError;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.card.*;
import model.commands.HeraldOfCreation;
import model.commands.Scanner;
import model.networkLocators.BattleAction;
import view.ZoneLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private ZoneSlot[] playerOneMonsterZone;
    private ZoneSlot[] playerTwoMonsterZone;
    private ZoneSlot[] playerOneSpellZone;
    private ZoneSlot[] playerTwoSpellZone;
    private ObservableList<Card> playerOneGraveYard;
    private ObservableList<Card> playerTwoGraveYard;
    private ObservableList<Card> playerOneDrawZone;
    private ObservableList<Card> playerTwoDrawZone;
    private ZoneSlot playerOneFieldZone;
    private ZoneSlot playerTwoFieldZone;
    private ObservableList<Card> playerOneHand;
    private ObservableList<Card> playerTwoHand;


    {
        playerOneMonsterZone = new ZoneSlot[6];
        playerOneSpellZone = new ZoneSlot[6];
        playerTwoMonsterZone = new ZoneSlot[6];  //TODO: 1 start index
        playerTwoSpellZone = new ZoneSlot[6];
        playerOneGraveYard = FXCollections.observableArrayList();
        playerTwoGraveYard = FXCollections.observableArrayList();
        playerOneHand = FXCollections.observableArrayList();
        playerTwoHand = FXCollections.observableArrayList();
        playerOneFieldZone = new ZoneSlot();
        playerTwoFieldZone = new ZoneSlot();
        for (int i = 1; i < 6; i++) {
            playerOneMonsterZone[i] = new ZoneSlot();
            playerOneSpellZone[i] = new ZoneSlot();
            playerTwoMonsterZone[i] = new ZoneSlot();
            playerTwoSpellZone[i] = new ZoneSlot();
        }
    }

    public Board(Deck deck1, Deck deck2) {
        playerOneDrawZone = FXCollections.observableArrayList(deck1.getMainDeck());
        playerTwoDrawZone = FXCollections.observableArrayList(deck2.getMainDeck());
        for (int i = 0; i < 4; i++) {
            addCardFromDeckToHand(1);
        }
        for (int i = 0; i < 4; i++) {
            addCardFromDeckToHand(2);
        }
//        shuffleDecks(); TODO : narinid
    }

    public void addCardFromGraveYardToField(int player, Card card) throws MonsterZoneFull {
        if (player == 1) {
            if (numberOfMonsterCards(1) == 5) {
                throw new MonsterZoneFull();
            }
            for (int i = 1; i <= 5; i++) {
                if (playerOneMonsterZone[i].getCard() == null) {
                    playerOneMonsterZone[i].setCard(card);
                    break;
                }
            }
            return;
        }
        if (numberOfMonsterCards(2) == 5) {
            throw new MonsterZoneFull();
        }
        for (int i = 1; i <= 5; i++) {
            if (playerTwoMonsterZone[i].getCard() == null) {
                playerTwoMonsterZone[i].setCard(card);
                break;
            }
        }
    }

    public void addCardFromDeckToHand(int playerNumber) {
        if (playerNumber == 1) {
            Card card = playerOneDrawZone.get(0);
            playerOneDrawZone.remove(0);
            playerOneHand.add(card);
        } else {
            Card card = playerTwoDrawZone.get(0);
            playerTwoDrawZone.remove(0);
            playerTwoHand.add(card);
        }
    }

    public void addCardFromDeckToHand(int playerNumber, Card card) {
        if (playerNumber == 1) {
            playerOneDrawZone.remove(card);
            playerOneHand.add(card);
        } else {
            playerTwoDrawZone.remove(card);
            playerTwoHand.add(card);
        }
    }

    public void sendCardFromHandToGraveYard(int player, Card card) {
        if (player == 1) {
            playerOneGraveYard.add(card);
//            playerOneHand.remove(playerOneHand.stream().
//                    filter(card1 -> card1.getName().equals(card.getName())).findFirst().get());
            playerOneHand.remove(card);
            GameController.getInstance().getDestroyedCardsForPlayerOne().add(card);
        } else {
            playerTwoGraveYard.add(card);
//            playerTwoHand.remove(playerTwoHand.stream().
//                    filter(card1 -> card1.getName().equals(card.getName())).findFirst().get());
            playerTwoHand.remove(card);
            GameController.getInstance().getDestroyedCardsForPlayerTwo().add(card);
        }
    }

    public void sendCardFromSpellZoneToGraveyard(Card card) {
        int player = getOwnerOfCard(card);
        if (player == 1) {
            playerOneGraveYard.add(card);
            for (int i = 1; i < 6; i++) {
                if (playerOneSpellZone[i].getCard() == card)
                    playerOneSpellZone[i].setCard(null);
            }
            GameController.getInstance().getDestroyedCardsForPlayerOne().add(card);
        } else if (player == 2) {
            playerTwoGraveYard.add(card);
            for (int i = 1; i < 6; i++) {
                if (playerTwoSpellZone[i].getCard() == card)
                    playerTwoSpellZone[i].setCard(null);
            }
            GameController.getInstance().getDestroyedCardsForPlayerTwo().add(card);
        }
    }

    public void showBoard() {
        String opponentNickname = GameController.getOpponent().getNickname();
        String playerNickname = GameController.getCurrentPlayer().getNickname();
        int opponentLp = GameController.getInstance().getOpponentLp();
        int playerLp = GameController.getInstance().getCurrentLp();
        int playerHandSize;
        int opponentHandSize;
        int playerDrawZoneCards;
        int opponentDrawZoneCards;
        int playerGraveyardCards;
        int opponentGraveyardCards;
        if (GameController.getInstance().getCurrentPlayerNumber() == 1) {
            playerHandSize = playerOneHand.size();
            opponentHandSize = playerTwoHand.size();
            playerDrawZoneCards = playerOneDrawZone.size();
            opponentDrawZoneCards = playerTwoDrawZone.size();
            playerGraveyardCards = playerOneGraveYard.size();
            opponentGraveyardCards = playerTwoGraveYard.size();
        } else {
            playerHandSize = playerTwoHand.size();
            opponentHandSize = playerOneHand.size();
            playerDrawZoneCards = playerTwoDrawZone.size();
            opponentDrawZoneCards = playerOneDrawZone.size();
            playerGraveyardCards = playerTwoGraveYard.size();
            opponentGraveyardCards = playerOneGraveYard.size();
        }
        System.out.println(opponentNickname + " : " + opponentLp);
        for (int i = 0; i < opponentHandSize; i++) {
            System.out.print("\tc");
        }
        System.out.println("\n*" + opponentDrawZoneCards + "*");
        if (GameController.getInstance().getCurrentPlayerNumber() == 1) {
            showGameBoard(playerGraveyardCards, opponentGraveyardCards, playerTwoSpellZone, playerTwoMonsterZone,
                    playerTwoFieldZone, playerOneFieldZone, playerOneMonsterZone, playerOneSpellZone);
        } else {
            showGameBoard(playerGraveyardCards, opponentGraveyardCards, playerOneSpellZone, playerOneMonsterZone,
                    playerOneFieldZone, playerTwoFieldZone, playerTwoMonsterZone, playerTwoSpellZone);
        }
        System.out.println("\n\t\t\t\t\t\t*" + playerDrawZoneCards + "*");
        for (int i = 0; i < playerHandSize; i++) {
            System.out.print("\tc");
        }
        System.out.println("\n" + playerNickname + " : " + playerLp);
    }

    private void showGameBoard(int playerGraveyardCards, int opponentGraveyardCards, ZoneSlot[] playerOneSpellZone,
                               ZoneSlot[] playerOneMonsterZone, ZoneSlot playerOneFieldZone,
                               ZoneSlot playerTwoFieldZone, ZoneSlot[] playerTwoMonsterZone,
                               ZoneSlot[] playerTwoSpellZone) {
        showUserBoard(playerOneSpellZone, playerOneMonsterZone);
        System.out.println("\n" + opponentGraveyardCards + "\t\t\t\t\t\t" + playerOneFieldZone.toString());
        System.out.println("--------------------------");
        System.out.println(playerTwoFieldZone.toString() + "\t\t\t\t\t\t" + playerGraveyardCards);
        showUserBoard(playerTwoMonsterZone, playerTwoSpellZone);
    }

    private void showUserBoard(ZoneSlot[] playerOneSpellZone, ZoneSlot[] playerOneMonsterZone) {
        for (int i = 1; i < 6; i++) {
            System.out.print("\t" + playerOneSpellZone[i].toString());
        }
        System.out.println();
        for (int i = 1; i < 6; i++) {
            System.out.print("\t" + playerOneMonsterZone[i].toString());
        }
    }

    @Deprecated
    public Card getCard(String field, int player, int index) {
        switch (field) {
            case "monster":
                if (player == 1)
                    return playerOneMonsterZone[index].getCard();
                else if (player == 2)
                    return playerTwoMonsterZone[index].getCard();

                break;
            case "spell":
                if (player == 1)
                    return playerOneSpellZone[index].getCard();
                else if (player == 2)
                    return playerTwoSpellZone[index].getCard();

                break;
            case "hand":
                index--;
                if (player == 1)
                    return playerOneHand.get(index);
                else if (player == 2)
                    return playerTwoHand.get(index);
                break;
        }
        return null;
    }

    public Card getCard(CardLocation cardLocation, int index, int player) {
        switch (cardLocation) {
            case MONSTER:
                return player == 1 ? playerOneMonsterZone[index].getCard() : playerTwoMonsterZone[index].getCard();
            case SPELL:
                return player == 1 ? playerOneSpellZone[index].getCard() : playerTwoSpellZone[index].getCard();
            case HAND:
                return player == 1 ? playerOneHand.get(index) : playerTwoHand.get(index);
            case FIELD:
                return player == 1 ? playerOneFieldZone.getCard() : playerTwoFieldZone.getCard();
            case DECK:
                return player == 1 ? playerOneDrawZone.get(index) : playerTwoDrawZone.get(index);
            case GRAVEYARD:
                return player == 1 ? playerOneGraveYard.get(index) : playerTwoGraveYard.get(index);
        }
        return null;
    }

    public Card getCard(String field, int player) {
        if (field.equals("field")) {
            if (player == 1)
                return playerOneFieldZone.getCard();
            else if (player == 2)
                return playerTwoFieldZone.getCard();
        }
        return null;
    }

    public int getNumberOfCardsInHand(int player) {
        if (player == 1)
            return playerOneHand.size();
        else if (player == 2)
            return playerTwoHand.size();
        return -1;
    }

    public Card drawCard(int playerNum) {
        Card card;
        if (playerNum == 1) {
            card = playerOneDrawZone.get(0);
            playerOneHand.add(card);
            playerOneDrawZone.remove(0);
        } else {
            card = playerTwoDrawZone.get(0);
            playerTwoHand.add(card);
            playerTwoDrawZone.remove(0);
        }
        return card;
    }

    public void shuffleDecks() {
        Collections.shuffle(playerOneDrawZone);
        Collections.shuffle(playerTwoDrawZone);
    }

    public boolean isCardInMonsterZone(MonsterCard monsterCard) {
        for (int i = 1; i < 6; i++) {
            if (monsterCard == playerOneMonsterZone[i].getCard() ||
                    monsterCard == playerTwoMonsterZone[i].getCard())
                return true;
        }
        return false;
    }

    public boolean isCardInHand(Card card) {
        return playerOneHand.contains(card) || playerTwoHand.contains(card);
    }

    public void summonCard(MonsterCard monsterCard, int player) throws Exception {
        System.out.println(monsterCard.getName() + "*" + monsterCard.getCardType());
        if (monsterCard.getCardType() != CardType.RITUAL && GameController.getInstance().getState() != State.SPECIAL_SUMMON) {
            if (GameController.getInstance().isSummoned()) {
                throw new AlreadySummonedError();
            }
        }
        if (player == 1) {
            for (int i = 1; i <= 5; i++) {
                if (playerOneMonsterZone[i].getCard() == null) {
                    playerOneMonsterZone[i].setDefending(false);
                    playerOneMonsterZone[i].setHidden(false);
                    playerOneMonsterZone[i].setCard(monsterCard);
                    playerOneHand.remove(monsterCard);
                    return;
                }
            }
        } else {
            for (int i = 1; i <= 5; i++) {
                if (playerTwoMonsterZone[i].getCard() == null) {
                    playerTwoMonsterZone[i].setDefending(false);
                    playerTwoMonsterZone[i].setHidden(false);
                    playerTwoMonsterZone[i].setCard(monsterCard);
                    playerTwoHand.remove(monsterCard);
                    return;
                }
            }
        }
        throw new MonsterZoneFull();
    }

    public int numberOfMonsterCards(int player) {
        int number = 0;
        if (player == 1) {
            for (int i = 1; i < 6; i++) {
                if (playerOneMonsterZone[i].getCard() != null)
                    number++;
            }
        } else if (player == 2) {
            for (int i = 1; i < 6; i++) {
                if (playerTwoMonsterZone[i].getCard() != null)
                    number++;
            }
        }
        return number;
    }

    public int numberOfSpellAndTrapCards(int player) {
        int number = 0;
        if (player == 1) {
            for (int i = 1; i < 6; i++) {
                if (playerOneSpellZone[i].getCard() != null)
                    number++;
            }
        } else if (player == 2) {
            for (int i = 1; i < 6; i++) {
                if (playerTwoSpellZone[i].getCard() != null)
                    number++;
            }
        }
        return number;
    }

    public void addCardCheatToHand(Card card, int player) {
        if (player == 1) {
            playerOneHand.add(card);
            return;
        }
        playerTwoHand.add(card);
    }

    public Card getCardFromMonsterZone(int indexOfCard, int player) {
        if (player == 1)
            return playerOneMonsterZone[indexOfCard].getCard();
        else if (player == 2)
            return playerTwoMonsterZone[indexOfCard].getCard();
        return null;
    }

    public List<Card> getGraveyard(int player) {
        if (player == 1)
            return playerOneGraveYard;
        else
            return playerTwoGraveYard;
    }

    public void sendCardFromMonsterZoneToGraveyard(int indexOfCard, int player) {
        if (player == 1) {
            Card card = playerOneMonsterZone[indexOfCard].getCard();
            playerOneGraveYard.add(card);
            playerOneMonsterZone[indexOfCard].setCard(null);
            playerOneMonsterZone[indexOfCard].reset();
            GameController.getInstance().getDestroyedCardsForPlayerOne().add(card);
        } else if (player == 2) {
            Card card = playerTwoMonsterZone[indexOfCard].getCard();
            playerTwoGraveYard.add(card);
            playerTwoMonsterZone[indexOfCard].setCard(null);
            playerTwoMonsterZone[indexOfCard].reset();
            GameController.getInstance().getDestroyedCardsForPlayerTwo().add(card);
        }
    }

    public void sendCardFromMonsterZoneToGraveyard(Card card) {
        int player = getOwnerOfCard(card);
        int indexOfCard = 0;
        if (player == 1) {
            for (int i = 1; i < 6; i++) {
                if (playerOneMonsterZone[i].getCard() == card) {
                    indexOfCard = i;
                    break;
                }
            }
            playerOneGraveYard.add(card);
            playerOneMonsterZone[indexOfCard].setCard(null);
            playerOneMonsterZone[indexOfCard].reset();
            GameController.getInstance().getDestroyedCardsForPlayerOne().add(card);
        } else if (player == 2) {
            for (int i = 1; i < 6; i++) {
                if (playerTwoMonsterZone[i].getCard() == card) {
                    indexOfCard = i;
                    break;
                }
            }
            playerTwoGraveYard.add(card);
            playerTwoMonsterZone[indexOfCard].setCard(null);
            playerTwoMonsterZone[indexOfCard].reset();
            GameController.getInstance().getDestroyedCardsForPlayerTwo().add(card);
        }
    }

    public void setMonster(int playerNum, MonsterCard card) {
        if (playerNum == 1) {
            setMonsterInBoard(card, playerOneMonsterZone, playerOneHand);
        } else {
            setMonsterInBoard(card, playerTwoMonsterZone, playerTwoHand);
        }
    }

    private void setMonsterInBoard(MonsterCard card, ZoneSlot[] playerOneMonsterZone, List<Card> playerOneHand) {
        for (int i = 1; i < 6; i++) {
            if (playerOneMonsterZone[i].getCard() == null) {
                playerOneMonsterZone[i].setDefending(true);
                playerOneMonsterZone[i].setHidden(true);
                playerOneMonsterZone[i].setCard(card);
                playerOneHand.remove(card);
                break;
            }
        }
    }


    public void setSpell(int playerNum, SpellCard card) throws SpellZoneFullError {
        if (card.getProperty() == Property.FIELD) {
            if (playerNum == 1) {
                if (playerOneFieldZone.getCard() != null)
                    getGraveyard(1).add(playerOneFieldZone.getCard());
                playerOneFieldZone.setHidden(true);
                playerOneFieldZone.setCard(card);
                playerOneHand.remove(card);
            } else if (playerNum == 2) {
                if (playerTwoFieldZone.getCard() != null)
                    getGraveyard(2).add(playerTwoFieldZone.getCard());
                playerTwoFieldZone.setHidden(true);
                playerTwoFieldZone.setCard(card);
                playerTwoHand.remove(card);
            }
            return;
        }
        int counter = 0;
        if (playerNum == 1) {
            for (int i = 1; i < 6; i++) {
                if (playerOneSpellZone[i].getCard() != null)
                    counter++;
            }
            if (counter == 5)
                throw new SpellZoneFullError();
            for (int i = 1; i < 6; i++) {
                if (playerOneSpellZone[i].getCard() == null) {
                    playerOneSpellZone[i].setHidden(true);
                    playerOneSpellZone[i].setCard(card);
                    playerOneHand.remove(card);
                    break;
                }
            }
        } else {
            for (int i = 1; i < 6; i++) {
                if (playerTwoSpellZone[i].getCard() != null)
                    counter++;
            }
            if (counter == 5)
                throw new SpellZoneFullError();
            for (int i = 1; i < 6; i++) {
                if (playerTwoSpellZone[i].getCard() == null) {
                    playerTwoSpellZone[i].setHidden(true);
                    playerTwoSpellZone[i].setCard(card);
                    playerTwoHand.remove(card);
                    break;
                }
            }
        }
    }

    public void setTrap(int playerNum, TrapCard card) throws SpellZoneFullError {
        int counter = 0;
        if (playerNum == 1) {
            for (int i = 1; i < 6; i++) {
                if (playerOneSpellZone[i].getCard() != null)
                    counter++;
            }
            if (counter == 5)
                throw new SpellZoneFullError();
            for (int i = 1; i < 6; i++) {
                if (playerOneSpellZone[i].getCard() == null) {
                    playerOneSpellZone[i].setHidden(true);
                    playerOneSpellZone[i].setCard(card);
                    playerOneHand.remove(card);
                    break;
                }
            }
        } else {
            for (int i = 1; i < 6; i++) {
                if (playerTwoSpellZone[i].getCard() != null)
                    counter++;
            }
            if (counter == 5)
                throw new SpellZoneFullError();
            for (int i = 1; i < 6; i++) {
                if (playerTwoSpellZone[i].getCard() == null) {
                    playerTwoSpellZone[i].setHidden(true);
                    playerTwoSpellZone[i].setCard(card);
                    playerTwoHand.remove(card);
                    break;
                }
            }
        }
    }

    public void setSpellFaceUp(Card card) throws SpellZoneFullError {
        ZoneSlot zoneSlot = getZoneSlotByCard(card);
        if (zoneSlot == null) {
            if (card instanceof SpellCard)
                setSpell(getOwnerOfCard(card), (SpellCard) card);
            else if (card instanceof TrapCard)
                setTrap(getOwnerOfCard(card), (TrapCard) card);
        }
        zoneSlot = getZoneSlotByCard(card);
        zoneSlot.setHidden(false);
    }

    public ZoneSlot[] getCurrentPlayerMonsterCards() {
        if (GameController.getInstance().getCurrentPlayerNumber() == 1) {
            return playerOneMonsterZone;
        }
        return playerTwoMonsterZone;
    }

    public String showGraveyard(int player) {
        StringBuilder result = new StringBuilder();
        if (player == 1)
            getGraveyardList(result, playerOneGraveYard);
        else if (player == 2)
            getGraveyardList(result, playerTwoGraveYard);

        return result.toString();
    }

    private void getGraveyardList(StringBuilder result, List<Card> playerGraveYard) {
        if (playerGraveYard.size() == 0)
            result.append("graveyard empty");
        else {
            for (int i = 0; i < playerGraveYard.size(); i++) {
                result.append(i + 1).append(". ").append(playerGraveYard.get(i).getName()).append(':')
                        .append(playerGraveYard.get(i).getDescription()).append('\n');
            }
        }
    }

    public boolean isOpponentCardHidden(Card card, int playerNum) {
        if (playerNum == 1) {
            if (card instanceof MonsterCard) {
                for (int i = 1; i < 6; i++) {
                    if (playerOneMonsterZone[i].getCard() == card) {
                        return playerOneMonsterZone[i].isHidden();
                    }
                }
            } else {
                for (int i = 1; i < 6; i++) {
                    if (playerOneSpellZone[i].getCard() == card) {
                        return playerOneSpellZone[i].isHidden();
                    }
                }
            }
        } else {
            if (card instanceof MonsterCard) {
                for (int i = 1; i < 6; i++) {
                    if (playerTwoMonsterZone[i].getCard() == card) {
                        return playerTwoMonsterZone[i].isHidden();
                    }
                }
            } else {
                for (int i = 1; i < 6; i++) {
                    if (playerTwoSpellZone[i].getCard() == card) {
                        return playerTwoSpellZone[i].isHidden();
                    }
                }
            }
        }
        return true;
    }

    public ZoneSlot getZoneSlotByLocation(CardLocation cardLocation, int index, int player) {
        if (player == 1) {
            switch (cardLocation) {
                case FIELD:
                    return playerOneFieldZone;
                case SPELL:
                    return playerOneSpellZone[index];
                case MONSTER:
                    return playerOneMonsterZone[index];
            }
        } else if (player == 2) {
            switch (cardLocation) {
                case FIELD:
                    return playerTwoFieldZone;
                case SPELL:
                    return playerTwoSpellZone[index];
                case MONSTER:
                    return playerTwoMonsterZone[index];
            }
        }
        return null;
    }

    public boolean isMonsterZoneEmpty(int playerNum) {
        if (playerNum == 1) {
            for (int i = 1; i < 6; i++)
                if (playerOneMonsterZone[i].getCard() != null)
                    return false;
        } else {
            for (int i = 1; i < 6; i++)
                if (playerTwoMonsterZone[i].getCard() != null)
                    return false;
        }
        return true;
    }

    public void deactivateFieldSpell(int playerNumber) {
        if (playerNumber == 1) {
            Card card = playerOneFieldZone.getCard();
            if (card != null) {
                playerOneGraveYard.add(card);
                playerOneFieldZone.setCard(null);
            }
        } else {
            Card card = playerTwoFieldZone.getCard();
            if (card != null) {
                playerTwoGraveYard.add(card);
                playerTwoFieldZone.setCard(null);
            }
        }

    }

    public List<Card> getPlayerOneGraveYard() {
        return playerOneGraveYard;
    }

    public List<Card> getPlayerTwoGraveYard() {
        return playerTwoGraveYard;
    }

    public ZoneSlot[] getPlayerOneMonsterZone() {
        return playerOneMonsterZone;
    }

    public ZoneSlot[] getPlayerTwoMonsterZone() {
        return playerTwoMonsterZone;
    }

    public List<Card> getPlayerDrawZone(int player) {
        if (player == 1)
            return playerOneDrawZone;
        else
            return playerTwoDrawZone;
    }

    public ZoneSlot getZoneSlotByCard(Card card) {
        if (card == playerOneFieldZone.getCard())
            return playerOneFieldZone;
        if (card == playerTwoFieldZone.getCard())
            return playerTwoFieldZone;
        for (int i = 1; i < 6; i++) {
            if (card == playerOneSpellZone[i].getCard())
                return playerOneSpellZone[i];
            if (card == playerTwoSpellZone[i].getCard())
                return playerTwoSpellZone[i];
            if (card == playerOneMonsterZone[i].getCard())
                return playerOneMonsterZone[i];
            if (card == playerTwoMonsterZone[i].getCard())
                return playerTwoMonsterZone[i];
        }
        return null;
    }

    public ZoneSlot[] getPlayerSpellZone(int playerNum) {
        return playerNum == 1 ? playerOneSpellZone : playerTwoSpellZone;
    }

    public ZoneSlot getPlayerFieldZone(int player) {
        if (player == 1) return playerOneFieldZone;
        if (player == 2) return playerTwoFieldZone;
        return null;
    }

    public int getOwnerOfCard(Card card) {
        if (card == playerOneFieldZone.getCard())
            return 1;
        if (card == playerTwoFieldZone.getCard())
            return 2;
        if (playerOneHand.contains(card))
            return 1;
        if (playerTwoHand.contains(card))
            return 2;
        if (playerOneDrawZone.contains(card))
            return 1;
        if (playerTwoDrawZone.contains(card))
            return 2;
        if (playerOneGraveYard.contains(card))
            return 1;
        if (playerTwoGraveYard.contains(card))
            return 2;
        for (int i = 1; i < 6; i++) {
            if (card == playerOneSpellZone[i].getCard())
                return 1;
            if (card == playerTwoSpellZone[i].getCard())
                return 2;
            if (card == playerOneMonsterZone[i].getCard())
                return 1;
            if (card == playerTwoMonsterZone[i].getCard())
                return 2;
        }

        return 0;
    }

    public CardLocation getCardLocation(Card card) {
        if (card == playerOneFieldZone.getCard())
            return CardLocation.FIELD;
        if (card == playerTwoFieldZone.getCard())
            return CardLocation.FIELD;
        if (playerOneHand.contains(card))
            return CardLocation.HAND;
        if (playerTwoHand.contains(card))
            return CardLocation.HAND;
        if(playerOneGraveYard.contains(card) || playerTwoGraveYard.contains(card))
            return CardLocation.GRAVEYARD;
        if (playerOneDrawZone.contains(card) || playerTwoDrawZone.contains(card))
            return CardLocation.DECK;
        for (int i = 1; i < 6; i++) {
            if (card == playerOneSpellZone[i].getCard())
                return CardLocation.SPELL;
            if (card == playerTwoSpellZone[i].getCard())
                return CardLocation.SPELL;
            if (card == playerOneMonsterZone[i].getCard())
                return CardLocation.MONSTER;
            if (card == playerTwoMonsterZone[i].getCard())
                return CardLocation.MONSTER;
        }
        return null;
    }

    public int getIndexOfCard(SelectedCard selectedCard) {
        Card card = selectedCard.getCard();
        switch (selectedCard.getCardLocation()) {
            case HAND:
                if (getOwnerOfCard(card) == 1) {
                    if (playerOneHand.contains(card))
                        return playerOneHand.indexOf(card);
                } else {
                    if (playerTwoHand.contains(card))
                        return playerTwoHand.indexOf(card);
                }
                break;
            case MONSTER:
                if (getOwnerOfCard(card) == 1) {
                    for (int i = 1; i < 7; i++)
                        if (playerOneMonsterZone[i].getCard() == card)
                            return i;
                } else {
                    for (int i = 1; i < 7; i++)
                        if (playerTwoMonsterZone[i].getCard() == card)
                            return i;
                }
                break;
            case SPELL:
                if (getOwnerOfCard(card) == 1) {
                    for (int i = 1; i < 7; i++)
                        if (playerOneSpellZone[i].getCard() == card)
                            return i;
                } else {
                    for (int i = 1; i < 7; i++)
                        if (playerTwoSpellZone[i].getCard() == card)
                            return i;
                }
                break;
        }
        return -1;
    }

    public int getIndexOfCard(Card card, CardLocation location) {
        switch (location) {
            case HAND:
                if (getOwnerOfCard(card) == 1) {
                    if (playerOneHand.contains(card))
                        return playerOneHand.indexOf(card);
                } else {
                    if (playerTwoHand.contains(card))
                        return playerTwoHand.indexOf(card);
                }
                break;
            case MONSTER:
                if (getOwnerOfCard(card) == 1) {
                    for (int i = 1; i < 7; i++)
                        if (playerOneMonsterZone[i].getCard() == card)
                            return i;
                } else {
                    for (int i = 1; i < 7; i++)
                        if (playerTwoMonsterZone[i].getCard() == card)
                            return i;
                }
                break;
            case SPELL:
                if (getOwnerOfCard(card) == 1) {
                    for (int i = 1; i < 7; i++)
                        if (playerOneSpellZone[i].getCard() == card)
                            return i;
                } else {
                    for (int i = 1; i < 7; i++)
                        if (playerTwoSpellZone[i].getCard() == card)
                            return i;
                }
                break;
        }
        return -1;
    }

    public int getIndexOfCard(Card card) {
        CardLocation location = getCardLocation(card);
        switch (location) {
            case HAND:
                if (getOwnerOfCard(card) == 1) {
                    if (playerOneHand.contains(card))
                        return playerOneHand.indexOf(card);
                } else {
                    if (playerTwoHand.contains(card))
                        return playerTwoHand.indexOf(card);
                }
                break;
            case MONSTER:
                if (getOwnerOfCard(card) == 1) {
                    for (int i = 1; i < 7; i++)
                        if (playerOneMonsterZone[i].getCard() == card)
                            return i;
                } else {
                    for (int i = 1; i < 7; i++)
                        if (playerTwoMonsterZone[i].getCard() == card)
                            return i;
                }
                break;
            case SPELL:
                if (getOwnerOfCard(card) == 1) {
                    for (int i = 1; i < 7; i++)
                        if (playerOneSpellZone[i].getCard() == card)
                            return i;
                } else {
                    for (int i = 1; i < 7; i++)
                        if (playerTwoSpellZone[i].getCard() == card)
                            return i;
                }
                break;
            case GRAVEYARD:
                if (getOwnerOfCard(card) == 1) {
                    return playerOneGraveYard.indexOf(card);
                } else {
                    return playerTwoGraveYard.indexOf(card);
                }
            case DECK:
                if (getOwnerOfCard(card) == 1) {
                    return playerOneDrawZone.indexOf(card);
                } else {
                    return playerTwoDrawZone.indexOf(card);
                }

        }
        return -1;
    }

    public Card getCardByBattleAction(BattleAction battleAction) {
        CardLocation location = battleAction.getCardLocation();
        int index = battleAction.getIndex();
        int player = battleAction.getPlayerNumber();
        return getCard(location, index, player);
    }

    public void setCardFromHandToFieldZone(int player, Card card) {
        if (player == 1) {
            if (playerOneFieldZone.getCard() != null)
                playerOneGraveYard.add(playerOneFieldZone.getCard());
            playerOneFieldZone.setCard(card);
            playerOneHand.remove(card);
        } else if (player == 2) {
            if (playerTwoFieldZone.getCard() != null)
                playerTwoGraveYard.add(playerTwoFieldZone.getCard());
            playerTwoFieldZone.setCard(card);
            playerTwoHand.remove(card);
        }
    }

    public ArrayList<Card> faceUpSpellAndTraps() {
        ArrayList<Card> spellAndTraps = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            if (playerOneSpellZone[i].getCard() != null && (!playerOneSpellZone[i].isHidden()))
                spellAndTraps.add(playerOneSpellZone[i].getCard());
            if (playerTwoSpellZone[i].getCard() != null && (!playerTwoSpellZone[i].isHidden()))
                spellAndTraps.add(playerTwoSpellZone[i].getCard());
        }
        for (int i = 1; i < 6; i++) {
            Card card;
            card = playerOneMonsterZone[i].getCard();
            if (card != null)
                if (card.getCommands() != null)
                    if (card.getCommands().size() != 0)
                        if (card.getCommands().get(0) instanceof Scanner ||
                                card.getCommands().get(0) instanceof HeraldOfCreation)
                            spellAndTraps.add(playerOneMonsterZone[i].getCard());
            card = playerTwoMonsterZone[i].getCard();
            if (card != null)
                if (card.getCommands() != null)
                    if (card.getCommands().size() != 0)
                        if (card.getCommands().get(0) instanceof Scanner ||
                                card.getCommands().get(0) instanceof HeraldOfCreation)
                            spellAndTraps.add(playerTwoMonsterZone[i].getCard());
        }
        return spellAndTraps;
    }

    public ArrayList<Card> getCardInMonsterZone(int player) {
        ArrayList<Card> monsterCards = new ArrayList<>();
        if (player == 1) {
            for (int i = 1; i < 6; i++) {
                if (playerOneMonsterZone[i].getCard() != null)
                    monsterCards.add(playerOneMonsterZone[i].getCard());
            }
        } else if (player == 2) {
            for (int i = 1; i < 6; i++) {
                if (playerTwoMonsterZone[i].getCard() != null)
                    monsterCards.add(playerTwoMonsterZone[i].getCard());
            }
        }
        return monsterCards;
    }

    public ArrayList<Card> getCardInSpellZone(int player) {
        ArrayList<Card> arrayList = new ArrayList<>();
        if (player == 1) {
            for (int i = 1; i < 6; i++) {
                if (playerOneSpellZone[i].getCard() != null)
                    arrayList.add(playerOneSpellZone[i].getCard());
            }
        } else if (player == 2) {
            for (int i = 1; i < 6; i++) {
                if (playerTwoSpellZone[i].getCard() != null)
                    arrayList.add(playerTwoSpellZone[i].getCard());
            }
        }
        return arrayList;
    }

    public void sendCardFromMonsterZoneToAnother(Card card, int fromPlayer, int toPlayer) {
        if (fromPlayer == 1) {
            for (int i = 1; i < 6; i++) {
                if (playerOneMonsterZone[i].getCard() == card) {
                    playerOneMonsterZone[i].setCard(null);
                    for (int j = 1; j < 6; j++) {
                        if (playerTwoMonsterZone[j].getCard() == null) {
                            playerTwoMonsterZone[j].setDefending(false);
                            playerTwoMonsterZone[j].setHidden(false);
                            playerTwoMonsterZone[j].setCard(card);
                            return;
                        }
                    }
                }
            }
        } else if (fromPlayer == 2) {
            for (int i = 1; i < 6; i++) {
                if (playerTwoMonsterZone[i].getCard() == card) {
                    playerTwoMonsterZone[i].setCard(null);
                    for (int j = 1; j < 6; j++) {
                        if (playerOneMonsterZone[j].getCard() == null) {
                            playerOneMonsterZone[j].setDefending(false);
                            playerOneMonsterZone[j].setHidden(false);
                            playerOneMonsterZone[j].setCard(card);
                            return;
                        }
                    }
                }
            }
        }
    }


    public Card getCounterTraps(int playerNumber) throws Exception {
        if (playerNumber == 2) {
            for (int i = 1; i < 6; i++) {
                Card card = playerOneSpellZone[i].getCard();
                if (card instanceof TrapCard && !GameController.getInstance().getEffectController().isMirageDragoon() &&
                        card.canActivate()) {
                    return card;
                } else if (card instanceof SpellCard) {
                    Property property = ((SpellCard) card).getProperty();
                    if (property == Property.QUICK_PLAY || property == Property.COUNTER) {
                        if (card.canActivate()) {
                            return card;
                        }
                    }
                }
            }
            for (Card card : playerOneHand) {
                if (card instanceof SpellCard && ((SpellCard) card).getProperty() == Property.QUICK_PLAY) {
                    if (card.canActivate()) {
                        return card;
                    }
                }
            }
        } else {
            for (int i = 1; i < 6; i++) {
                Card card = playerTwoSpellZone[i].getCard();
                if (card instanceof TrapCard && !GameController.getInstance().getEffectController().isMirageDragoon()
                        && card.canActivate()) {
                    return card; //fixme: speed
                } else if (card instanceof SpellCard) {
                    Property property = ((SpellCard) card).getProperty();
                    if (property == Property.QUICK_PLAY || property == Property.COUNTER) {
                        if (card.canActivate()) {
                            return card;
                        }
                    }
                }
            }
            for (Card card : playerTwoHand) {
                if (card instanceof SpellCard && ((SpellCard) card).getProperty() == Property.QUICK_PLAY) {
                    if (card.canActivate()) {
                        return card;
                    }
                }
            }
        }
        return null;
    }

    public List<Card> getPlayerOneHand() {
        return playerOneHand;
    }

    public List<Card> getPlayerTwoHand() {
        return playerTwoHand;
    }

    public List<Card> getCurrentPlayerHand() {
        if (GameController.getInstance().getCurrentPlayerNumber() == 1) {
            return playerOneHand;
        }
        return playerTwoHand;
    }

    public List<Card> getPlayerHand(int player) {
        if (player == 1)
            return playerOneHand;
        else
            return playerTwoHand;
    }

    public ZoneSlot[] getPlayerMonsterZone(int playerNum) {
        if (playerNum == 1) {
            return playerOneMonsterZone;
        }
        return playerTwoMonsterZone;
    }

    public ZoneLocation getZoneLocation(ZoneSlot zoneSlot) {
        for (int i = 1; i < 6; i++) {
            if (playerOneMonsterZone[i] == zoneSlot) {
                return new ZoneLocation(1, i, CardLocation.MONSTER);
            }
        }
        for (int i = 1; i < 6; i++) {
            if (playerTwoMonsterZone[i] == zoneSlot) {
                return new ZoneLocation(2, i, CardLocation.MONSTER);
            }
        }
        for (int i = 1; i < 6; i++) {
            if (playerOneSpellZone[i] == zoneSlot) {
                return new ZoneLocation(1, i, CardLocation.SPELL);
            }
        }
        for (int i = 1; i < 6; i++) {
            if (playerTwoSpellZone[i] == zoneSlot) {
                return new ZoneLocation(2, i, CardLocation.SPELL);
            }
        }
        if (playerOneFieldZone == zoneSlot) {
            return new ZoneLocation(1, 1, CardLocation.FIELD);
        }
        if (playerTwoFieldZone == zoneSlot) {
            return new ZoneLocation(2, 1, CardLocation.FIELD);
        }
        return null;
    }

    public boolean isCardInMonsterZone(Card card) {
        int player = getOwnerOfCard(card);
        if (player == 1) {
            for (int i = 1; i < 6; i++) {
                if (playerOneMonsterZone[i].getCard() == card)
                    return true;
            }
        } else if (player == 2) {
            for (int i = 1; i < 6; i++) {
                if (playerOneMonsterZone[i].getCard() == card)
                    return true;
            }
        }
        return false;
    }

    public void swap() {
        ZoneSlot[] playerOneMonsterZone = this.playerOneMonsterZone;
        ZoneSlot[] playerOneSpellZone = this.playerOneSpellZone;
        ObservableList<Card> playerOneGraveYard = this.playerOneGraveYard;
        ObservableList<Card> playerOneDrawZone = this.playerOneDrawZone;
        ZoneSlot playerOneFieldZone = this.playerOneFieldZone;
        ObservableList<Card> playerOneHand = this.playerOneHand;
        this.playerOneHand = playerTwoHand;
        playerTwoHand = playerOneHand;
        this.playerOneFieldZone = playerTwoFieldZone;
        playerTwoFieldZone = playerOneFieldZone;
        this.playerOneDrawZone = playerTwoDrawZone;
        playerTwoDrawZone = playerOneDrawZone;
        this.playerOneGraveYard = playerTwoGraveYard;
        playerTwoGraveYard = playerOneGraveYard;
        this.playerOneSpellZone = playerTwoSpellZone;
        playerTwoSpellZone = playerOneSpellZone;
        this.playerOneMonsterZone = playerTwoMonsterZone;
        playerTwoMonsterZone = playerOneMonsterZone;
    }

}
