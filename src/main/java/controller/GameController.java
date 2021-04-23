package controller;

import model.Battle;
import model.Board;
import model.card.Card;


public class GameController {
    private static GameController instance = null;
    private Card selectedCard;
    private Battle battle;
    private Board board;
    private boolean isAI;


    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    private GameController() {

    }

    public void startGame(String username, int numberOfRounds) {

    }

    public void selectPlayerCard(String fieldType) {

    }

    public void selectPlayerCard(String fieldType, int fieldNumber) {

    }

    public void selectOpponentCard(String fieldType) {

    }

    public void selectOpponentCard(String fieldType, int fieldNumber) {

    }

    public void deselect() {

    }

    public void setCard() {

    }

    public void setMonster() {

    }

    public void setSpell() {

    }

    public void goNextPhase() {

    }

    public void summon() {

    }

    public void changeCardPosition(String newPosition) {

    }

    public void flipSummon() {

    }

    public void attack(int number) {

    }

    public void directAttack() {

    }

    public void activateEffect() {

    }

    public void ritualSummon() {

    }

    public void showGraveyard() {

    }

    public void showSelectedCard() {

    }

    public void surrender() {

    }
}
