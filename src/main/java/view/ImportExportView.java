package view;

import controller.DatabaseController;
import controller.ImportExportController;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ImportExportView {
    public void init(Pane root) {
        HBox importHBox = new HBox(), exportHBox = new HBox();
        TextField importTextField = new TextField("Import card name:"), exportTextField = new TextField("Export card name:");
        Button importButton = new Button();
        importButton.setStyle(getClass().getResource("/view/importexport.css").toExternalForm());
        importButton.setPrefWidth(80);
        importButton.setPrefHeight(80);
        Button exportButton = new Button();
        exportButton.setStyle(getClass().getResource("/view/importexport.css").toExternalForm());
        exportButton.setPrefWidth(80);
        exportButton.setPrefHeight(80);
        importHBox.getChildren().addAll(importButton, importTextField);
        importHBox.setSpacing(10);
        exportHBox.getChildren().addAll(exportButton, exportTextField);
        exportHBox.setSpacing(10);
        importHBox.setTranslateX(70);
        importHBox.setTranslateY(200);
        exportHBox.setTranslateX(70);
        exportHBox.setTranslateY(300);
        importHBox.setAlignment(Pos.CENTER);
        exportHBox.setAlignment(Pos.CENTER);
        addEventToButton(importButton, exportButton, importTextField, exportTextField);
        root.getChildren().addAll(exportHBox, importHBox);
    }

    private void addEventToButton(Button importButton, Button exportButton, TextField importField, TextField exportField) {
        importButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    ImportExportController.importCard(importField.getText());
                    new MyAlert(Alert.AlertType.CONFIRMATION, "Card is imported successfully.").show();
                } catch (Exception expt) {
                    new MyAlert(Alert.AlertType.ERROR, "No such card exists.").show();
                }
            }
        });
        exportButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    DatabaseController.loadGameCards();
                    ImportExportController.exportCard(exportField.getText());
                    new MyAlert(Alert.AlertType.CONFIRMATION, "Card is exported successfully.").show();
                } catch (Exception expt) {
                    new MyAlert(Alert.AlertType.ERROR, "No such card exists.").show();
                }
            }
        });
    }
}
