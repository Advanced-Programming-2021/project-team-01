package model;

import model.card.Card;
import model.card.SpellCard;
import model.card.TrapCard;

public class ZoneSlot {
    private Card card;
    private boolean isHidden;
    private boolean isDefending;

    public void setCard(Card card) {
        this.card = card;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public void setDefending(boolean defending) {
        isDefending = defending;
    }

    public Card getCard() {
        return card;
    }

    public String toString(){
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
            }else if (isDefending){
                return "DO";
            }
            return "OO";
        }
    }
}
