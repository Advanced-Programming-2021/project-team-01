package controller.exceptions;

public class NoActiveDeck extends Exception {
    public NoActiveDeck(String username) {
        super(String.format("%s has no active deck", username));
    }
}