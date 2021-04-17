package controller.exceptions;

public class CardNotInHand extends Exception {
    public CardNotInHand(String message) {
        super(message);
    }
}