package controller.exceptions;

public class NotSummonCard extends Exception {
    public NotSummonCard() {
        super("you can’t summon this card");
    }
}