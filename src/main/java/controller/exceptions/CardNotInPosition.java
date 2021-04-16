package controller.exceptions;

public class CardNotInPosition extends Exception {
    public CardNotInPosition(String message) {
        super(message);
    }
}