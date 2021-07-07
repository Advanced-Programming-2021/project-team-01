package model.commands;

import model.Board;
import model.GamePhase;
import model.ZoneSlot;
import model.card.Card;
import model.card.MonsterCard;
import view.GameView;

import java.util.ArrayList;

public class Scanner extends Command implements Activate {
    Board board;
    Card card;
    MonsterCard tempMyCard;
    int index;
    ZoneSlot[] monsterZones;

    public Scanner(Card card) {
        super(card);
    }

    @Override
    public void run() throws Exception {
        board = gameController.getGameBoard();
        tempMyCard = (MonsterCard) ((MonsterCard) myCard).clone();
        index = gameController.getSelectedCard().getIndex();
        if (gameController.getCurrentPlayerNumber() == 1)
            monsterZones = board.getPlayerOneMonsterZone();
        else
            monsterZones = board.getPlayerTwoMonsterZone();
        int owner = board.getOwnerOfCard(myCard);
        int opponent;
        if (owner == 1)
            opponent = 2;
        else
            opponent = 1;
        ArrayList<Card> graveyards = new ArrayList<>(board.getGraveyard(opponent));
        ArrayList<Card> monsterCardsInGraveYard = new ArrayList<>();
        for (Card card1 : graveyards) {
            if (card1 instanceof MonsterCard)
                monsterCardsInGraveYard.add(card1);
        }
        if (monsterCardsInGraveYard.size() == 0)
            throw new Exception("Graveyard is empty.");
        card = GameView.getNeededCards(monsterCardsInGraveYard, 1).get(0);
        copyCard((MonsterCard) card, (MonsterCard) myCard);
    }

    public void copyCard(MonsterCard reference, MonsterCard scanner) {
        scanner.setAttack(reference.getAttack());
        scanner.setCardType(reference.getCardType());
        scanner.setDefense(reference.getDefense());
        scanner.setMonsterTypes(reference.getMonsterTypes());
        scanner.setAttribute(reference.getAttribute());
        scanner.setLevel(reference.getLevel());
        scanner.setName(reference.getName());
        scanner.setDescription(reference.getDescription());
    }

    @Override
    public void runContinuous() throws Exception {
        if (gameController.getGamePhase() == GamePhase.END_PHASE) {
            copyCard(tempMyCard, (MonsterCard) myCard);
        } else if (gameController.getGamePhase() == GamePhase.STANDBY_PHASE) {
            int owner = board.getOwnerOfCard(myCard);
            int opponent;
            if (owner == 1)
                opponent = 2;
            else
                opponent = 1;
            ArrayList<Card> graveyards = new ArrayList<>(board.getGraveyard(opponent));
            ArrayList<Card> monsterCardsInGraveYard = new ArrayList<>();
            for (Card card1 : graveyards) {
                if (card1 instanceof MonsterCard)
                    monsterCardsInGraveYard.add(card1);
            }
            if (monsterCardsInGraveYard.size() == 0)
                return;
            card = GameView.getNeededCards(monsterCardsInGraveYard, 1).get(0);
            GameView.showAlert("Card selected for scanner");
            copyCard((MonsterCard) card, (MonsterCard) myCard);
        }
    }

}
