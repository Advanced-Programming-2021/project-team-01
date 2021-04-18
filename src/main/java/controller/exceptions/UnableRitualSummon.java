package controller.exceptions;

public class UnableRitualSummon extends Exception {
    public UnableRitualSummon() {
        super("there is no way you could ritual summon a monster");
    }
}