package controller.exceptions;

public class MainDeckIsFull extends Exception {
    public MainDeckIsFull() {
        super("main deck is full");
    }
}