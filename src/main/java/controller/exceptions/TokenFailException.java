package controller.exceptions;

public class TokenFailException extends Exception {
    public TokenFailException(){
        super("auth token failed");
    }
}
