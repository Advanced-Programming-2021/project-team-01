package controller.exceptions;

public class NotFlippSummon extends Exception {
    public NotFlippSummon() {
        super("you can’t flip summon this card");
    }
}