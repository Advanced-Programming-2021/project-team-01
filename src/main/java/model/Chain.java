package model;

import model.commands.Command;

import java.util.ArrayList;

public class Chain {
    private ArrayList<Command> commands;

    public Chain() {
        commands = new ArrayList<>();
    }

    public void run() throws Exception {
        for (Command command : commands) {
            command.run();
        }
    }

    public void setNext(Command command) {
        commands.add(command);
    }
}
