package controller.exceptions;

public class DeckExists extends Exception {
    public DeckExists(String message) {
        super(message);
    }
}