package model.commands;

import model.Board;
import model.GamePhase;
import model.ZoneSlot;
import model.card.Card;
import view.menu.GameView;

import java.util.ArrayList;

public class MysticalSpaceTyphoon extends Command implements Activate{
    Board board;

    public MysticalSpaceTyphoon(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        ZoneSlot[] zoneSlots = board.getPlayerSpellZone(gameController.getOpponentPlayerNumber());
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            if (zoneSlots[i].getCard() != null)
                cards.add(zoneSlots[i].getCard());
        }
        System.out.println("----->"+cards.size());
        if (cards.size() == 0) {
            throw new Exception("No card in zone");
        }
        GameView.printListOfCard(cards);
        String cardNumber = GameView.prompt("Choose a card number from spell zone :");
        int number = Integer.parseInt(cardNumber);
        if (number > 5 || number < 0)
            throw new Exception("Invalid number");
        if (zoneSlots[number].getCard() == null)
            throw new Exception("Empty zone");
        board.sendCardFromSpellZoneToGraveyard(zoneSlots[number].getCard());
        board.sendCardFromSpellZoneToGraveyard(myCard);
    }

    @Override
    public boolean canActivate() throws Exception {
        board = gameController.getGameBoard();
        boolean canActivate = board.numberOfSpellAndTrapCards(gameController.getOpponentPlayerNumber()) != 0;
        boolean correctPhase = gameController.getGamePhase() == GamePhase.MAIN_PHASE1 ||
                gameController.getGamePhase() == GamePhase.MAIN_PHASE2 ||
                gameController.getGamePhase() == GamePhase.BATTLE_PHASE;
        return canActivate && correctPhase;
    }
}
