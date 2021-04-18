package controller.exceptions;

public class MonsterLevelNotMatch extends Exception {
    public MonsterLevelNotMatch() {
        super("selected monsters levels don’t match with ritual monster");
    }
}