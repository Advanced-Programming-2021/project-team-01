package model.card;

public class TrapCard extends Card{
    Property property;
    //fixme : status is unclear


    public TrapCard(String name, String description, int price, Property property) {
        super(name, description, price);
        this.property = property;
    }
}
