package controller.exceptions;

public class UnableSpecialSummon extends Exception {
    public UnableSpecialSummon() {
        super("there is no way you could special summon a monster");
    }
}