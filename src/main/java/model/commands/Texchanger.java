package model.commands;

import model.Board;
import model.State;
import model.card.Card;
import model.card.CardType;
import model.card.MonsterCard;
import model.card.MonsterType;
import view.GameView;

import java.util.ArrayList;

public class Texchanger extends Command implements Activate {
    Board board;
    Card shouldSummon;
    int player;
    boolean canSummon;

    public Texchanger(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        if (canSummon) {
            gameController.setState(State.SPECIAL_SUMMON);
            board.summonCard((MonsterCard) shouldSummon, player);
        }
        gameController.setState(State.NONE);
    }

    @Override
    public boolean canActivate() throws Exception {
        if (!(gameController.getState() == State.ATTACK))
            return false;
        board = gameController.getGameBoard();

        player = board.getOwnerOfCard(myCard);
        ArrayList<Card> graveyard = new ArrayList<>();
        ArrayList<Card> hand = new ArrayList<>();
        ArrayList<Card> deck = new ArrayList<>();

        for (Card card : board.getPlayerHand(player)) {
            if (card instanceof MonsterCard)
                if (((MonsterCard) card).getMonsterTypes().contains(MonsterType.CYBERSE) &&
                        ((MonsterCard) card).getCardType() == CardType.NORMAL)
                    hand.add(card);
        }
        for (Card card : board.getGraveyard(player)) {
            if (card instanceof MonsterCard)
                if (((MonsterCard) card).getMonsterTypes().contains(MonsterType.CYBERSE) &&
                        ((MonsterCard) card).getCardType() != CardType.NORMAL)
                    graveyard.add(card);
        }
        for (Card card : board.getPlayerDrawZone(player)) {
            if (card instanceof MonsterCard)
                if (((MonsterCard) card).getMonsterTypes().contains(MonsterType.CYBERSE) &&
                        ((MonsterCard) card).getCardType() != CardType.NORMAL)
                    deck.add(card);
        }
        ArrayList<Card> groups = new ArrayList<>();
        groups.addAll(deck);
        groups.addAll(hand);
        groups.addAll(graveyard);
        if (groups.isEmpty()) {
            canSummon = false;
            return true;
        }
        canSummon = true;
        shouldSummon = GameView.getNeededCards(groups,1).get(0);
        return true;
    }
}
