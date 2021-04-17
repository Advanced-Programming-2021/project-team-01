package controller.exceptions;

public class UsernameExists extends Exception {
    public UsernameExists(String message) {
        super(message);
    }
}