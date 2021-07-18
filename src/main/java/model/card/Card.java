package model.card;

import Network.Requests.SkipSerialisation;
import com.google.gson.annotations.Expose;
import controller.Effect;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.commands.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public abstract class Card {
    @SkipSerialisation
    private transient Image cardImage;
    @SkipSerialisation
    private transient static TreeMap<String, Card> allCards = new TreeMap<>();
    protected String name;
    private String description;
    protected String type = "type";
    private int price;
    @SkipSerialisation
    protected transient ArrayList<Command> commands = new ArrayList<>();
    @SkipSerialisation
    static transient HashMap<String, Image> cachedImage = new HashMap<>();

    public Card(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
        addImage();
        cardImage = cachedImage.get(buildImage());
    }

    public static void setAllcard(TreeMap temp) {
        allCards = temp;
    }


    public ImagePattern getCardImage() {
        if (cardImage == null)
            cardImage = cachedImage.get(buildImage());
       return new ImagePattern(cardImage);
    }

    public ImagePattern getBackImage() {
        Image image = new Image(getClass().getResource("/Cards/unknown.jpg").toExternalForm());
        return new ImagePattern(image);
    }

    public void addImage() {
        String imageName = buildImage();
        if (cachedImage.containsKey(imageName)) return;
        Image image = null;
        try {
            image = new Image(getClass().getResource("/Cards/" + imageName).toExternalForm());
        } catch (Exception e) {
            image = new Image(getClass().getResource("/Cards/custom.jpg").toExternalForm());
        }
        cachedImage.put(imageName, image);
    }

    private String buildImage() {
        String[] parts = name.split(" ");
        for (String part : parts) { //fixme for is not object
            StringBuilder temp = new StringBuilder(part);
            temp.setCharAt(0, Character.toUpperCase(part.charAt(0)));
            part = temp.toString();
        }
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            result.append(part);
        }
        result.append(".jpg");
        return result.toString();
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
        if (commands.size() == 1)
            return;
        commands.add(command);
    }

    public void addCommands() {
        if (commands == null)
            commands = new ArrayList<>();
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
        else if (this.getName().equals(Effect.TERATIGER.toString()))
            addCommands(new TerraTiger(this));
        else if (this.getName().equals(Effect.TEXCHANGER.toString()))
            addCommands(new Texchanger(this));
        else if (this.getName().equals(Effect.HERALD_OF_CREATION.toString()))
            addCommands(new HeraldOfCreation(this));
        else if (this.getName().equals(Effect.SPELL_ABSORPTION.toString()))
            addCommands(new SpellAbsorption(this));
        else if (this.getName().equals(Effect.SWORDS_OF_REVEALING_LIGHT.toString()))
            addCommands(new SwordOfRevealingLight(this));
        else if (this.getName().equals(Effect.MESSENGER_OF_PEACE.toString()))
            addCommands(new MessengerOfPeace(this));
        else addCommands();
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
