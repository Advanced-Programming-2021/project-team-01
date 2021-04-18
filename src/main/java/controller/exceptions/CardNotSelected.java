package controller.exceptions;

public class CardNotSelected extends Exception {
    public CardNotSelected() {
        super("no card is selected yet");
    }
}