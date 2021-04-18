package controller.exceptions;

public class NotMonsterCard extends Exception {
    public NotMonsterCard() {
        super("you can’t attack with this card");
    }
}