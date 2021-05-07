package model.card;

import com.google.gson.annotations.Expose;
import controller.Spell;
import model.commands.*;

import java.util.ArrayList;
import java.util.TreeMap;

public abstract class Card {
    private static TreeMap<String, Card> allCards = new TreeMap<>();
    protected String type = "type";
    @Expose(serialize = false, deserialize = false)
    protected ArrayList<Command> commands = new ArrayList<>();
    private String name;
    private String description;
    private int price;

    public Card(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public static Card getCardByName(String name) {
        if (allCards.containsKey(name)) {
            return allCards.get(name);
        }
        return null;
    }

    public static TreeMap<String, Card> getAllCards() {
        return allCards;
    }

    public static void addCardToDatabase(Card card) {
        allCards.put(card.getName(), card);
    }

    public void doActions() throws Exception {
        for (Command command : commands) {
            command.run();
        }
    }

    public void addCommands(Command command) {
        if (commands == null) {
            commands = new ArrayList<>();
        }
        commands.add(command);
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void addCommandsToCard() {
        if (this.getName().equals(Spell.POT_OF_GREED.toString())) {
            addCommands(new PotOfGreed());
        } else if (this.getName().equals(Spell.MONSTER_REBORN.toString())) {
            addCommands(new MonsterReborn());
        } else if (this.getName().equals(Spell.BLACK_PENDANT.toString())) {
            addCommands(new EquipNormal());
        } else if (this.getName().equals(Spell.MAGNUM_SHIELD.toString())){
            addCommands(new EquipWarrior());
        } else if (this.getName().equals(Spell.UNITED_WE_STAND.toString())){
            addCommands(new EquipNormal());
        } else if (this.getName().equals(Spell.SWORD_OF_DESTRUCTION.toString())){
            addCommands(new EquipFiend());
        }
    }
}
