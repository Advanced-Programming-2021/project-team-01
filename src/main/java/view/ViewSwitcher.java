package view;

import controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class ViewSwitcher {
    private static Stage stage;
    private static GraphicalView currentView;

    public static void switchTo(View view) {
        Pane root = null;
        FXMLLoader fxmlLoader = null;
        Scene scene = null;
        KeyCombination cheatKeyCombination = new KeyCodeCombination(KeyCode.C,
                KeyCombination.SHIFT_DOWN, KeyCombination.CONTROL_DOWN);
        try {
            fxmlLoader = new FXMLLoader(Objects.requireNonNull(ViewSwitcher.class.getResource(view.getFileName())));
            if (view == View.GAME_VIEW)
                fxmlLoader.setController(GameView.getInstance());
            root = fxmlLoader.load();
            scene = new Scene(root);
            switch (view) {
                case SHOP: {
                    scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
                        if (cheatKeyCombination.match(event))
                            ShopView.setupCheatScene();
                    });
                }
                case SCOREBOARD: {
                    new ScoreboardView().init(root);
                    break;
                }
                case IMPORTEXPORT: {
                    new ImportExportView().init(root);
                    break;
                }
                case LOGIN: {
                    currentView = new LoginView();
                    currentView.init(root);
                    break;
                }
                case MAIN: {
                    LoginView.mediaView.getMediaPlayer().stop();
                    LoginView.mediaView.getMediaPlayer().play();
                    new MainView().init(root);
                    break;
                }
                case GAME_VIEW: {
                    LoginView.mediaView.getMediaPlayer().stop();
                    GameView.getInstance().init(root);
                    scene.setOnKeyPressed(event -> {
                        if(event.getCode() == KeyCode.ESCAPE){
                            GameView.getInstance().setupEscPressed();
                        }
                    });
                    scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
                        if (cheatKeyCombination.match(event))
                            GameView.getInstance().setupCheatScene();
                    });
                    break;
                }
                case CARD_CREATOR: {
                    new CardCreator().init(root);
                    break;
                }
                case PRE_GAME: {
                    new GamePreview().init(root);
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

    public static GraphicalView getCurrentView() {
        return currentView;
    }
}
