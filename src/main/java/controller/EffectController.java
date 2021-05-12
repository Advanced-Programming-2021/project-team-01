package controller;

import model.Board;
import model.ZoneSlot;
import model.card.*;

import java.util.ArrayList;
import java.util.HashMap;

public class EffectController {
    GameController gameController;
    Board board;

    public EffectController(GameController gameController) {
        this.gameController = gameController;
        board = gameController.gameBoard;
    }

    HashMap<Card, Integer> changeOfCards = new HashMap<>();
    ArrayList<Card> effectedCards = new ArrayList<>();


    public void doEffects() throws Exception {
        ArrayList<Card> cards = board.faceUpSpellAndTraps();
        for (Card card : cards) {
            card.doContinuousActions();
        }

    }

    public int fieldAttackBoosterCurrent(MonsterCard monsterCard) {
        if (!board.isCardInMonsterZone(monsterCard))
            return 0;
        ZoneSlot zoneSlot = gameController.gameBoard.getPlayerFieldZone(gameController.getCurrentPlayerNumber());
        SpellCard card = (SpellCard) zoneSlot.getCard();
        if (card == null)
            return 0;
        Effect effect = Effect.getSpellByName(card.getName());
        if (effect == Effect.UMIIRUKA && monsterCard.getMonsterTypes().contains(MonsterType.AQUA))
            return 500;
        if (effect == Effect.FOREST && (monsterCard.getMonsterTypes().contains(MonsterType.INSECT) ||
                monsterCard.getMonsterTypes().contains(MonsterType.BEAST) || monsterCard.getMonsterTypes().contains(MonsterType.BEAST_WARRIOR)))
            return 200;
        if (effect == Effect.CLOSED_FOREST && (monsterCard.getMonsterTypes().contains(MonsterType.BEAST) ||
                monsterCard.getMonsterTypes().contains(MonsterType.BEAST_WARRIOR))) {
            if (gameController.getCurrentPlayerNumber() == 1) {
                if (board.getOwnerOfCard(monsterCard) == 1)
                    return 100 * board.getPlayerOneGraveYard().size();
            } else {
                if (board.getOwnerOfCard(monsterCard) == 2)
                    return 100 * board.getPlayerTwoGraveYard().size();
            }
        }
        if (effect == Effect.YAMI) {
            if (monsterCard.getMonsterTypes().contains(MonsterType.FIEND) ||
                    monsterCard.getMonsterTypes().contains(MonsterType.SPELLCASTER))
                return 200;
            if (monsterCard.getMonsterTypes().contains(MonsterType.FAIRY))
                return -200;
        }
        return 0;
    }

    public int fieldAttackBoosterOpponent(MonsterCard monsterCard) {
        if (!board.isCardInMonsterZone(monsterCard))
            return 0;
        ZoneSlot zoneSlot = gameController.gameBoard.getPlayerFieldZone(gameController.getOpponentPlayerNumber());
        SpellCard card = (SpellCard) zoneSlot.getCard();
        if (card == null)
            return 0;
        Effect effect = Effect.getSpellByName(card.getName());
        if (effect == Effect.UMIIRUKA && monsterCard.getMonsterTypes().contains(MonsterType.AQUA))
            return 500;
        if (effect == Effect.FOREST && (monsterCard.getMonsterTypes().contains(MonsterType.INSECT) ||
                monsterCard.getMonsterTypes().contains(MonsterType.BEAST) || monsterCard.getMonsterTypes().contains(MonsterType.BEAST_WARRIOR)))
            return 200;
        if (effect == Effect.YAMI) {
            if (monsterCard.getMonsterTypes().contains(MonsterType.FIEND) ||
                    monsterCard.getMonsterTypes().contains(MonsterType.SPELLCASTER))
                return 200;
            if (monsterCard.getMonsterTypes().contains(MonsterType.FAIRY))
                return -200;
        }
        return 0;
    }

    public int fieldDefenceBoosterCurrent(MonsterCard monsterCard) {
        if (!board.isCardInMonsterZone(monsterCard))
            return 0;
        ZoneSlot zoneSlot = gameController.gameBoard.getPlayerFieldZone(gameController.getCurrentPlayerNumber());
        SpellCard card = (SpellCard) zoneSlot.getCard();
        if (card == null)
            return 0;
        Effect effect = Effect.getSpellByName(card.getName());
        if (effect == Effect.UMIIRUKA && monsterCard.getMonsterTypes().contains(MonsterType.AQUA))
            return -400;
        if (effect == Effect.FOREST && (monsterCard.getMonsterTypes().contains(MonsterType.INSECT) ||
                monsterCard.getMonsterTypes().contains(MonsterType.BEAST) || monsterCard.getMonsterTypes().contains(MonsterType.BEAST_WARRIOR)))
            return 200;
        if (effect == Effect.YAMI) {
            if (monsterCard.getMonsterTypes().contains(MonsterType.FIEND) ||
                    monsterCard.getMonsterTypes().contains(MonsterType.SPELLCASTER))
                return 200;
            if (monsterCard.getMonsterTypes().contains(MonsterType.FAIRY))
                return -200;
        }
        return 0;
    }

    public int fieldDefenceBoosterOpponent(MonsterCard monsterCard) {
        if (!board.isCardInMonsterZone(monsterCard))
            return 0;
        ZoneSlot zoneSlot = gameController.gameBoard.getPlayerFieldZone(gameController.getOpponentPlayerNumber());
        SpellCard card = (SpellCard) zoneSlot.getCard();
        if (card == null)
            return 0;
        Effect effect = Effect.getSpellByName(card.getName());
        if (effect == Effect.UMIIRUKA && monsterCard.getMonsterTypes().contains(MonsterType.AQUA))
            return -400;
        if (effect == Effect.FOREST && (monsterCard.getMonsterTypes().contains(MonsterType.INSECT) ||
                monsterCard.getMonsterTypes().contains(MonsterType.BEAST) || monsterCard.getMonsterTypes().contains(MonsterType.BEAST_WARRIOR)))
            return 200;
        if (effect == Effect.YAMI) {
            if (monsterCard.getMonsterTypes().contains(MonsterType.FIEND) ||
                    monsterCard.getMonsterTypes().contains(MonsterType.SPELLCASTER))
                return 200;
            if (monsterCard.getMonsterTypes().contains(MonsterType.FAIRY))
                return -200;
        }
        return 0;
    }
    public int unitedWeStand() {
        int counter = 0;
        for (int i = 1; i <= 5; i++) {
            if (!board.getZoneSlotByLocation(CardLocation.MONSTER, i, gameController.getCurrentPlayerNumber()).isHidden() &&
            board.getZoneSlotByLocation(CardLocation.MONSTER, i , gameController.getCurrentPlayerNumber()).getCard() == null) {
                counter++;
            }
        }
        return 800 * counter;
    }

}