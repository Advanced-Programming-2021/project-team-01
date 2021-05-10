package model.card;

public class TrapCard extends Card {
    Property property;
    String limitationStatus;


    public TrapCard(String name, String description, int price, Property property, String limitationStatus) {
        super(name, description, price);
        super.type = "trap";
        this.property = property;
        this.limitationStatus = limitationStatus;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new TrapCard(this.getName(), this.getDescription(), this.getPrice(), this.property, this.limitationStatus);
    }

    public Property getProperty() {
        return property;
    }

    public String getLimitationStatus() {
        return limitationStatus;
    }
}
