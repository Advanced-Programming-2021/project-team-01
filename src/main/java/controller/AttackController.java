package controller;

import controller.exceptions.*;
import model.GamePhase;
import model.State;
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
    int damage;

    public AttackController(GameController gameController) {
        this.gameController = gameController;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public MonsterCard getAttacker() {
        return attacker;
    }

    public MonsterCard getTarget() {
        return target;
    }

    protected String attack(int number) throws Exception {
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
        if (gameController.gameBoard.getOwnerOfCard(gameController.selectedCard.getCard()) != gameController.getCurrentPlayerNumber() )
            throw new Exception("You can't attack with opponent card");
        if (attackedCards.contains(gameController.selectedCard.getCard())) {
            throw new AlreadyAttacked();
        }
        target = (MonsterCard) gameController.gameBoard.getCard("monster", gameController.getOpponentPlayerNumber(), number);
        attacker = (MonsterCard) gameController.selectedCard.getCard();
        if (target == null) {
            throw new NoCardToAttack();
        }
        if (target.getName().equals(Effect.TEXCHANGER.toString()) && target.canActivate())
            gameController.createChain(target);
        else
            gameController.createChain();
        gameController.chain.run();

        if (gameController.getState() != State.ATTACK || gameController.effectController.isSwordOfRevealingLight()) {
            return "battle negated";
        }
        if (gameController.gameBoard.getZoneSlotByLocation(CardLocation.MONSTER, number,
                gameController.getOpponentPlayerNumber()).toString().equals("OO")) {
            return attackInFaceUpPosition(number, target, attacker);
        } else if (gameController.gameBoard.getZoneSlotByLocation(CardLocation.MONSTER, number,
                gameController.getOpponentPlayerNumber()).toString().equals("DO")) {
            return attackFaceUpDefencePosition(number, target, attacker);
        } else {
            return attackFaceDownDefencePosition(number, target, attacker);
        }
    }

    private String attackFaceDownDefencePosition(int number, MonsterCard target, MonsterCard attacker) throws Exception {
        ZoneSlot zoneSlotAttacker = gameController.gameBoard.getZoneSlotByCard(attacker);
        ZoneSlot zoneSlotTarget = gameController.gameBoard.getZoneSlotByCard(target);
        damage = zoneSlotAttacker.getAttack() - zoneSlotTarget.getDefence();
        gameController.gameBoard.getZoneSlotByLocation(CardLocation.MONSTER, number, gameController.getOpponentPlayerNumber()).setHidden(false);
        gameController.effectController.checkTargetEffects();
        if (target.getName().equals(Effect.MARSHMALLON.toString()))
            gameController.decreasePlayerLP(gameController.getCurrentPlayerNumber(), 1000);
        if (damage > 0) {
            if (target.getName().equals(Effect.MARSHMALLON.toString())) {
                attackedCards.add(attacker);
                return String.format("the defense position monster %s was attacked", target.getName());
            }
            gameController.gameBoard.sendCardFromMonsterZoneToGraveyard(number, gameController.getOpponentPlayerNumber());
            attackedCards.add(attacker);
            return String.format("the defense position monster %s was destroyed", target.getName());
        }
        if (damage == 0) {
            attackedCards.add(attacker);
            return String.format("defense position monster was %s and no card is destroyed", target.getName());
        } else {
            gameController.increasePlayerLp(damage);
            attackedCards.add(attacker);
            return String.format("defense position monster was %s and no card is destroyed and you receive %d damage", target.getName(), -damage);
        }
    }

    private String attackFaceUpDefencePosition(int number, MonsterCard target, MonsterCard attacker) throws Exception {
        ZoneSlot zoneSlotAttacker = gameController.gameBoard.getZoneSlotByCard(attacker);
        ZoneSlot zoneSlotTarget = gameController.gameBoard.getZoneSlotByCard(target);
        damage = zoneSlotAttacker.getAttack() - zoneSlotTarget.getDefence();
        gameController.effectController.checkTargetEffects();
        if (damage > 0) {
            if (target.getName().equals(Effect.MARSHMALLON.toString())) {
                attackedCards.add(attacker);
                return "the defense position monster was Marshmallon";
            }
            gameController.gameBoard.sendCardFromMonsterZoneToGraveyard(number, gameController.getOpponentPlayerNumber());
            attackedCards.add(attacker);
            return "the defense position monster was destroyed";
        }
        if (damage == 0) {
            attackedCards.add(attacker);
            return "no card is destroyed";
        } else {
            gameController.increasePlayerLp(damage);
            attackedCards.add(attacker);
            return String.format("no card is destroyed and you receive %d damage", -damage);
        }
    }

    private String attackInFaceUpPosition(int number, MonsterCard target, MonsterCard attacker) throws Exception {
        ZoneSlot zoneSlotAttacker = gameController.gameBoard.getZoneSlotByCard(attacker);
        ZoneSlot zoneSlotTarget = gameController.gameBoard.getZoneSlotByCard(target);
        damage = zoneSlotAttacker.getAttack() - zoneSlotTarget.getAttack();
        gameController.effectController.checkTargetEffects();
        if (damage > 0) {
            gameController.increaseOpponentLp(-damage);
            if (target.getName().equals(Effect.MARSHMALLON.toString())) {
                attackedCards.add(attacker);
                return String.format("opponent monster was Marshmallon and opponent received %d damage", damage);
            }
            gameController.gameBoard.sendCardFromMonsterZoneToGraveyard(number, gameController.getOpponentPlayerNumber());
            attackedCards.add(attacker);
            return String.format("opponent monster destroyed and opponent received %d damage", damage);
        } else if (damage == 0) {
            if (target.getName().equals(Effect.MARSHMALLON.toString()) && attacker.getName().equals(Effect.MARSHMALLON.toString())) {
                return "both cards was Marshmallon";
            }
            if (target.getName().equals(Effect.MARSHMALLON.toString())) {
                gameController.gameBoard.sendCardFromMonsterZoneToGraveyard(gameController.selectedCard.getIndex(), gameController.getCurrentPlayerNumber());
                return "Marshmallon was attacked and your card is destroyed";
            }
            if (attacker.getName().equals(Effect.MARSHMALLON.toString())) {
                gameController.gameBoard.sendCardFromMonsterZoneToGraveyard(number, gameController.getOpponentPlayerNumber());
                return "opponent card destroyed";
            }
            gameController.gameBoard.sendCardFromMonsterZoneToGraveyard(number, gameController.getOpponentPlayerNumber());
            gameController.gameBoard.sendCardFromMonsterZoneToGraveyard(gameController.selectedCard.getIndex(), gameController.getCurrentPlayerNumber());
            return "both you and your opponent received no damage and both cards are destroyed";
        } else {
            gameController.increasePlayerLp(damage);
            if (attacker.getName().equals(Effect.MARSHMALLON.toString())) {
                return String.format("your monster card is Marshmallon and you receive %d damage", -damage);
            }
            gameController.gameBoard.sendCardFromMonsterZoneToGraveyard(gameController.selectedCard.getIndex(), gameController.getCurrentPlayerNumber());
            return String.format("your monster card is destroyed and you receive %d damage", -damage);
        }
    }

    protected String directAttack() throws Exception {
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
        if (attackedCards.contains(gameController.selectedCard.getCard())) {
            throw new AlreadyAttacked();
        }
        if (!gameController.getGameBoard().isMonsterZoneEmpty(gameController.getOpponentPlayerNumber())) {
            throw new DirectAttackError();
        }
        attacker = (MonsterCard) gameController.selectedCard.getCard();
        ZoneSlot zoneSlotAttacker = gameController.gameBoard.getZoneSlotByCard(attacker);
        gameController.createChain();
        gameController.chainController.chain.run();
        if (gameController.getState() != State.ATTACK || gameController.effectController.isSwordOfRevealingLight()) {
            return "battle negated";
        }
        gameController.decreasePlayerLP(gameController.getOpponentPlayerNumber(), zoneSlotAttacker.getAttack());
        attackedCards.add(attacker);
        return String.format("you opponent receives %d battle damage", zoneSlotAttacker.getAttack());
    }

    public int getAttackerNumber() {
        return gameController.gameBoard.getOwnerOfCard(attacker);
    }

    public int getTargetNumber() {
        return gameController.gameBoard.getOwnerOfCard(target);
    }
}
