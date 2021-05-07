package model.commands;

import controller.exceptions.NoFieldSpellInDeck;
import model.Board;
import model.card.Card;
import model.card.Property;
import model.card.SpellCard;
import view.menu.GameView;

import java.util.ArrayList;

public class Terrafoming extends Command implements Activate{
    Board board;
    @Override
    public void run() throws NoFieldSpellInDeck {
        board = gameController.getGameBoard();
        ArrayList<Card> cardsInDeck = board.getPlayerDrawZone(gameController.getCurrentPlayerNumber());
        ArrayList<Card> fieldSpells = new ArrayList<>();
        for (Card card : cardsInDeck) {
            if (!(card instanceof SpellCard))
                continue;
            if (((SpellCard) card).getProperty() == Property.FIELD)
                fieldSpells.add(card);
        }
        if (fieldSpells.size() == 0)
            throw new NoFieldSpellInDeck();
        GameView.printListOfCard(fieldSpells);
        String indexString = GameView.prompt("Enter a number :");
        int index;
        try {
            index = Integer.parseInt(indexString);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
        Card card = fieldSpells.get(index);
        board.addCardFromDeckToHand(gameController.getCurrentPlayerNumber(), card);
        board.sendCardFromSpellZoneToGraveyard(gameController.getCurrentPlayerNumber(), gameController.getSelectedCard().getCard());
    }
}
