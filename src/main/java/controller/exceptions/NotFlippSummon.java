package controller.exceptions;

public class NotFlippSummon extends Exception {
    public NotFlippSummon(String message) {
        super("you can’t flip summon this card");
    }
}