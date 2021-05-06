package controller;

import controller.exceptions.InvalidCommandException;
import controller.exceptions.MonsterZoneFull;
import controller.exceptions.NoFieldSpellInDeck;
import model.Board;
import model.ZoneSlot;
import model.card.*;
import view.menu.GameView;

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

    protected void run(Spell spell) throws Exception {
        if (spell == Spell.MONSTER_REBORN)
            monsterReborn();
        else if (spell == Spell.POT_OF_GREED)
            potOfGreed();
        else if (spell == Spell.RAIGEKI)
            raigeki();
        else if (spell == Spell.TERRAFORMING)
            terraforming();
        else if (spell == Spell.UMIIRUKA || spell == Spell.CLOSED_FOREST ||
                spell == Spell.FOREST || spell == Spell.YAMI)
            fieldCard();
        else if (spell == Spell.BLACK_PENDANT) {
            equipNormal();
        } else if (spell == Spell.UNITED_WE_STAND) {
            equipNormal();
        } else if (spell == Spell.MAGNUM_SHIELD) {
            equipWarrior();
        } else if (spell == Spell.SWORD_OF_DESTRUCTION) {
            equipFiend();
        } else if (spell == Spell.DARK_HOLE) {
            darkHole();
        } else if (spell == Spell.HARPIES_FEATHER_DUSTER) {
            harpiesFeatherDuster();
        }

    }

    private void equipFiend() throws Exception {
        if (board.numberOfMonsterCards(gameController.getCurrentPlayerNumber()) == 0) {
            throw new Exception("no monster card");
        }
        int indexOfMonster = Integer.parseInt(GameView.prompt("please choose a monster!"));
        ZoneSlot zoneSlot = board.getZoneSlotByLocation(CardLocation.MONSTER, indexOfMonster, gameController.getCurrentPlayerNumber());
        if (zoneSlot.getCard() == null) throw new Exception("there is no monster here!");
        if (!((MonsterCard) zoneSlot.getCard()).getMonsterTypes().contains(MonsterType.FIEND)) {
            throw new Exception("only Fiend type monsters are allowed!");
        }
        zoneSlot.setEquippedCard(gameController.selectedCard.getCard());
        board.setSpellFaceUp(gameController.getCurrentPlayerNumber(), gameController.selectedCard.getCard());

    }

    private void equipWarrior() throws Exception {
        if (board.numberOfMonsterCards(gameController.getCurrentPlayerNumber()) == 0) {
            throw new Exception("no monster card");
        }
        int indexOfMonster = Integer.parseInt(GameView.prompt("please choose a monster!"));
        ZoneSlot zoneSlot = board.getZoneSlotByLocation(CardLocation.MONSTER, indexOfMonster, gameController.getCurrentPlayerNumber());
        if (zoneSlot.getCard() == null) throw new Exception("there is no monster here!");
        if (!((MonsterCard) zoneSlot.getCard()).getMonsterTypes().contains(MonsterType.WARRIOR)) {
            throw new Exception("only warrior type monsters are allowed");
        }
        zoneSlot.setEquippedCard(gameController.selectedCard.getCard());
        board.setSpellFaceUp(gameController.getCurrentPlayerNumber(), gameController.selectedCard.getCard());
    }

    private void equipNormal() throws Exception {
        if (board.numberOfMonsterCards(gameController.getCurrentPlayerNumber()) == 0) {
            throw new Exception("no monster card");
        }
        int indexOfMonster = Integer.parseInt(GameView.prompt("please choose a monster!"));
        ZoneSlot zoneSlot = board.getZoneSlotByLocation(CardLocation.MONSTER, indexOfMonster, gameController.getCurrentPlayerNumber());
        if (zoneSlot.getCard() == null) throw new Exception("there is no monster here!");
        zoneSlot.setEquippedCard(gameController.selectedCard.getCard());
        board.setSpellFaceUp(gameController.getCurrentPlayerNumber(), gameController.selectedCard.getCard());
    }

    protected void monsterReborn() throws InvalidCommandException, MonsterZoneFull {
        String grave = GameView.prompt("player/opponent");
        ArrayList<Card> graveYard;
        if (grave.equals("player")) {
            if (gameController.getCurrentPlayerNumber() == 1) graveYard = board.getPlayerOneGraveYard();
            else graveYard = board.getPlayerTwoGraveYard();
        } else if (grave.equals("opponent")) {
            if (gameController.getCurrentPlayerNumber() == 1) graveYard = board.getPlayerTwoGraveYard();
            else graveYard = board.getPlayerOneGraveYard();
        } else {
            throw new InvalidCommandException();
        }
        int index = 0;
        for (Card card : graveYard) { //fixme : mvc bam
            System.out.print(index + "|" + card.getName() + " : " + card.getDescription());
            index++;
        }
        System.out.println();
        int cardIndex = Integer.parseInt(GameView.prompt("chose specified index"));
        if (cardIndex > index) throw new InvalidCommandException();
        Card card = graveYard.get(cardIndex);
        board.addCardFromGraveYardToField(gameController.getCurrentPlayerNumber(), card);
        graveYard.remove(card);
        board.sendCardFromSpellZoneToGraveyard(gameController.getCurrentPlayerNumber(), gameController.selectedCard.getCard());
    }

    protected void potOfGreed() {
        int playerNumber = gameController.getCurrentPlayerNumber();
        board.addCardFromDeckToHand(playerNumber);
        board.addCardFromDeckToHand(playerNumber);
        board.sendCardFromSpellZoneToGraveyard(gameController.getCurrentPlayerNumber(), gameController.selectedCard.getCard());
    }

    protected void raigeki() {
        if (gameController.getCurrentPlayerNumber() == 1) {
            ZoneSlot[] zoneSlot = board.getPlayerTwoMonsterZone();
            for (int i = 1; i < 6; i++) {
                if (zoneSlot[i].getCard() != null) {
                    board.sendCardFromMonsterZoneToGraveyard(i, gameController.getOpponentPlayerNumber());
                    zoneSlot[i].setHidden(false);
                    zoneSlot[i].setDefending(false);
                }
            }
            board.sendCardFromSpellZoneToGraveyard(gameController.getCurrentPlayerNumber(), gameController.selectedCard.getCard());
        } else {
            ZoneSlot[] zoneSlot = board.getPlayerOneMonsterZone();
            for (int i = 1; i < 6; i++) {
                if (zoneSlot[i].getCard() != null) {
                    board.sendCardFromMonsterZoneToGraveyard(i, gameController.getOpponentPlayerNumber());
                    zoneSlot[i].setHidden(false);
                    zoneSlot[i].setDefending(false);
                }
            }
            board.sendCardFromSpellZoneToGraveyard(gameController.getCurrentPlayerNumber(), gameController.selectedCard.getCard());
        }
    }

    protected void terraforming() throws NoFieldSpellInDeck {
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
        String indexString = GameView.prompt("Enter a number :");
        int index;
        try {
            index = Integer.parseInt(indexString);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
        Card card = fieldSpells.get(index);
        board.addCardFromDeckToHand(gameController.getCurrentPlayerNumber(), card);
        board.sendCardFromSpellZoneToGraveyard(gameController.getCurrentPlayerNumber(), gameController.selectedCard.getCard());
    }

    protected void fieldCard() {
        board.setCardFromHandToFieldZone(gameController.getCurrentPlayerNumber(), gameController.selectedCard.getCard());
    }

    public int fieldAttackBoosterCurrent(MonsterCard monsterCard) {
        if (!board.isCardInMonsterZone(monsterCard))
            return 0;
        ZoneSlot zoneSlot = gameController.gameBoard.getPlayerFieldZone(gameController.getCurrentPlayerNumber());
        SpellCard card = (SpellCard) zoneSlot.getCard();
        if (card == null)
            return 0;
        Spell spell = Spell.getSpellByName(card.getName());
        if (spell == Spell.UMIIRUKA && monsterCard.getMonsterTypes().contains(MonsterType.AQUA))
            return 500;
        if (spell == Spell.FOREST && (monsterCard.getMonsterTypes().contains(MonsterType.INSECT) ||
                monsterCard.getMonsterTypes().contains(MonsterType.BEAST) || monsterCard.getMonsterTypes().contains(MonsterType.BEAST_WARRIOR)))
            return 200;
        if (spell == Spell.CLOSED_FOREST && (monsterCard.getMonsterTypes().contains(MonsterType.BEAST) ||
                monsterCard.getMonsterTypes().contains(MonsterType.BEAST_WARRIOR))) {
            if (gameController.getCurrentPlayerNumber() == 1) {
                return 100 * board.getPlayerOneGraveYard().size();
            } else {
                return 100 * board.getPlayerTwoGraveYard().size();
            }
        }
        if (spell == Spell.YAMI) {
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
        Spell spell = Spell.getSpellByName(card.getName());
        if (spell == Spell.UMIIRUKA && monsterCard.getMonsterTypes().contains(MonsterType.AQUA))
            return 500;
        if (spell == Spell.FOREST && (monsterCard.getMonsterTypes().contains(MonsterType.INSECT) ||
                monsterCard.getMonsterTypes().contains(MonsterType.BEAST) || monsterCard.getMonsterTypes().contains(MonsterType.BEAST_WARRIOR)))
            return 200;
        if (spell == Spell.YAMI) {
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
        Spell spell = Spell.getSpellByName(card.getName());
        if (spell == Spell.UMIIRUKA && monsterCard.getMonsterTypes().contains(MonsterType.AQUA))
            return -400;
        if (spell == Spell.FOREST && (monsterCard.getMonsterTypes().contains(MonsterType.INSECT) ||
                monsterCard.getMonsterTypes().contains(MonsterType.BEAST) || monsterCard.getMonsterTypes().contains(MonsterType.BEAST_WARRIOR)))
            return 200;
        if (spell == Spell.YAMI) {
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
        Spell spell = Spell.getSpellByName(card.getName());
        if (spell == Spell.UMIIRUKA && monsterCard.getMonsterTypes().contains(MonsterType.AQUA))
            return -400;
        if (spell == Spell.FOREST && (monsterCard.getMonsterTypes().contains(MonsterType.INSECT) ||
                monsterCard.getMonsterTypes().contains(MonsterType.BEAST) || monsterCard.getMonsterTypes().contains(MonsterType.BEAST_WARRIOR)))
            return 200;
        if (spell == Spell.YAMI) {
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
            if (!board.getZoneSlotByLocation(CardLocation.MONSTER, i, gameController.getCurrentPlayerNumber()).isHidden()) {
                counter++;
            }
        }
        return 800 * counter;
    }

    public void darkHole() {
        ZoneSlot[] zoneSlot = board.getPlayerTwoMonsterZone();
        for (int i = 1; i < 6; i++) {
            if (zoneSlot[i].getCard() != null) {
                board.sendCardFromMonsterZoneToGraveyard(i, 2);
                zoneSlot[i].setHidden(false);
                zoneSlot[i].setDefending(false);
            }
        }
        zoneSlot = board.getPlayerOneMonsterZone();
        for (int i = 1; i < 6; i++) {
            if (zoneSlot[i].getCard() != null) {
                board.sendCardFromMonsterZoneToGraveyard(i, 1);
                zoneSlot[i].setHidden(false);
                zoneSlot[i].setDefending(false);
            }
        }
        board.sendCardFromSpellZoneToGraveyard(gameController.getCurrentPlayerNumber(), gameController.selectedCard.getCard());
    }

    public void harpiesFeatherDuster() {
        ZoneSlot[] zoneSlot = board.getPlayerSpellZone(gameController.getOpponentPlayerNumber());
        for (int i = 1; i < 6; i++) {
            if (zoneSlot[i].getCard() != null) {
                board.sendCardFromSpellZoneToGraveyard(i, zoneSlot[i].getCard());
                zoneSlot[i].setHidden(false);
            }
        }
        board.sendCardFromSpellZoneToGraveyard(gameController.getCurrentPlayerNumber(), gameController.selectedCard.getCard());
    }
}

//Harpies feather dust, twin twister, mystical space typhoon

/*
    1- battle ox
    2- dadbeh  *|\+2+/|*
    3- erfan
    4-....
 */

