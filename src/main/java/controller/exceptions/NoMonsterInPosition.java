package controller.exceptions;

public class NoMonsterInPosition extends Exception {
    public NoMonsterInPosition() {
        super("there no monsters on this address");
    }
}