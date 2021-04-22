package model.card;

public class MonsterCard extends Card
{
    private int attack;
    private int defense;
    private CardType cardType;
    private MonsterType[] monsterTypes;
    private Attribute attribute;
    int level;

    public MonsterCard(String name, String description, int price, int attack, int defense,
                       CardType cardType, MonsterType[] monsterTypes, Attribute attribute, int level) {
        super(name, description, price);
        this.attack = attack;
        this.defense = defense;
        this.cardType = cardType;
        this.monsterTypes = monsterTypes;
        this.attribute = attribute;
        this.level = level;
    }
}
