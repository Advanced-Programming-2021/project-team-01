package model;

import controller.GameController;
import controller.Effect;
import model.card.Card;
import model.card.MonsterCard;
import model.card.SpellCard;
import model.card.TrapCard;

public class ZoneSlot {
    private Card card;
    private boolean isHidden;
    private boolean isDefending;
    private Card equippedCard;

    public ZoneSlot() {
        isHidden = false;
        isDefending = false;
        equippedCard = null;
    }


    public void setEquippedCard(Card card) {
        this.equippedCard = card;
    }

    public Card getEquippedCard(Card card) {
        return equippedCard;
    }

    public int getAttack() {
        if (card instanceof MonsterCard) {
            int baseAttack = ((MonsterCard) card).getAttack();
            if (equippedCard != null) {
                baseAttack += equipAttackBooster();
            }
            return baseAttack + GameController.getInstance().getEffectController().fieldAttackBoosterCurrent((MonsterCard) card) +
                    GameController.getInstance().getEffectController().fieldAttackBoosterOpponent((MonsterCard) card);
        }
        return 0;
    }

    private int equipAttackBooster() {
        if (Effect.getSpellByName(equippedCard.getName()) == Effect.BLACK_PENDANT) {
            return 500;
        } else if (Effect.getSpellByName(equippedCard.getName()) == Effect.SWORD_OF_DESTRUCTION) {
            return 400;
        } else if (Effect.getSpellByName(equippedCard.getName()) == Effect.MAGNUM_SHIELD) {
            if (!isDefending) {
                return ((MonsterCard) card).getDefense();
            }
        } else if (Effect.getSpellByName(equippedCard.getName()) == Effect.UNITED_WE_STAND) {
            return GameController.getInstance().getEffectController().unitedWeStand();
        }
        return 0;
    }

    private int equipDefenceBooster() {
        if (Effect.getSpellByName(equippedCard.getName()) == Effect.UNITED_WE_STAND) {
            return GameController.getInstance().getEffectController().unitedWeStand();
        } else if (Effect.getSpellByName(equippedCard.getName()) == Effect.MAGNUM_SHIELD) {
            if (isDefending) {
                return ((MonsterCard) card).getAttack();
            }
        } else if (Effect.getSpellByName(equippedCard.getName()) == Effect.SWORD_OF_DESTRUCTION) {
            return -200;
        }
        return 0;
    }

    public int getDefence() {
        if (card instanceof MonsterCard) {
            int baseDefence = ((MonsterCard) card).getDefense();
            if (equippedCard != null) {
                baseDefence += equipDefenceBooster();
            }
            return baseDefence + GameController.getInstance().getEffectController().fieldDefenceBoosterCurrent((MonsterCard) card) +
                    GameController.getInstance().getEffectController().fieldDefenceBoosterOpponent((MonsterCard) card);
        }
        return 0;
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

    public void setCard(Card card) {
        this.card = card;
    }

    public String toString() {
        if (card == null) {
            return "E";
        }
        if (card instanceof SpellCard || card instanceof TrapCard) {
            if (isHidden) {
                return "H";
            }
            return "O";
        } else {
            if (isDefending && isHidden) {
                return "DH";
            } else if (isDefending) {
                return "DO";
            }
            return "OO";
        }
    }


}
