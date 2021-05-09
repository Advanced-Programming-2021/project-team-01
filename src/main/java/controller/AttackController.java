package controller;

import controller.exceptions.*;
import model.GamePhase;
import model.ZoneSlot;
import model.card.Card;
import model.card.CardLocation;
import model.card.MonsterCard;

import java.util.ArrayList;

public class AttackController {
    GameController gameController;
    ArrayList<Card> attackedCards = new ArrayList<>();//TODO: clear in battle phase
    MonsterCard target;
    MonsterCard attacker;
    public AttackController(GameController gameController) {
        this.gameController = gameController;
    }

    public MonsterCard getAttacker() {
        return attacker;
    }

    public MonsterCard getTarget() {
        return target;
    }

    protected String attack(int number) throws CardNotSelected, NotMonsterCard, NotAllowedAction, AlreadyAttacked, NoCardToAttack {
        if (gameController.selectedCard.getCard() == null) {
            throw new CardNotSelected();
        }
        if (gameController.selectedCard.getCardLocation() != CardLocation.MONSTER ||
                !gameController.getZoneSlotSelectedCard().toString().equals("OO")) {
            throw new NotMonsterCard();
        }
        if (gameController.phaseController.getGamePhase() != GamePhase.BATTLE_PHASE) {
            throw new NotAllowedAction();
        }
        if (attackedCards.contains(gameController.selectedCard.getCard())){
            throw new AlreadyAttacked();
        }
        target = (MonsterCard) gameController.gameBoard.getCard("monster", gameController.getOpponentPlayerNumber(),number);
        attacker = (MonsterCard) gameController.selectedCard.getCard();
        if (target == null){
            throw new NoCardToAttack();
        }
        if (gameController.gameBoard.getZoneSlotByLocation(CardLocation.MONSTER,number,
                gameController.getOpponentPlayerNumber()).toString().equals("OO")){
            return attackInFaceUpPosition(number, target, attacker);
        } else if (gameController.gameBoard.getZoneSlotByLocation(CardLocation.MONSTER,number,
                gameController.getOpponentPlayerNumber()).toString().equals("DO")){
            return attackFaceUpDefencePosition(number, target, attacker);
        } else {
            return attackFaceDownDefencePosition(number, target, attacker);
        }
    }

    private String attackFaceDownDefencePosition(int number, MonsterCard target, MonsterCard attacker) {
        ZoneSlot zoneSlotAttacker = gameController.gameBoard.getZoneSlotByCard(attacker);
        ZoneSlot zoneSlotTarget = gameController.gameBoard.getZoneSlotByCard(target);
        int damage = zoneSlotAttacker.getAttack() - zoneSlotTarget.getDefence();
        gameController.gameBoard.getZoneSlotByLocation(CardLocation.MONSTER, number, gameController.getOpponentPlayerNumber()).setHidden(false);
        if (damage > 0){
            gameController.gameBoard.sendCardFromMonsterZoneToGraveyard(number, gameController.getOpponentPlayerNumber());
            attackedCards.add(attacker);
            return String.format("the defense position monster %s was destroyed", target.getName());
        }
        if (damage == 0){
            attackedCards.add(attacker);
            return String.format("defense position monster was %s and no card is destroyed", target.getName());
        }
        else {
            gameController.increasePlayerLp(damage);
            attackedCards.add(attacker);
            return String.format("defense position monster was %s and no card is destroyed and you receive %d damage", target.getName(),-damage);
        }
    }

    private String attackFaceUpDefencePosition(int number, MonsterCard target, MonsterCard attacker) {
        ZoneSlot zoneSlotAttacker = gameController.gameBoard.getZoneSlotByCard(attacker);
        ZoneSlot zoneSlotTarget = gameController.gameBoard.getZoneSlotByCard(target);
        int damage = zoneSlotAttacker.getAttack() - zoneSlotTarget.getDefence();
        if (damage > 0){
            gameController.gameBoard.sendCardFromMonsterZoneToGraveyard(number, gameController.getOpponentPlayerNumber());
            attackedCards.add(attacker);
            return "the defense position monster was destroyed";
        }
        if (damage == 0){
            attackedCards.add(attacker);
            return "no card is destroyed";
        }
        else {
            gameController.increasePlayerLp(damage);
            attackedCards.add(attacker);
            return String.format("no card is destroyed and you receive %d damage",-damage);
        }
    }

    private String attackInFaceUpPosition(int number, MonsterCard target, MonsterCard attacker) {
        ZoneSlot zoneSlotAttacker = gameController.gameBoard.getZoneSlotByCard(attacker);
        ZoneSlot zoneSlotTarget = gameController.gameBoard.getZoneSlotByCard(target);
        int damage = zoneSlotAttacker.getAttack() - zoneSlotTarget.getAttack();
        if (damage > 0){
            gameController.increaseOpponentLp(-damage);
            gameController.gameBoard.sendCardFromMonsterZoneToGraveyard(number, gameController.getOpponentPlayerNumber());
            attackedCards.add(attacker);
            return String.format("opponent monster destroyed and opponent received %d damage",damage);
        }
        else if (damage == 0){
            gameController.gameBoard.sendCardFromMonsterZoneToGraveyard(number, gameController.getOpponentPlayerNumber());
            gameController.gameBoard.sendCardFromMonsterZoneToGraveyard(gameController.selectedCard.getIndex(), gameController.getCurrentPlayerNumber());
            return "both you and your opponent received no damage and both cards are destroyed";
        }
        else{
            gameController.increasePlayerLp(-damage);
            gameController.gameBoard.sendCardFromMonsterZoneToGraveyard(gameController.selectedCard.getIndex(), gameController.getCurrentPlayerNumber());
            return String.format("your monster card is destroyed and you receive %d damage",-damage);
        }
    }

    protected String directAttack() throws CardNotSelected, NotMonsterCard, NotAllowedAction, AlreadyAttacked, DirectAttackError {
        if (gameController.selectedCard.getCard() == null) {
            throw new CardNotSelected();
        }
        if (gameController.selectedCard.getCardLocation() != CardLocation.MONSTER ||
                !gameController.getZoneSlotSelectedCard().toString().equals("OO")) {
            throw new NotMonsterCard();
        }
        if (gameController.phaseController.getGamePhase() != GamePhase.BATTLE_PHASE) {
            throw new NotAllowedAction();
        }
        if (attackedCards.contains(gameController.selectedCard.getCard())){
            throw new AlreadyAttacked();
        }
        if (!gameController.getGameBoard().isMonsterZoneEmpty(gameController.getOpponentPlayerNumber())) {
            throw new DirectAttackError();
        }
        MonsterCard attackingCard = (MonsterCard) gameController.selectedCard.getCard();
        ZoneSlot zoneSlotAttacker = gameController.gameBoard.getZoneSlotByCard(attackingCard);
        gameController.decreasePlayerLP(gameController.getOpponentPlayerNumber(), zoneSlotAttacker.getAttack());
        attackedCards.add(attackingCard);
        return String.format("you opponent receives %d battle damage", zoneSlotAttacker.getAttack());
    }
}
