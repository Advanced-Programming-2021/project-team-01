package controller.exceptions;

public class AlreadySummonedError extends Exception {
    public AlreadySummonedError() {
        super("you already summoned/set on this turn");
    }
}