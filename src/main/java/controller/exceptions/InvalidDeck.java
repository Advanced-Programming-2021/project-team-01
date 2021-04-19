package controller.exceptions;

public class InvalidDeck extends Exception {
    public InvalidDeck(String username) {
        super(String.format("%s’s deck is invalid", username));
    }
}