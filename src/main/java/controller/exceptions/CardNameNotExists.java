package controller.exceptions;

public class CardNameNotExists extends Exception {
    public CardNameNotExists(String name) {
        super(String.format("card with name %s does not exist", name));
    }
}