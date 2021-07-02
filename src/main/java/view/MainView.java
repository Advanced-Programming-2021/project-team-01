package view;

import controller.RegisterController;
import javafx.fxml.FXML;

public class MainView {
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
