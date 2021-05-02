package model;

import model.card.Card;
import model.card.SpellCard;
import model.card.TrapCard;

public class ZoneSlot {
    private Card card;
    private boolean isHidden;
    private boolean isDefending;
    public void setCard(Card card) {

    }

    public void isHidden(boolean value) {

    }

    public Card getCard() {
        return card;
    }

    public String toSting(){
        if (card == null){
            return "E";
        }
        if (card instanceof SpellCard || card instanceof TrapCard){
            if (isHidden){
                return "H";
            }
            return "O";
        }else {
            if (isDefending && isHidden){
                return "DH";
            }else if (isDefending && !isHidden){
                return "DO";
            }
            return "OO";
        }
    }
}
