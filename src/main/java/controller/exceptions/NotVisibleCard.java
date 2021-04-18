package controller.exceptions;

public class NotVisibleCard extends Exception {
    public NotVisibleCard() {
        super("card is not visible");
    }
}