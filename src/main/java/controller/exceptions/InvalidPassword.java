package controller.exceptions;

public class InvalidPassword extends Exception {
    public InvalidPassword() {
        super("current password is invalid");
    }
}