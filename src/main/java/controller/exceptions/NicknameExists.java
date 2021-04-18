package controller.exceptions;

public class NicknameExists extends Exception{
    public NicknameExists(String nickname) {
        super(String.format("user with nickname %s already exists", nickname));
    }
}
