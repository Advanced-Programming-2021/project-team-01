package model.card;

import com.google.gson.annotations.Expose;
import controller.Effect;
import model.commands.*;

import java.util.ArrayList;
import java.util.TreeMap;

public abstract class Card {
    private static final TreeMap<String, Card> allCards = new TreeMap<>();
    protected String name;
    private String description;
    private int price;
    protected String type = "type";
    @Expose(serialize = false, deserialize = false)
    protected ArrayList<Command> commands = new ArrayList<>();

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

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public void doActions() throws Exception {
        for (Command command : commands) {
            command.run();
        }
    }

    public void doContinuousActions() throws Exception {
        for (Command command : commands) {
            command.runContinuous();
        }
    }

    public boolean canActivate() throws Exception {
        for (Command command : commands) {
            if (command.canActivate()) {
                return true;
            }
        }
        return false;
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
        if (this.getName().equals(Effect.POT_OF_GREED.toString())) {
            addCommands(new PotOfGreed(this));
        } else if (this.getName().equals(Effect.RAIGEKI.toString())) {
            addCommands(new Raigeki(this));
        } else if (this.getName().equals(Effect.MYSTICAL_SPACE_TYPHOON.toString())) {
            addCommands(new MysticalSpaceTyphoon(this));
        } else if (this.getName().equals(Effect.MONSTER_REBORN.toString())) {
            addCommands(new MonsterReborn(this));
        } else if (this.getName().equals(Effect.TWIN_TWISTERS.toString())) {
            addCommands(new TwinTwisters(this));
        } else if (this.getName().equals(Effect.HARPIES_FEATHER_DUSTER.toString())) {
            addCommands(new HarpiesFeatherDuster(this));
        } else if (this.getName().equals(Effect.DARK_HOLE.toString())) {
            addCommands(new DarkHole(this));
        } else if (this.getName().equals(Effect.BLACK_PENDANT.toString())) {
            addCommands(new EquipNormal(this));
        } else if (this.getName().equals(Effect.MAGNUM_SHIELD.toString())) {
            addCommands(new EquipWarrior(this));
        } else if (this.getName().equals(Effect.UNITED_WE_STAND.toString())) {
            addCommands(new EquipNormal(this));
        } else if (this.getName().equals(Effect.SWORD_OF_DESTRUCTION.toString())) {
            addCommands(new EquipFiend(this));
        } else if (this.getName().equals(Effect.TERRAFORMING.toString()))
            addCommands(new Terrafoming(this));
        else if (this.getName().equals(Effect.UMIIRUKA.toString()) ||
                this.getName().equals(Effect.YAMI.toString()) || this.getName().equals(Effect.FOREST.toString()) ||
                this.getName().equals(Effect.CLOSED_FOREST.toString()))
            addCommands(new FieldCard(this));
        else if (this.getName().equals(Effect.CHANGE_OF_HEART.toString()))
            addCommands(new ChangeOfHeart(this));
        else if (this.getName().equals(Effect.SUPPLY_SQUAD.toString()))
            addCommands(new SupplySquad(this));
        else if (this.getName().equals(Effect.NEGATE_ATTACK.toString()))
            addCommands(new NegateAttack(this));
        else if (this.getName().equals(Effect.MIRROR_FORCE.toString()))
            addCommands(new MirrorForce(this));
        else if (this.getName().equals(Effect.MAGIC_CYLINDER.toString()))
            addCommands(new MagicCylinder(this));
        else if (this.getName().equals(Effect.MIND_CRUSH.toString()))
            addCommands(new MindCrush(this));
        else if (this.getName().equals(Effect.TORRENTIAL_TRIBUTE.toString()))
            addCommands(new TorrentialTribute(this));
        else if (this.getName().equals(Effect.TIME_SEAL.toString()))
            addCommands(new TimeSeal(this));
        else if (this.getName().equals(Effect.MAGIC_JAMMER.toString()))
            addCommands(new MagicJammer(this));
        else if (this.getName().equals(Effect.TRAP_HOLE.toString()))
            addCommands(new TrapHole(this));
        else if (this.getName().equals(Effect.CALL_OF_THE_HAUNTED.toString()))
            addCommands(new CallOfTheHaunted(this));
        else if (this.getName().equals(Effect.SOLEMN_WARNING.toString()))
            addCommands(new SolemnWarning(this));
        else if (this.getName().equals(Effect.SUIJIN.toString()))
            addCommands(new Suijin(this));
        else if (this.getName().equals(Effect.MAN_EATER_BUG.toString()))
            addCommands(new ManEaterBug(this));
        else if (this.getName().equals(Effect.RITUAL.toString()))
            addCommands(new AdvanceRitualArt(this));
        else if (this.getName().equals(Effect.TRICKY.toString()))
            addCommands(new TheTricky(this));
        else if (this.getName().equals(Effect.GATE_GUARDIAN.toString()))
            addCommands(new GateGuardian(this));
        else if (this.getName().equals(Effect.SCANNER.toString()))
            addCommands(new Scanner(this));

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setType(String trap) {
        this.type = trap;
    }
}
