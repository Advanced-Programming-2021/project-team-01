package controller.exceptions;

public class NotRightTurnForActivasion extends Exception {
    public NotRightTurnForActivasion() {
        super("you can’t activate an effect on this turn");
    }
}