package controller.exceptions;

public class NotPreparedForActivation extends Exception {
    public NotPreparedForActivation(String message) {
        super(message);
    }
}