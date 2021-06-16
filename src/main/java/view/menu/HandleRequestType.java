package view.menu;

import controller.GameController;
import view.ChainView;
import view.Menu;

import java.util.Scanner;

public class HandleRequestType {
    public static Scanner scanner = new Scanner(System.in);
    public static Menu currentMenu = Menu.REGISTER_MENU;
    ShopView shopView;
    DeckView deckView;
    GameView gameView;
    ChainView chainView;
    ImportExportView importExportView;
    MainMenu mainMenu;
    ScoreBoardView scoreBoardView;
    ProfileView profileView;
    RegisterView registerView;
    String command = "";

    public RegisterView getRegisterView() {
        return registerView;
    }

    public ProfileView getProfileView() {
        return profileView;
    }

    public DeckView getDeckView(){
        return deckView;
    }

    public ShopView getShopView() {
        return shopView;
    }

    public ScoreBoardView getScoreBoardView() {
        return scoreBoardView;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }


    public void start() throws Exception {
        registerView = new RegisterView();
        profileView = new ProfileView();
        scoreBoardView = new ScoreBoardView();
        shopView = new ShopView();
        deckView = new DeckView();
        gameView = new GameView();
        chainView = new ChainView();
        importExportView = new ImportExportView();
        mainMenu = new MainMenu();
        while (currentMenu != Menu.EXIT) {
            if (GameController.getCurrentPlayer() != null &&
                    GameController.getCurrentPlayer().getUsername().equals("AI")) {
                String output = GameController.getInstance().getAiBasicController().handleAiMoves();
                System.out.println(output);
                continue;
            }
            if (scanner.hasNext()) {
                command = scanner.nextLine();
            } else
                break;
            if (currentMenu == Menu.REGISTER_MENU) {
                registerView.run(command);
            } else if (currentMenu == Menu.MAIN_MENU) {
                mainMenu.run(command);
            } else if (currentMenu == Menu.DECK_MENU) {
                deckView.run(command);
            } else if (currentMenu == Menu.PROFILE_VIEW) {
                profileView.run(command);
            } else if (currentMenu == Menu.GAME_MENU) {
                gameView.run(command);
            } else if (currentMenu == Menu.IMPORT_EXPORT) {
                importExportView.run(command);
            } else if (currentMenu == Menu.SHOP) {
                shopView.run(command);
            } else if (currentMenu == Menu.SCOREBOARD) {
                scoreBoardView.run(command);
            } else if (currentMenu == Menu.CHAIN) {
                chainView.run(command);
            } else {
                throw new RuntimeException("core adaption failed!");
            }
        }
    }

}

