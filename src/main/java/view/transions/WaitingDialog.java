package view.transions;

import Network.Responses.Battle.ActivateChainResponse;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class WaitingDialog extends Dialog<Boolean> {
    BorderPane pane;
    public Boolean boroGomsho = Boolean.FALSE;

    public WaitingDialog() {
        setX(0);
        pane = new BorderPane();
        BackgroundImage backgroundimage = new BackgroundImage(new Image(getClass().getResource("/Assets/50061.png").toExternalForm()),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(500, 180, false, false, false, false));
        pane.setBackground(new Background(backgroundimage));
        pane.setPrefWidth(450);
        pane.setPrefHeight(180);
        pane.setBackground(new Background(backgroundimage));
        getDialogPane().setContent(pane);
        initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.TRANSPARENT);
        getDialogPane().getStylesheets().add(getClass().getResource("/view/game.css").toExternalForm());

        Text text = new Text("Wait for your opponent...");
        text.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
        text.setWrappingWidth(400);
        text.setFill(Color.WHITE);
        text.setTranslateX(+15);
        pane.setCenter(text);

        Button ok = new Button("ok");

        ok.setPrefWidth(80);
        ok.setPrefHeight(40);
        ok.setTranslateX(175);
        ok.setOnMouseClicked(event -> {
            System.out.println(boroGomsho.toString());
            if (boroGomsho){
                setResult(true);
                close();
            }
        });


        Image image;
        ImageView imageView;
        image = new Image("Assets/info.png");
        imageView = new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setTranslateY(50);
        imageView.setTranslateX(30);
        imageView.setFitWidth(80);
        pane.setLeft(imageView);
        pane.setBottom(ok);
    }

    public void setBoroGomsho(Boolean boroGomsho) {
        this.boroGomsho = boroGomsho;
    }
}
