package view;

import controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
        Scene scene = null;
        try {
            fxmlLoader = new FXMLLoader(Objects.requireNonNull(ViewSwitcher.class.getResource(view.getFileName())));
            if (view == View.GAME_VIEW)
                fxmlLoader.setController(GameView.getInstance());
            root = fxmlLoader.load();
            scene = new Scene(root);
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
                    scene.setOnKeyPressed(event -> {
                        if(event.getCode() == KeyCode.ESCAPE){
                            GameView.getInstance().setupEscPressed();
                        }
                    });
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert root != null;
        stage.setScene(scene);
        stage.setMaxWidth(GameView.WIDTH);
        stage.setMaxHeight(GameView.HEIGHT);
        stage.setResizable(false);
        stage.show();
    }

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public static Stage getStage() {
        return stage;
    }
}
