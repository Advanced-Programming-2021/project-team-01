package controller.exceptions;

public class DeckNotExists extends Exception {
    public DeckNotExists(String message) {
        super(message);
    }
}