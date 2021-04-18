package controller.exceptions;

public class CardNotInPosition extends Exception {
    public CardNotInPosition() {
        super("no card found in the given position");
    }
}