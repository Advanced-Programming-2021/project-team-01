package controller.exceptions;

public class NotEnoughMoney extends Exception {
    public NotEnoughMoney() {
        super("not enough money");
    }
}