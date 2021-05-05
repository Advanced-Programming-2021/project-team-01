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
        else if (spell == Spell.UMIIRUKA)
            activeUmiiruka();
        else if (spell == Spell.BLACK_PENDANT){
            equipNormal();
        }
        else if (spell == Spell.UNITED_WE_STAND){
            equipNormal();
        }
        else if (spell == Spell.MAGNUM_SHIELD){
            equipWarrior();
        }
        else if (spell == Spell.SWORD_OF_DESTRUCTION){
            equipFiend();
        }

    }

    private void equipFiend() throws Exception {
        if (board.numberOfMonsterCards(gameController.getCurrentPlayerNumber()) == 0){
            throw new Exception("no monster card");
        }
        int indexOfMonster = Integer.parseInt(GameView.prompt("please choose a monster!"));
        ZoneSlot zoneSlot = board.getZoneSlotByLocation(CardLocation.MONSTER,indexOfMonster, gameController.getCurrentPlayerNumber());
        if (zoneSlot.getCard() == null) throw new Exception("there is no monster here!");
        if (!((MonsterCard)zoneSlot.getCard()).getMonsterTypes().contains(MonsterType.FIEND)){
            throw new Exception("only Fiend type monsters are allowed!");
        }
        zoneSlot.setEquippedCard(gameController.selectedCard.getCard());
        board.setSpellFaceUp(gameController.getCurrentPlayerNumber(), gameController.selectedCard.getCard());

    }

    private void equipWarrior() throws Exception {
        if (board.numberOfMonsterCards(gameController.getCurrentPlayerNumber()) == 0){
            throw new Exception("no monster card");
        }
        int indexOfMonster = Integer.parseInt(GameView.prompt("please choose a monster!"));
        ZoneSlot zoneSlot = board.getZoneSlotByLocation(CardLocation.MONSTER,indexOfMonster, gameController.getCurrentPlayerNumber());
        if (zoneSlot.getCard() == null) throw new Exception("there is no monster here!");
        if (!((MonsterCard)zoneSlot.getCard()).getMonsterTypes().contains(MonsterType.WARRIOR)){
            throw new Exception("only warrior type monsters are allowed");
        }
        zoneSlot.setEquippedCard(gameController.selectedCard.getCard());
        board.setSpellFaceUp(gameController.getCurrentPlayerNumber(), gameController.selectedCard.getCard());
    }

    private void equipNormal() throws Exception {
        if (board.numberOfMonsterCards(gameController.getCurrentPlayerNumber()) == 0){
            throw new Exception("no monster card");
        }
        int indexOfMonster = Integer.parseInt(GameView.prompt("please choose a monster!"));
        ZoneSlot zoneSlot = board.getZoneSlotByLocation(CardLocation.MONSTER,indexOfMonster, gameController.getCurrentPlayerNumber());
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
            ZoneSlot[] zoneSlot = gameController.gameBoard.getPlayerTwoMonsterZone();
            for (int i = 1; i < 6; i++) {
                if (zoneSlot[i].getCard() != null) {
                    gameController.gameBoard.sendCardFromMonsterZoneToGraveyard(i, gameController.getOpponentPlayerNumber());
                    zoneSlot[i].setCard(null);
                    zoneSlot[i].setHidden(false);
                    zoneSlot[i].setDefending(false);
                }
            }
            gameController.gameBoard.sendCardFromSpellZoneToGraveyard(gameController.getCurrentPlayerNumber(), gameController.selectedCard.getCard());
        } else {
            ZoneSlot[] zoneSlot = gameController.gameBoard.getPlayerOneMonsterZone();
            for (int i = 1; i < 6; i++) {
                if (zoneSlot[i].getCard() != null) {
                    gameController.gameBoard.sendCardFromMonsterZoneToGraveyard(i, gameController.getOpponentPlayerNumber());
                    zoneSlot[i].setCard(null);
                    zoneSlot[i].setHidden(false);
                    zoneSlot[i].setDefending(false);
                }
            }
            gameController.gameBoard.sendCardFromSpellZoneToGraveyard(gameController.getCurrentPlayerNumber(), gameController.selectedCard.getCard());
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

    protected void activeUmiiruka() {
        board.setCardFromHandToFieldZone(gameController.getCurrentPlayerNumber(),gameController.selectedCard.getCard());
    }
    protected void deActivateUmiiruka() {
        board.deactivateFieldSpell(gameController.getCurrentPlayerNumber());//Todo : age toye battle phase nabood she bayad handle she
    }

}

/*
    1- battle ox
    2- dadbeh  *|\+2+/|*
    3- erfan
    4-....
 */

