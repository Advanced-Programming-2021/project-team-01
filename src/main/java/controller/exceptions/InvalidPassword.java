package controller.exceptions;

public class InvalidPassword extends Exception {
    public InvalidPassword(String message) {
        super(message);
    }
}