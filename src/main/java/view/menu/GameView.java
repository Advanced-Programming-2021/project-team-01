package view.menu;

import controller.GameController;
import controller.exceptions.LevelFiveException;
import controller.exceptions.LevelSevenException;
import model.GamePhase;
import view.ConsoleCommands;
import view.Menu;

import java.util.Scanner;
import java.util.regex.Matcher;

class GameView {

    public void run(String input) {
        Matcher matcher;
        if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.SELECT_PLAYER_CARD, input)) != null) {
            selectPlayerCard(matcher);
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.DESELECT_CARD, input)) != null) {
            deselectCard();
        } else if ((ConsoleCommands.getMatcher(ConsoleCommands.SUMMON_MONSTER, input)) != null) {
            summonCard();
        } else if ((ConsoleCommands.getMatcher(ConsoleCommands.SET_CARD, input)) != null) {
            setCard();
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.CHANGE_MONSTER_POSITION, input)) != null) {
            changeCardPosition(matcher);
        } else if ((ConsoleCommands.getMatcher(ConsoleCommands.FLIP_SUMMON, input)) != null) {
            flipSummonCard();
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.ATTACK, input)) != null) {
            attackOpponentsMonster(matcher);
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.DIRECT_ATTACK, input)) != null) {
            directAttack();
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.ACTIVATE_EFFECT, input)) != null) {
            activateSpellCard();
        } else if ((ConsoleCommands.getMatcher(ConsoleCommands.SHOW_GRAVEYARD, input)) != null) {
            showGraveyard();
        } else if ((ConsoleCommands.getMatcher(ConsoleCommands.SHOW_SELECTED_CARD, input)) != null) {
            showCard();
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.SHOW_GRAVEYARD, input)) != null) {
            directAttack();
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.SHOW_GRAVEYARD, input)) != null) {
            directAttack();
        } else if ((ConsoleCommands.getMatcher(ConsoleCommands.SURRENDER, input)) != null) {
            surrender();
            HandleRequestType.currentMenu = Menu.MAIN_MENU;
        } else if ((ConsoleCommands.getMatcher(ConsoleCommands.NEXT_PHASE, input)) != null){
            System.out.println(GameController.getInstance().nextPhase());
        } else {
            System.out.println("invalid command");
            return;
        }
        if (GameController.getInstance().getGamePhase() == GamePhase.MAIN_PHASE1){
            GameController.getInstance().getGameBoard().showBoard();
        }
    }

    private void selectPlayerCard(Matcher matcher) {
        String type = matcher.group("type");
        try {
            if (matcher.group("opponent") == null) {
                if ("field".equals(type)) {
                    GameController.getInstance().selectPlayerCard(type);
                } else {
                    int number = Integer.parseInt(matcher.group("number"));
                    GameController.getInstance().selectPlayerCard(type, number);
                }
            } else {
                if ("field".equals(type)) {
                    GameController.getInstance().selectOpponentCard(type);
                } else {
                    int number = Integer.parseInt(matcher.group("number"));
                    GameController.getInstance().selectOpponentCard(type, number);
                }
            }
            System.out.println("card selected");
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
        }
    }

    private void deselectCard() {
        try {
            GameController.getInstance().deselect();
            System.out.println("card selected");
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
        }
    }

    private void summonCard() {
        try {
            GameController.getInstance().summon();
            System.out.println("summoned successfully");
        }catch (LevelFiveException exception){
            String input = HandleRequestType.scanner.nextLine();
            if (input.equals("cancel")) return;
            int index = Integer.parseInt(input);
            try {
                GameController.getInstance().tributeSummonLevel5(index);
                System.out.println("summoned successfully");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }catch (LevelSevenException levelSevenException){
            String input = HandleRequestType.scanner.nextLine();
            if (input.equals("cancel")) return;
            int index1 = Integer.parseInt(input);
            int index2 = Integer.parseInt(HandleRequestType.scanner.nextLine());
            try {
                GameController.getInstance().tributeSummonLevel7(index1,index2);
                System.out.println("summoned successfully");
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
        }
    }

    private void setCard() {
        try {
            GameController.getInstance().setCard();
            System.out.println("set successfully");
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
        }
    }

    private void changeCardPosition(Matcher matcher) {
        try {
            String newPosition = matcher.group("position");
            GameController.getInstance().changeCardPosition(newPosition);
            System.out.println("monster card position changed successfully");
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
        }
    }

    private void flipSummonCard() {
        try {
            GameController.getInstance().flipSummon();
            System.out.println("flip summoned successfully");
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
        }
    }

    private void attackOpponentsMonster(Matcher matcher) {
        try {
            int number = Integer.parseInt(matcher.group("number"));
            String response = GameController.getInstance().attack(number);
            System.out.println(response);
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
        }
    }

    private void directAttack() {
        try {
            GameController.getInstance().directAttack();
            //TODO: print success message
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
        }
    }

    private void activateSpellCard() {
        try {
            GameController.getInstance().activateEffect();
            System.out.println("spell activated");
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
        }
    }

    private void showGraveyard() {
        try {
            System.out.print(GameController.getInstance().showGraveyard());
            back();
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
        }
    }

    private void back() {
        String command;
        while (true) {
            command = HandleRequestType.scanner.nextLine();
            if (command.equals("back"))
                break;
            else
                System.out.println("invalid command!");
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

    private void surrender() {
        try {
            GameController.getInstance().surrender();
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
        }
    }
}
