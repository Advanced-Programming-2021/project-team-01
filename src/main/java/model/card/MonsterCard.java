package model.card;

import controller.GameController;

import java.util.ArrayList;

public class MonsterCard extends Card {
    int level;
    private CardType cardType;
    private ArrayList<MonsterType> monsterTypes;
    private Attribute attribute;
    private int attack;
    private int defense;

    public MonsterCard(String name, String description, int price, int attack, int defense,
                       CardType cardType, ArrayList<MonsterType> monsterTypes, Attribute attribute, int level) {
        super(name, description, price);
        super.type = "monster";
        this.attack = attack;
        this.defense = defense;
        this.cardType = cardType;
        this.monsterTypes = monsterTypes;
        this.attribute = attribute;
        this.level = level;
    }

    public int getAttack() {
        if (name.equals("The Calculator")){
            return GameController.getInstance().getEffectController().getCalculatorPoints();
        }
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public ArrayList<MonsterType> getMonsterTypes() {
        return monsterTypes;
    }

    public void setMonsterTypes(ArrayList<MonsterType> monsterTypes) {
        this.monsterTypes = monsterTypes;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new MonsterCard(getName(), getDescription(), getPrice(), getAttack(), getDefense(), getCardType(),
                getMonsterTypes(), getAttribute(), getLevel());
    }
}

