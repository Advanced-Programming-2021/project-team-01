package model.commands;

import model.Board;
import model.GamePhase;
import model.ZoneSlot;
import model.card.Card;
import model.card.MonsterCard;
import console.menu.GameView;

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


        ArrayList<Card> graveyards = new ArrayList<>(board.getPlayerOneGraveYard());
        graveyards.addAll(board.getPlayerTwoGraveYard());
        ArrayList<Card> monsterCardsInGraveYard = new ArrayList<>();
        for (Card card1 : graveyards) {
            if (card1 instanceof MonsterCard)
                monsterCardsInGraveYard.add(card1);
        }
        if (monsterCardsInGraveYard.size() == 0)
            throw new Exception("Graveyard is empty.");
        GameView.printListOfCard(monsterCardsInGraveYard);
        int indexOfCard = GameView.getValidNumber(0, monsterCardsInGraveYard.size() - 1);
        card = monsterCardsInGraveYard.get(indexOfCard);
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
        if (board.getOwnerOfCard(myCard) == gameController.getCurrentPlayerNumber()) {
            if (gameController.getGamePhase() == GamePhase.END_PHASE) {
                copyCard(tempMyCard, (MonsterCard) myCard);
            } else if (gameController.getGamePhase() == GamePhase.STANDBY_PHASE) {
                ArrayList<Card> graveyards = new ArrayList<>(board.getPlayerOneGraveYard());
                graveyards.addAll(board.getPlayerTwoGraveYard());
                ArrayList<Card> monsterCardsInGraveYard = new ArrayList<>();
                for (Card card1 : graveyards) {
                    if (card1 instanceof MonsterCard)
                        monsterCardsInGraveYard.add(card1);
                }
                if (monsterCardsInGraveYard.size() == 0)
                    return;
                GameView.showConsole("Select card for scanner:");
                GameView.printListOfCard(monsterCardsInGraveYard);
                int indexOfCard = GameView.getValidNumber(0, monsterCardsInGraveYard.size() - 1);
                card = monsterCardsInGraveYard.get(indexOfCard);
                copyCard((MonsterCard) card, (MonsterCard) myCard);
            }
        }
    }
}
