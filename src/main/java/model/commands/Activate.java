package model.commands;

public interface Activate {
    void run() throws Exception;

    void runContinuous() throws Exception;
    boolean canActivate() throws Exception;
}
