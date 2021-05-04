package controller.exceptions;

public class HiddenCardError extends Exception {
    public HiddenCardError() {
        super("card is not visible");
    }
}