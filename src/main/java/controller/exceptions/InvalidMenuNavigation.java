package controller.exceptions;

public class InvalidMenuNavigation extends Exception {
    public InvalidMenuNavigation() {
        super("menu navigation is not possible");
    }
}