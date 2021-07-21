import Network.Client.Client;
import Network.Utils.Logger;
import controller.DatabaseController;
import controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.View;
import view.ViewSwitcher;

import java.util.Scanner;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String host = scanner.nextLine();
        int port = Integer.parseInt(scanner.nextLine());
        DatabaseController.loadGameCards();
        GameController.getInstance();
        Logger.set();
        Client.setInstance(host, port);
        ViewSwitcher.setStage (primaryStage);
        ViewSwitcher.switchTo(View.LOGIN);
    }
}
