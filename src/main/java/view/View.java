package view;

public enum View {
    LOGIN("Login.fxml"),
    MAIN("Main.fxml"),
    ABOUT("about.fxml");

    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}