package model.commands;

import controller.GameController;
import model.State;
import model.card.Card;
import model.card.MonsterCard;

import java.util.ArrayList;
import java.util.List;

public class TerraTiger extends Command implements Activate {

    public TerraTiger(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {

        List<Card> hand = GameController.getInstance().getGameBoard().getCurrentPlayerHand();
        hand.remove(myCard);
        List<Card> appropriateCards = new ArrayList<>();
        for (Card card : hand) {
            if (!(card instanceof MonsterCard))
                continue;
            if (((MonsterCard) card).getLevel() > 4)
                continue;
            appropriateCards.add(card);
        }
        GameController.getInstance().setState(State.SPECIAL_SUMMON);
        GameController.getInstance().getGameBoard().summonCard((MonsterCard) appropriateCards.get(0), gameController.getCurrentPlayerNumber());
        GameController.getInstance().setState(State.NONE);

    }

    @Override
    public boolean canActivate() {
        return true;
    }
}
