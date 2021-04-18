package controller.exceptions;

public class ActivationPhaseError extends Exception {
    public ActivationPhaseError() {
        super("action not allowed in this phase");
    }
}