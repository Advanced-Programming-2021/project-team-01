package model.commands;

import controller.exceptions.NoFieldSpellInDeck;
import model.Board;
import model.GamePhase;
import model.card.Card;
import model.card.Property;
import model.card.SpellCard;
import console.menu.GameView;

import java.util.ArrayList;

public class Terrafoming extends Command implements Activate {
    Board board;

    public Terrafoming(Card card) {
        super(card);
    }

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
        int index = GameView.getValidNumber(0, fieldSpells.size() - 1);
        Card card = fieldSpells.get(index);
        board.addCardFromDeckToHand(gameController.getCurrentPlayerNumber(), card);
        board.sendCardFromSpellZoneToGraveyard(myCard);
    }

    @Override
    public boolean canActivate() throws Exception {
        board = gameController.getGameBoard();
        ArrayList<Card> cardsInDeck = board.getPlayerDrawZone(gameController.getCurrentPlayerNumber());
        ArrayList<Card> fieldSpells = new ArrayList<>();
        for (Card card : cardsInDeck) {
            if (!(card instanceof SpellCard))
                continue;
            if (((SpellCard) card).getProperty() == Property.FIELD)
                fieldSpells.add(card);
        }
        boolean canActivate = fieldSpells.size() != 0;
        boolean correctPhase = gameController.getGamePhase() == GamePhase.MAIN_PHASE1 ||
                gameController.getGamePhase() == GamePhase.MAIN_PHASE2 ||
                gameController.getGamePhase() == GamePhase.BATTLE_PHASE;
        return correctPhase && canActivate;//TODO : phase ha doroste ya na
    }
}
