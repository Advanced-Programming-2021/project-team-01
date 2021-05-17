package model.card;

public class SpellCard extends Card {
    Property property;
    String limitationStatus;


    public Property getProperty() {
        return property;
    }

    public String getLimitationStatus() {
        return limitationStatus;
    }

    public SpellCard(String name, String description, int price, Property property, String limitationStatus) {
        super(name, description, price);
        super.type = "spell";
        this.property = property;
        this.limitationStatus = limitationStatus;
    }

    public Object clone() throws CloneNotSupportedException {
        return new SpellCard(this.getName(), this.getDescription(), this.getPrice(), this.property, this.limitationStatus);
    }

}
