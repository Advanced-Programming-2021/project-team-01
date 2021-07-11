import Network.Client.Client;
import controller.DatabaseController;
import controller.GameController;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import view.MyAlert;
import view.View;
import view.ViewSwitcher;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        DatabaseController.loadGameCards();
        GameController.getInstance();
        Client.setInstance(12345);
        ViewSwitcher.setStage(primaryStage);
        ViewSwitcher.switchTo(View.LOGIN);
    }
}
