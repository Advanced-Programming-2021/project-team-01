package controller.exceptions;

public class NotAllowedAction extends Exception {
    public NotAllowedAction(String message) {
        super(message);
    }
}