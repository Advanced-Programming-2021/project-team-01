package controller.exceptions;

public class AlreadyChangedPosition extends Exception {
    public AlreadyChangedPosition() {
        super("you already changed this card position in this turn");
    }
}