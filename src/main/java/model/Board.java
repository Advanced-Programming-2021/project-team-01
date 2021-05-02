package model;

import controller.GameController;
import model.card.Card;

import java.util.ArrayList;

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
    private Player playerOne;
    private Player playerTwo;

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
        for (int i = 1; i < 6; i++) {
            playerOneMonsterZone[i] = new ZoneSlot();
            playerOneSpellZone[i] = new ZoneSlot();
            playerTwoMonsterZone[i] = new ZoneSlot();
            playerTwoSpellZone[i] = new ZoneSlot();
        }
    }

    public Board(Deck deck1, Deck deck2, Player player1, Player player2) {
        playerOneDrawZone = deck1.getMainDeck();
        playerTwoDrawZone = deck2.getMainDeck();
        playerOne = player1;
        playerTwo = player2;
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
            playerGraveyardCards = playerOneDrawZone.size();
            opponentGraveyardCards = playerTwoGraveYard.size();
        }

    }

    public Card getCard(String field, int number, int player) {
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

}
