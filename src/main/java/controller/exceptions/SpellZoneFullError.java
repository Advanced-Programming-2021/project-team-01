package controller.exceptions;

public class SpellZoneFullError extends Exception {
    public SpellZoneFullError() {
        super("spell card zone is full");
    }
}