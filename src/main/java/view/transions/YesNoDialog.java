package view.transions;

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

    public YesNoDialog(String prompt) {
        Pane pane = new Pane();
        BackgroundImage backgroundimage = new BackgroundImage(new Image(getClass().getResource("/Assets/50061.png").toExternalForm()),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(450, 180, false, false, false, false));
        pane.setBackground(new Background(backgroundimage));
        pane.setPrefWidth(450);
        pane.setPrefHeight(180);
        pane.setBackground(new Background(backgroundimage));
        getDialogPane().setContent(pane);
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.TRANSPARENT);
        getDialogPane().getStylesheets().add(getClass().getResource("/view/game.css").toExternalForm());

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setPrefWidth(80);
        yesButton.setPrefHeight(40);
        yesButton.setOnMouseClicked(event -> {
            setResult(true);
            close();
        });

        noButton.setPrefWidth(80);
        noButton.setPrefHeight(40);
        noButton.setOnMouseClicked(event -> {
            setResult(false);
            close();
        });

        Label label = new Label(prompt);
        label.setTextFill(Color.WHITE);

        label.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        label.setAlignment(Pos.CENTER);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(noButton);

        Region region = new Region();
        region.setPrefWidth(80);
        hBox.getChildren().add(region);

        hBox.getChildren().add(yesButton);


        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);

        Region region2 = new Region();
        region2.setPrefHeight(40);

        vBox.getChildren().addAll(label, region2, hBox);
        vBox.setPrefWidth(450);
        vBox.setPrefHeight(180);

        pane.getChildren().addAll(vBox);

    }

}
