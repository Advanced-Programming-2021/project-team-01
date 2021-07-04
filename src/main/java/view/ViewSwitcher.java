package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class ViewSwitcher {
    private static Stage stage;

    public static void switchTo(View view) {
        Pane root = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(Objects.requireNonNull(ViewSwitcher.class.getResource(view.getFileName())));
            if (view == View.GAME_VIEW)
                fxmlLoader.setController(GameView.getInstance());
            root = fxmlLoader.load();
            switch (view) {
                case SCOREBOARD: {
                    new ScoreboardView().init(root);
                    break;
                }
                case IMPORTEXPORT: {
                    new ImportExportView().init(root);
                    break;
                }
                case LOGIN: {
                    new LoginView().init(root);
                    break;
                }
                case GAME_VIEW: {
                    GameView.getInstance().init(root);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMaxWidth(GameView.WIDTH);
        stage.setMaxHeight(GameView.HEIGHT);
        stage.setResizable(false);
        stage.show();
    }

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }
}
