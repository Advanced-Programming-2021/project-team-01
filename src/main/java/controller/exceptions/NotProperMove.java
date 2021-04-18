package controller.exceptions;

public class NotProperMove extends Exception {
    public NotProperMove() {
        super("it’s not your turn to play this kind of moves");
    }
}