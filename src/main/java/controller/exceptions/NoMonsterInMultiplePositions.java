package controller.exceptions;

public class NoMonsterInMultiplePositions extends Exception{
    public NoMonsterInMultiplePositions() {
        super("there is no monster on one of these addresses");
    }
}
