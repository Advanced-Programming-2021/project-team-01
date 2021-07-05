import controller.DatabaseController;
import controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;
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
        ViewSwitcher.setStage(primaryStage);
        ViewSwitcher.switchTo(View.SHOP);
    }
}
