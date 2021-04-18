package controller.exceptions;

public class DirectAttackError extends Exception{
    public DirectAttackError() {
        super("you can’t attack the opponent directly");
    }
}
