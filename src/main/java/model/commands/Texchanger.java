package model.commands;

import model.Board;
import model.State;
import model.card.Card;
import model.card.CardType;
import model.card.MonsterCard;
import model.card.MonsterType;
import view.menu.GameView;

import java.util.ArrayList;

public class Texchanger extends Command implements Activate {
    Board board;
    Card shouldSummon;
    int player;
    public Texchanger(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        gameController.setState(State.SPECIAL_SUMMON);
        board.summonCard((MonsterCard) shouldSummon, player);
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
                if (((MonsterCard) card).getMonsterTypes().contains(MonsterType.CYBER) &&
                        ((MonsterCard) card).getCardType() != CardType.NORMAL)
                    hand.add(card);
        }
        for (Card card : board.getGraveyard(player)) {
            if (card instanceof MonsterCard)
                if (((MonsterCard) card).getMonsterTypes().contains(MonsterType.CYBER) &&
                        ((MonsterCard) card).getCardType() != CardType.NORMAL)
                    graveyard.add(card);
        }
        for (Card card : board.getPlayerDrawZone(player)) {
            if (card instanceof MonsterCard)
                if (((MonsterCard) card).getMonsterTypes().contains(MonsterType.CYBER) &&
                        ((MonsterCard) card).getCardType() != CardType.NORMAL)
                    deck.add(card);
        }
        ArrayList<String> groups = new ArrayList<>();
        if (!deck.isEmpty()) groups.add("Deck");
        if (!hand.isEmpty()) groups.add("Hand");
        if (!graveyard.isEmpty()) groups.add("Graveyard");
        if (groups.isEmpty()) return false;
        String selected = GameView.selectGroups(groups);
        int index;
        switch (selected) {
            case "Hand":
                GameView.printListOfCard(hand);
                index = GameView.getValidNumber(0,hand.size()-1);
                shouldSummon = hand.get(index);
                break;
            case "Deck":
                GameView.printListOfCard(deck);
                index = GameView.getValidNumber(0,deck.size()-1);
                shouldSummon = deck.get(index);
                break;
            case "Graveyard":
                GameView.printListOfCard(graveyard);
                index = GameView.getValidNumber(0,graveyard.size()-1);
                shouldSummon = graveyard.get(index);
                break;
        }
        return true;
    }
}
