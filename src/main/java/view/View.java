package view;

public enum View {
    LOGIN("Login.fxml"),
    MAIN("Main.fxml"),
    PROFILE("profile.fxml"),
    SCOREBOARD("Scoreboard.fxml"),
    IMPORTEXPORT("ImportExport.fxml"),
    SHOP("shop.fxml"),
    DECK("Deck.fxml"),
    PRE_DECK("PreDeck.fxml"),
    PRE_GAME("GamePreview.fxml"),
    GAME_VIEW("GameView.fxml"),
    CARD_CREATOR("");

    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}