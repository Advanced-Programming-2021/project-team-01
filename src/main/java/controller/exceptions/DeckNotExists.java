package controller.exceptions;

public class DeckNotExists extends Exception {
    public DeckNotExists(String name) {
        super(String.format("deck with name %s does not exist", name));
    }
}