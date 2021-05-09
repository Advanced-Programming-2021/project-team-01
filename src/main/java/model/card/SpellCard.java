package model.card;

public class SpellCard extends Card {
    Property property;
    //fixme: status is unclear


    public Property getProperty() {
        return property;
    }

    public SpellCard(String name, String description, int price, Property property) {
        super(name, description, price);
        super.type = "spell";
        this.property = property;
    }

    //    @Override
//    public Object clone() throws CloneNotSupportedException {
//        SpellCard spellCard =  new SpellCard(this.getName(), this.getDescription(), this.getPrice(), this.property);
//        spellCard.set
//    }
    public Object clone() throws CloneNotSupportedException {
        return new SpellCard(this.getName(), this.getDescription(), this.getPrice(), this.property);
    }

}
