package controller.exceptions;

public class WrongUsernamePassword extends Exception {
    public WrongUsernamePassword(String message) {
        super(message);
    }
}