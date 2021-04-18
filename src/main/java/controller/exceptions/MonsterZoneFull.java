package controller.exceptions;

public class MonsterZoneFull extends Exception {
    public MonsterZoneFull() {
        super("monster card zone is full");
    }
}