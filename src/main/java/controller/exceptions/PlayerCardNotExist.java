package controller.exceptions;

public class PlayerCardNotExist extends Exception {
    public PlayerCardNotExist() {
        super("You don't have this card");
    }
}
