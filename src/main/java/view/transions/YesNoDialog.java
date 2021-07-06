package view.transions;

import com.jfoenix.controls.JFXDialog;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.StageStyle;


public class YesNoDialog extends Dialog<Boolean> {

    public YesNoDialog(String prompt){
        Pane pane = new Pane();
        BackgroundImage backgroundimage = new BackgroundImage(new Image(getClass().getResource("/Assets/50061.png").toExternalForm()),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(450, 180, false, false, false, false));        pane.setBackground(new Background(backgroundimage));
        pane.setPrefWidth(450);
        pane.setPrefHeight(180);
        pane.setBackground(new Background(backgroundimage));
        getDialogPane().setContent(pane);
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.TRANSPARENT);
        getDialogPane().getStylesheets().add(getClass().getResource("/view/game.css").toExternalForm());

        Button yesButton = new Button("yes");
        Button noButton = new Button("no");

        yesButton.setTranslateY(100);
        yesButton.setTranslateX(300);
        yesButton.setPrefWidth(50);
        yesButton.setPrefHeight(20);
        yesButton.setOnMouseClicked(event -> {
            setResult(true);
            close();
        });

        noButton.setTranslateY(100);
        noButton.setTranslateX(100);
        noButton.setPrefWidth(50);
        noButton.setPrefHeight(20);
        noButton.setOnMouseClicked(event -> {
            setResult(false);
            close();
        });

        Label label = new Label(prompt);

        label.setTranslateY(50);
        label.setTranslateX(180);

        label.setTextFill(Color.WHITE);

        label.setFont(Font.font("Tahoma",FontWeight.BOLD,20));
        label.setAlignment(Pos.CENTER);

        pane.getChildren().addAll(yesButton, noButton, label);

    }

}
