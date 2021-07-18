package view;

import com.jfoenix.controls.JFXSlider;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import model.AdminCard;
import model.card.Card;

import java.util.Map;

public class AdminPanelView implements GraphicalView {
    public ChoiceBox<String> cardsChoiceBox;
    public Button exitButton;
    public JFXSlider slider;
    public Button decreaseButton;
    public Button increaseButton;
    public Button activateButton;

    @Override
    public void init(Pane root) {
        for (Map.Entry<String, Card> entry : Card.getAllCards().entrySet()) {
            cardsChoiceBox.getItems().add(entry.getKey());
        }
        slider.setMax(100);
        slider.setMin(0);
        activateButton.setStyle("-fx-background-color: #3c2f0b;-fx-text-fill: yellow;");
        decreaseButton.setStyle("-fx-background-color: #3c2f0b;-fx-text-fill: yellow;");
        increaseButton.setStyle("-fx-background-color: #3c2f0b;-fx-text-fill: yellow;");
        exitButton.setStyle("-fx-background-color: #3c2f0b;-fx-text-fill: yellow;");
    }

    public void exit(MouseEvent mouseEvent) {
        ViewSwitcher.switchTo(View.MAIN);
    }

    public void decreaseCardAmount(MouseEvent mouseEvent) {
        if (cardsChoiceBox.getValue() == null) {
            new MyAlert(Alert.AlertType.WARNING, "No card's selected").show();
            return;
        }
        AdminCard.decreaseCardAmount(cardsChoiceBox.getValue(), (int)slider.getValue());
    }

    public void increaseCardAmount(MouseEvent mouseEvent) {
        if (cardsChoiceBox.getValue() == null) {
            new MyAlert(Alert.AlertType.WARNING, "No card's selected").show();
            return;
        }
        AdminCard.increaseCardAmount(cardsChoiceBox.getValue(), (int)slider.getValue());
    }

    public void allowCard(MouseEvent mouseEvent) {
        if (cardsChoiceBox.getValue() == null) {
            new MyAlert(Alert.AlertType.WARNING, "No card's selected").show();
            return;
        }
        AdminCard.setAllowed(cardsChoiceBox.getValue());
    }
}
