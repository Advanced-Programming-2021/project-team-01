package controller;

import model.Board;
import model.State;
import model.ZoneSlot;
import model.card.*;

import java.util.ArrayList;
import java.util.HashMap;

public class EffectController {
    GameController gameController;
    Board board;
    AttackController attackController;
    HashMap<Card, Integer> changeOfCards = new HashMap<>();
    ArrayList<Card> effectedCards = new ArrayList<>();

    public EffectController(GameController gameController) {
        this.gameController = gameController;
        board = gameController.gameBoard;
    }

    public void doEffects() throws Exception {
        ArrayList<Card> cards = board.faceUpSpellAndTraps();
        for (Card card : cards) {
            card.doContinuousActions();
        }

    }

    public int fieldAttackBoosterCurrent(MonsterCard monsterCard) {
        if (!board.isCardInMonsterZone(monsterCard))
            return 0;
        ZoneSlot zoneSlot = gameController.gameBoard.getPlayerFieldZone(board.getOwnerOfCard(monsterCard));
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
        int number = board.getOwnerOfCard(monsterCard);
        if (number == 1)
            number = 2;
        else
            number = 1;
        ZoneSlot zoneSlot = gameController.gameBoard.getPlayerFieldZone(number);
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
        ZoneSlot zoneSlot = gameController.gameBoard.getPlayerFieldZone(board.getOwnerOfCard(monsterCard));
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
        int number = board.getOwnerOfCard(monsterCard);
        if (number == 1)
            number = 2;
        else
            number = 1;
        ZoneSlot zoneSlot = gameController.gameBoard.getPlayerFieldZone(number);
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
                    board.getZoneSlotByLocation(CardLocation.MONSTER, i, gameController.getCurrentPlayerNumber()).getCard() != null) {
                counter++;
            }
        }
        return 800 * counter;
    }

    public void checkTargetEffects() throws Exception {
        if (gameController.getState() == State.ATTACK) {
            attackController = gameController.getAttackController();
            if (attackController.getTarget().getName().equals(Effect.YOMI_SHIP.toString())) {
                if (attackController.getDamage() > 0)
                    board.sendCardFromMonsterZoneToGraveyard(attackController.getAttacker());
            } else if (attackController.getTarget().getName().equals(Effect.SUIJIN.toString())) {
                if (attackController.getTarget().canActivate())
                    attackController.getTarget().doActions();
            } else if (attackController.getTarget().getName().equals(Effect.EXPLODER_DRAGON.toString())) {
                if (attackController.getDamage() > 0) {
                    attackController.setDamage(0);
                }

            }
        }
    }

    public int getCalculatorPoints() {
        ZoneSlot[] array = gameController.getGameBoard().getCurrentPlayerMonsterCards();
        int totalLevel = 0;
        for (int i = 1; i <= 5; i++) {
            if (array[i].getCard() != null && !array[i].isHidden()) {
                totalLevel += ((MonsterCard) array[i].getCard()).getLevel();
            }
        }
        return totalLevel * 300;
    }

    public boolean isMirageDragoon() {
        Board board = GameController.instance.getGameBoard();
        for (int playerNum = 1; playerNum <= 2; playerNum++) {
            for (int i = 1; i <= 5; i++) {
                Card card = board.getPlayerMonsterZone(playerNum)[i].getCard();
                if (card != null && card.getName().equals("Mirage Dragon") &&
                        !board.getPlayerMonsterZone(playerNum)[i].isHidden()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isSwordOfRevealingLight() {
        Board board = GameController.instance.getGameBoard();
        for (int i = 1; i <= 5; i++) {
            Card card = board.getPlayerSpellZone(gameController.getOpponentPlayerNumber())[i].getCard();
            if (card != null && card.getName().equals("Swords of Revealing Light")) {
                return true;
            }
        }
        return false;
    }

    public boolean isMessengerOfPeace() {
        Board board = GameController.instance.getGameBoard();
        for (int i = 1; i <= 5; i++) {
            Card card = board.getPlayerSpellZone(gameController.getOpponentPlayerNumber())[i].getCard();
            if (card != null && card.getName().equals("Messenger of peace")) {
                return true;
            }
        }
        return false;
    }
}