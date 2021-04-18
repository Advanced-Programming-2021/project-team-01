package controller.exceptions;

public class InvalidRoundNumber extends Exception {
    public InvalidRoundNumber() {
        super("number of rounds is not supported");
    }
}