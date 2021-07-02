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
    GAME_VIEW("GameView.fxml");


    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}