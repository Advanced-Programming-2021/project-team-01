package controller.exceptions;

public class AlreadyAttacked extends Exception {
    public AlreadyAttacked(String message) {
        super(message);
    }
}