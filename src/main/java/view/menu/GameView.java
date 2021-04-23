package view.menu;

import controller.GameController;
import view.ConsoleCommands;

import java.util.regex.Matcher;

class GameView {

    public void run(String input) {
        Matcher matcher;
        if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.SELECT_PLAYER_CARD, input)) != null) {
            selectPlayerCard(matcher);
        } else if ((matcher = ConsoleCommands.getMatcher(ConsoleCommands.DESELECT_CARD, input)) != null) {
            deselectCard(matcher);
        } else {
            System.out.println("invalid command");
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

    private void deselectCard(Matcher matcher) {
        try {
            GameController.getInstance().deselect();
            System.out.println("card selected");
        } catch (Exception exp) {
            System.err.println(exp.getMessage());
        }
    }

    private void standByPhase(Matcher matcher) {

    }

    private void drawPhase(Matcher matcher) {

    }

    private void mainPhase(Matcher matcher) {

    }

    private void endPhase(Matcher matcher) {

    }

    private void summonCard(Matcher matcher) {

    }

    private void setMonster(Matcher matcher) {

    }

    private void changeCardPosition(Matcher matcher) {

    }

    private void flipSummonCard(Matcher matcher) {

    }

    private void attackOpponentsMonster(Matcher matcher) {

    }

    private void directAttack(Matcher matcher) {

    }

    private void activateSpellCard(Matcher matcher) {

    }

    private void setSpell(Matcher matcher) {

    }

    private void setTrap(Matcher matcher) {

    }

    private void showGraveyard(Matcher matcher) {

    }

    private void back(Matcher matcher) {

    }

    private void showCard(Matcher matcher) {

    }

    private void surrender(Matcher matcher) {

    }
}
