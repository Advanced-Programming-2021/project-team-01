package model.card;

import java.util.ArrayList;

public class MonsterCard extends Card {
    int level;
    private int attack;
    private int defense;
    private CardType cardType;
    private ArrayList<MonsterType> monsterTypes;
    private Attribute attribute;

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
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public ArrayList<MonsterType> getMonsterTypes() {
        return monsterTypes;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void increaseAttack(int amount) {
        attack += amount;
    }
    public void increaseDefense(int amount) {
        defense += amount;
    }

    public int getLevel() {
        return level;
    }
}
