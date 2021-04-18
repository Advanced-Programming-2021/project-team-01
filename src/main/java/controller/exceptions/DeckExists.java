package controller.exceptions;

public class DeckExists extends Exception {
    public DeckExists(String name) {
        super(String.format("deck with name %s already exists", name));
    }
}