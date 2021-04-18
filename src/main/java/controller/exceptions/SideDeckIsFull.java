package controller.exceptions;

public class SideDeckIsFull extends Exception {
    public SideDeckIsFull() {
        super("side deck is full");
    }
}