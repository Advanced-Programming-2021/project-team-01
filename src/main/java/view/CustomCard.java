package view;

import controller.Effect;
import model.card.MonsterCard;

import java.util.ArrayList;
import java.util.List;

public class CustomCard{
    List<MonsterCard> monsterCards = new ArrayList<>();
    List<Effect> effects = new ArrayList<>();

    public void addMonsterCard(MonsterCard monsterCard, Effect effect){
        monsterCards.add(monsterCard);
        effects.add(effect);
    }
}
