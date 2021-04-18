package controller.exceptions;

public class AlreadyInWantedPosition extends Exception {
    public AlreadyInWantedPosition() {
        super("this card is already in the wanted position");
    }
}