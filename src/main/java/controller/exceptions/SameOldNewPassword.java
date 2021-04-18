package controller.exceptions;

public class SameOldNewPassword extends Exception {
    public SameOldNewPassword() {
        super("please enter a new password");
    }
}