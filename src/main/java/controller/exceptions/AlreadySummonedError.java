package controller.exceptions;

public class AlreadySummonedError extends Exception {
    public AlreadySummonedError(String message) {
        super(message);
    }
}