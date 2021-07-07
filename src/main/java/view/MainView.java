package view;

import controller.RegisterController;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import view.transions.Lighting;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Timer;

public class MainView {
    Pane mainPane;
    ImagePattern beneathIcon = new ImagePattern(new Image(getClass().getResource("/Assets/Main/beneath.png").toExternalForm()));
    Rectangle characterRectangle = new Rectangle(300, 500);

    public void init(Pane root) {
        mainPane = root;
        Rectangle rectangle = new Rectangle(400, 70);
        rectangle.setFill(beneathIcon);
        rectangle.setTranslateX(800);
        rectangle.setTranslateY(450);
        characterRectangle.setTranslateX(850);
        characterRectangle.setTranslateY(0);
        mainPane.getChildren().addAll(rectangle, characterRectangle);
        fadeInAndOut();
        lightingAnimation();
    }

    private void lightingAnimation() {
        Rectangle rectangle = new Rectangle(100,600, Color.TRANSPARENT);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(Duration.millis(2000),event -> {
            Lighting lighting = new Lighting(rectangle);
            lighting.play();
            lighting.setOnFinished(event1 -> {
                rectangle.setFill(Color.TRANSPARENT);
            });
        });
        mainPane.getChildren().add(rectangle);
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void fadeInAndOut() {
        ImagePattern characterImage = new ImagePattern(pickImage());
        characterRectangle.setFill(characterImage);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), characterRectangle);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), characterRectangle);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeIn.setOnFinished(event -> {
            fadeOut.play();
        });
        fadeOut.setOnFinished(event -> {
            fadeInAndOut();
        });
        fadeIn.play();
    }

    private Image pickImage() {
        Random rand = new Random(LocalDateTime.now().getSecond());
        int result = rand.nextInt();
        result = result < 0 ? result * -1 : result;
        result = (result % 15) + 1;
        return new Image(getClass().getResource("/Assets/Main/" + String.valueOf(result) + ".png").toExternalForm());
    }

    @FXML
    private void logout() {
        RegisterController.getInstance().logout();
        ViewSwitcher.switchTo(View.LOGIN);
    }

    @FXML
    private void startScoreboardView() {
        ViewSwitcher.switchTo(View.SCOREBOARD);
    }

    @FXML
    private void startImportExportView() {
        ViewSwitcher.switchTo(View.IMPORTEXPORT);
    }

    @FXML
    private void startDeckView() { ViewSwitcher.switchTo(View.PRE_DECK); }

    @FXML
    private void startShopView() {
        ViewSwitcher.switchTo(View.SHOP);
    }

    @FXML
    private void startProfileView() {
        ViewSwitcher.switchTo(View.PROFILE);
    }

    @FXML
    private void startGamePreview() { ViewSwitcher.switchTo(View.PRE_GAME); }
}
