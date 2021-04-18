package controller.exceptions;

public class NotEnoughTribute extends Exception {
    public NotEnoughTribute() {
        super("there are not enough cards for tribute");
    }
}