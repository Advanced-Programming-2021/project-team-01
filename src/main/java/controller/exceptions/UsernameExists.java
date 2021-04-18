package controller.exceptions;

public class UsernameExists extends Exception {
    public UsernameExists(String username) {
        super(String.format("user with username %s already exists", username));
    }
}