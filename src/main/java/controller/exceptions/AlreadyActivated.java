package controller.exceptions;

public class AlreadyActivated extends Exception {
    public AlreadyActivated(String message) {
        super(message);
    }
}