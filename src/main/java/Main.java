import javafx.application.Application;
import javafx.stage.Stage;
import view.View;
import view.ViewSwitcher;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ViewSwitcher.setStage(primaryStage);
        ViewSwitcher.switchTo(View.PROFILE);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
