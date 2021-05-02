package model.card;

public class SpellCard extends Card{
    Property property;
    //fixme: status is unclear


    public SpellCard(String name, String description, int price, Property property) {
        super(name, description, price);
        super.type = "spell";
        this.property = property;
    }
}
