package controller.exceptions;

public class NotPreparedForActivation extends Exception {
    public NotPreparedForActivation() {
        super("preparations of this spell are not done yet");
    }
}