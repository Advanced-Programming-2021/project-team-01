package controller.exceptions;

public class NotChangablePosition extends Exception {
    public NotChangablePosition() {
        super("you can’t change this card position");
    }
}