import controller.DatabaseController;
import view.menu.HandleRequestType;

public class Main{
    public static void main(String[] args) {
        DatabaseController.loadGameCards();
        HandleRequestType handleRequestType = new HandleRequestType();
        handleRequestType.start();
    }
}