package controller;

import controller.exceptions.*;
import model.GamePhase;
import model.card.Card;
import model.card.CardLocation;
import model.card.MonsterCard;

import java.util.ArrayList;

public class AttackController {
    GameController gameController;
    ArrayList<Card> attackedCards = new ArrayList<>();

    public AttackController(GameController gameController) {
        this.gameController = gameController;
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
        MonsterCard target = (MonsterCard) gameController.gameBoard.getCard("monster", gameController.getOpponentPlayerNumber(),number);
        MonsterCard attacker = (MonsterCard) gameController.selectedCard.getCard();
        if (target == null){
            throw new NoCardToAttack();
        }
        if (gameController.gameBoard.getZoneSlotByLocation(CardLocation.MONSTER,number,
                gameController.getOpponentPlayerNumber()).toString().equals("OO")){
            int damage = attacker.getAttack() - target.getAttack();
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
        return "yohohohohoho";

    }
}
