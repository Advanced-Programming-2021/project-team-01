package controller;

import model.Battle;
import model.Board;
import model.card.Card;


public class GameController {
    private static GameController instance = null;
    private Battle battle;

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    private GameController() {

    }

    public void startGame(String username, int numberOfRounds) {
        //TODO: IMPORTANT
    }

    public void selectPlayerCard(String fieldType) {

    }

    public void selectPlayerCard(String fieldType, int fieldNumber) {
        if (fieldType.equals("monster")){

        }else if (fieldType.equals("spell")){

        }else if (fieldType.equals("hand")){

        }
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
