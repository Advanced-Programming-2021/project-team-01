package controller.exceptions;

public class CardNotInHand extends Exception {
    public CardNotInHand() {
        super("you can’t set this card");
    }
}