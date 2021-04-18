package controller.exceptions;

public class AlreadyAttacked extends Exception {
    public AlreadyAttacked() {
        super("this card already attacked");
    }
}