package controller.exceptions;

public class NotAllowedAction extends Exception {
    public NotAllowedAction() {
        super("you can’t do this action in this phase");
    }
}