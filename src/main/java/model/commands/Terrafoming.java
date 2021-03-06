package model.commands;

import controller.exceptions.NoFieldSpellInDeck;
import model.Board;
import model.GamePhase;
import model.card.Card;
import model.card.Property;
import model.card.SpellCard;
import view.GameView;

import java.util.ArrayList;
import java.util.List;

public class Terrafoming extends Command implements Activate {
    Board board;

    public Terrafoming(Card card) {
        super(card);
    }

    @Override
    public void run() throws NoFieldSpellInDeck {
        board = gameController.getGameBoard();
        List<Card> cardsInDeck = board.getPlayerDrawZone(gameController.getCurrentPlayerNumber());
        ArrayList<Card> fieldSpells = new ArrayList<>();
        for (Card card : cardsInDeck) {
            if (!(card instanceof SpellCard))
                continue;
            if (((SpellCard) card).getProperty() == Property.FIELD)
                fieldSpells.add(card);
        }
        Card card = GameView.getNeededCards(fieldSpells,1).get(0);
        board.addCardFromDeckToHand(gameController.getCurrentPlayerNumber(), card);
        board.sendCardFromSpellZoneToGraveyard(myCard);
    }

    @Override
    public boolean canActivate() throws Exception {
        board = gameController.getGameBoard();
        List<Card> cardsInDeck = board.getPlayerDrawZone(gameController.getCurrentPlayerNumber());
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
        return correctPhase && canActivate;
    }
}
