package controller.exceptions;

public class NotSpellCard extends Exception {
    public NotSpellCard() {
        super("activate effect is only for spell cards.");
    }
}