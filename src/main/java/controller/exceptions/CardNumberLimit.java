package controller.exceptions;

public class CardNumberLimit extends Exception {
    public CardNumberLimit(String message) {
        super(message);
    }
}