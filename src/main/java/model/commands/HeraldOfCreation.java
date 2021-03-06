package model.commands;

import model.Board;
import model.GamePhase;
import model.State;
import model.card.Card;
import model.card.MonsterCard;
import console.menu.GameView;

import java.util.ArrayList;

public class HeraldOfCreation extends Command implements Activate {
    Board board;
    int player;
    MonsterCard shouldSummon;

    public HeraldOfCreation(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        player = board.getOwnerOfCard(myCard);
    }

    @Override
    public void runContinuous() throws Exception {
        if (gameController.getGamePhase() == GamePhase.STANDBY_PHASE) {
            if (board.getPlayerHand(player).isEmpty() || board.numberOfMonsterCards(player) == 5)
                return;
            ArrayList<Card> highLevel = new ArrayList<>();
            for (Card card : board.getGraveyard(player)) {
                if (card instanceof MonsterCard && ((MonsterCard) card).getLevel() >= 7)
                    highLevel.add(card);
            }
            if (highLevel.isEmpty())
                return;
            shouldSummon = (MonsterCard) view.GameView.getNeededCards(highLevel, 1).get(0);
            GameView.printListOfCard(highLevel);
            State temp = gameController.getState();
            gameController.setState(State.SPECIAL_SUMMON);
            board.summonCard(shouldSummon, player);
            gameController.setState(temp);
        }
    }
}
