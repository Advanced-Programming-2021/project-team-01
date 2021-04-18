package controller.exceptions;

public class UsernameNotExists extends Exception {
    public UsernameNotExists() {
        super("there is no player with this username");
    }
}