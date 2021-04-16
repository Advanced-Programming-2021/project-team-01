package controller.exceptions;

public class SameOldNewPassword extends Exception {
    public SameOldNewPassword(String message) {
        super(message);
    }
}