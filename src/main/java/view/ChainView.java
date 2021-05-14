package view;

import controller.ChainController;
import controller.GameController;
import model.Player;
import view.menu.GameView;
import view.menu.HandleRequestType;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ChainView {
    public void run(String input) throws Exception {
        Matcher matcher;
        if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.SELECT_PLAYER_CARD_CHAIN, input)) != null) {
            selectPlayerCard(matcher);
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.DESELECT_CARD, input)) != null) {
            deselectCard();
        } else if ((ConsoleCommands.getMatcher(ConsoleCommands.SUMMON_MONSTER, input)) != null) {
            forbiddenCommand();
        } else if ((ConsoleCommands.getMatcher(ConsoleCommands.SET_CARD, input)) != null) {
            forbiddenCommand();
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.CHANGE_MONSTER_POSITION, input)) != null) {
            forbiddenCommand();
        } else if ((ConsoleCommands.getMatcher(ConsoleCommands.FLIP_SUMMON, input)) != null) {
            forbiddenCommand();
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.ATTACK, input)) != null) {
            forbiddenCommand();
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.DIRECT_ATTACK, input)) != null) {
            forbiddenCommand();
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.ACTIVATE_EFFECT, input)) != null) {
            activateSpellCard();
        } else if ((ConsoleCommands.getMatcher(ConsoleCommands.SHOW_GRAVEYARD, input)) != null) {
            forbiddenCommand();
        } else if ((ConsoleCommands.getMatcher(ConsoleCommands.SHOW_SELECTED_CARD, input)) != null) {
            showCard();
        }else if ((ConsoleCommands.getMatcher(ConsoleCommands.SURRENDER, input)) != null) {
            forbiddenCommand();
        } else if ((ConsoleCommands.getMatcher(ConsoleCommands.NEXT_PHASE, input)) != null) {
            forbiddenCommand();
        } else if (input.equals("css")) {
            showCard();
        } else if (input.equals("sb")) {
            GameController.getInstance().getGameBoard().showBoard();
        } else {
            System.out.println("invalid command");
        }
    }

    public void start() throws Exception {
        Scanner scanner = HandleRequestType.scanner;
        String input = "";
        while (true){
            if (scanner.hasNext())
                input = scanner.nextLine();
            if (input.equals("back")){
                GameController.getInstance().getChainController().back();
                break;
            }
            run(input);
        }
    }

    private void forbiddenCommand(){
        System.out.println("it’s not your turn to play this kind of moves");
    }

    private void selectPlayerCard(Matcher matcher) {
        String type = matcher.group("type");
        try {
            if ("field".equals(type)) {
                GameController.getInstance().selectPlayerCard(type);
            } else {
                int number = Integer.parseInt(matcher.group("number"));
                GameController.getInstance().selectPlayerCard(type, number);
            }
            System.out.println("card selected");
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
        }
    }

    private void deselectCard() {
        try {
            GameController.getInstance().deselect();
            System.out.println("card deselected");
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
        }
    }

    private void activateSpellCard() {
        try {
            GameController.getInstance().getChainController().activeEffect();
            System.out.println("spell/trap activated");
        } catch (Exception error) {
            System.err.println(error.getMessage());
        }
    }

    private void showCard() {
        try {
            String message = GameController.getInstance().showSelectedCard();
            System.out.println(message);
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
        }
    }

    public static void printTurn(Player player){
        System.out.println("now it will be "+player.getUsername()+"'s turn");
    }


}
