package model.commands;

import controller.exceptions.InvalidCommandException;
import controller.exceptions.MonsterZoneFull;
import model.Board;
import model.ZoneSlot;
import model.card.Card;
import view.menu.GameView;

import java.util.ArrayList;

public class TwinTwisters extends Command implements Activate{
    Board board;

    public TwinTwisters(Card card) {
        super(card);
    }

    @Override
    public void run() throws InvalidCommandException, MonsterZoneFull, Exception {
        board = gameController.getGameBoard();
        String discardCard = GameView.prompt("Choose a card number from your hand to discard : ");
        if (Integer.parseInt(discardCard) < 1 ||
                Integer.parseInt(discardCard) > board.getNumberOfCardsInHand(gameController.getCurrentPlayerNumber()))
            throw new Exception("Invalid card number");
        ZoneSlot[] zoneSlots = board.getPlayerSpellZone(gameController.getOpponentPlayerNumber());
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            if (zoneSlots[i].getCard() != null)
                cards.add(zoneSlots[i].getCard());
        }
        GameView.printListOfCard(cards);
        String firstCardNum = GameView.prompt("Choose one valid spell or trap card : ");
        boolean condition = !(firstCardNum.equals("1") || firstCardNum.equals("2") ||
                firstCardNum.equals("3") || firstCardNum.equals("4") || firstCardNum.equals("5"));
        if (condition)
            throw new Exception("Not valid number");
        if (zoneSlots[Integer.parseInt(firstCardNum)].getCard() == null)
            throw new Exception("Empty zone");
        String response = GameView.prompt("Do you want to choose the second card? (yes/no)");
        if (!response.equals("no") && !response.equals("yes"))
            throw new Exception("Invalid answer");
        if (response.equals("yes")) {
            cards.remove(Integer.parseInt(firstCardNum));
            GameView.printListOfCard(cards);
            String secondCardNum = GameView.prompt("Choose second card : ");
            if (!(secondCardNum.equals("1") || secondCardNum.equals("2") ||
                    secondCardNum.equals("3") || secondCardNum.equals("4") || secondCardNum.equals("5")))
                throw new Exception("Not valid number");
            if (zoneSlots[Integer.parseInt(firstCardNum)].getCard() == null)
                throw new Exception("Empty zone");
            board.sendCardFromSpellZoneToGraveyard(zoneSlots[Integer.parseInt(firstCardNum)].getCard());
            board.sendCardFromSpellZoneToGraveyard(zoneSlots[Integer.parseInt(secondCardNum)].getCard());
        } else {
            board.sendCardFromSpellZoneToGraveyard(zoneSlots[Integer.parseInt(firstCardNum)].getCard());
        }
        board.sendCardFromSpellZoneToGraveyard(myCard);
        board.sendCardFromHandToGraveYard(gameController.getCurrentPlayerNumber(),
                board.getCard("hand", gameController.getCurrentPlayerNumber(), Integer.parseInt(discardCard)));
    }

    @Override
    public boolean canActivate() throws Exception {
        return true;
    }
}
