package model;

import controller.GameController;
import controller.exceptions.AlreadySummonedError;
import controller.exceptions.MonsterZoneFull;
import controller.exceptions.SpellZoneFullError;
import model.card.*;

import java.util.ArrayList;
import java.util.Collections;

public class Board {
    private ZoneSlot[] playerOneMonsterZone;
    private ZoneSlot[] playerTwoMonsterZone;
    private ZoneSlot[] playerOneSpellZone;
    private ZoneSlot[] playerTwoSpellZone;
    private ArrayList<Card> playerOneGraveYard;
    private ArrayList<Card> playerTwoGraveYard;
    private ArrayList<Card> playerOneBanishedZone;
    private ArrayList<Card> playerTwoBanishedZone;
    private ArrayList<Card> playerOneDrawZone;
    private ArrayList<Card> playerTwoDrawZone;
    private ZoneSlot playerOneFieldZone;
    private ZoneSlot playerTwoFieldZone;
    private ArrayList<Card> playerOneHand;
    private ArrayList<Card> playerTwoHand;

    {
        playerOneMonsterZone = new ZoneSlot[6];
        playerOneBanishedZone = new ArrayList<>();
        playerOneSpellZone = new ZoneSlot[6];
        playerTwoMonsterZone = new ZoneSlot[6];
        playerTwoBanishedZone = new ArrayList<>();  //TODO: 1 start index
        playerTwoSpellZone = new ZoneSlot[6];
        playerOneGraveYard = new ArrayList<>();
        playerTwoGraveYard = new ArrayList<>();
        playerOneHand = new ArrayList<>();
        playerTwoHand = new ArrayList<>();
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
        playerOneDrawZone = deck1.getMainDeck();
        playerTwoDrawZone = deck2.getMainDeck();
        for (int i = 0; i < 4; i++) {
            addCardFromDeckToHand(1);
        }
        for (int i = 0; i < 4; i++) {
            addCardFromDeckToHand(2);
        }
        shuffleDecks();
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

    public Card getCard(String field, int player, int number) {
        switch (field) {
            case "monster":
                if (player == 1)
                    return playerOneMonsterZone[number].getCard();
                else if (player == 2)
                    return playerTwoMonsterZone[number].getCard();

                break;
            case "spell":
                if (player == 1)
                    return playerOneSpellZone[number].getCard();
                else if (player == 2)
                    return playerTwoSpellZone[number].getCard();

                break;
            case "hand":
                number--;
                if (player == 1)
                    return playerOneHand.get(number);
                else if (player == 2)
                    return playerTwoHand.get(number);
                break;
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

    public boolean isCardInHand(Card selectedCard, int player) {
        if (player == 1) {
            for (Card card : playerOneHand) {
                if (card == selectedCard)
                    return true;
            }
        } else if (player == 2) {
            for (Card card : playerTwoHand) {
                if (card == selectedCard)
                    return true;
            }
        }
        return false;
    }

    public void summonCard(MonsterCard monsterCard, int player) throws MonsterZoneFull, AlreadySummonedError {
        if (GameController.getInstance().isSummoned()) {
            throw new AlreadySummonedError();
        }
        if (player == 1) {
            for (int i = 1; i <= 5; i++) {
                if (playerOneMonsterZone[i].getCard() == null) {
                    playerOneMonsterZone[i].setCard(monsterCard);
                    playerOneHand.remove(monsterCard);
                    return;
                }
            }
        } else {
            for (int i = 1; i <= 5; i++) {
                if (playerTwoMonsterZone[i].getCard() == null) {
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

    public Card getCardFromMonsterZone(int indexOfCard, int player) {
        if (player == 1)
            return playerOneMonsterZone[indexOfCard].getCard();
        else if (player == 2)
            return playerTwoMonsterZone[indexOfCard].getCard();
        return null;
    }

    public void sendCardFromMonsterZoneToGraveyard(int indexOfCard,int player){
        if (player == 1) {
            Card card = playerOneMonsterZone[indexOfCard].getCard();
            playerOneGraveYard.add(card);
            playerOneMonsterZone[indexOfCard].setCard(null);
        } else if (player == 2) {
            Card card = playerOneMonsterZone[indexOfCard].getCard();
            playerOneGraveYard.add(card);
            playerOneMonsterZone[indexOfCard].setCard(null);
        }
    }

    public void setMonster(int playerNum, MonsterCard card) {
        if (playerNum == 1) {
            setMonsterInBoard(card, playerOneMonsterZone, playerOneHand);
        } else {
            setMonsterInBoard(card, playerTwoMonsterZone, playerTwoHand);
        }
    }

    private void setMonsterInBoard(MonsterCard card, ZoneSlot[] playerOneMonsterZone, ArrayList<Card> playerOneHand) {
        for (int i = 1; i < 6; i++) {
            if (playerOneMonsterZone[i].getCard() == null) {
                playerOneMonsterZone[i].setCard(card);
                playerOneMonsterZone[i].setDefending(true);
                playerOneMonsterZone[i].setHidden(true);
                playerOneHand.remove(card);
                break;
            }
        }
    }


    public void setSpell(int playerNum, SpellCard card) throws SpellZoneFullError {
        if (card.getProperty() == Property.FIELD) {
            if (playerNum == 1 && playerOneFieldZone.getCard() == null) {
                playerOneFieldZone.setCard(card);
                playerOneFieldZone.setHidden(true);
                playerOneHand.remove(card);
            } else if (playerTwoFieldZone.getCard() == null) {
                playerTwoFieldZone.setCard(card);
                playerTwoFieldZone.setHidden(true);
                playerTwoHand.remove(card);
            } else
                throw new SpellZoneFullError();
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
                    playerOneSpellZone[i].setCard(card);
                    playerOneSpellZone[i].setHidden(true);
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
                    playerTwoSpellZone[i].setCard(card);
                    playerTwoSpellZone[i].setHidden(true);
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
                    playerOneSpellZone[i].setCard(card);
                    playerOneSpellZone[i].setHidden(true);
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
                    playerTwoSpellZone[i].setCard(card);
                    playerTwoSpellZone[i].setHidden(true);
                    playerTwoHand.remove(card);
                    break;
                }
            }
        }
    }

    public String showGraveyard(int player){
        StringBuilder result = new StringBuilder();
        if (player==1){
            getGraveyardList(result, playerOneGraveYard);
        }else if (player ==2){
            getGraveyardList(result, playerTwoGraveYard);
        }
        return result.toString();
    }

    private void getGraveyardList(StringBuilder result, ArrayList<Card> playerGraveYard) {
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
            if (card instanceof MonsterCard)
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
}
