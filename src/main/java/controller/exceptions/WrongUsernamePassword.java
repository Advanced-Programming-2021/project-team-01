package controller.exceptions;

public class WrongUsernamePassword extends Exception {
    public WrongUsernamePassword() {
        super("Username and password didn’t match!");
    }
}