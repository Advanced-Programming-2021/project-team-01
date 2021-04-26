package model.card;

import java.util.ArrayList;

//
public enum MonsterType
{
    WARRIOR,
    BEAST,
    FIEND,
    AQUA,
    PYRO,
    SPELLCASTER,
    THUNDER,
    DRAGON,
    MACHINE,
    ROCK,
    INSECT,
    CYBER,
    FAIRY,
    BEAST_WARRIOR,
    SEA_SERPENT;

    public static MonsterType[] getMonsterTypesArray(String types) {
        ArrayList<MonsterType> monsterTypes = new ArrayList<>();
        String[] monsterTypeStringArray = types.split("-");

        for (String type : monsterTypeStringArray) {
            if (type.equals("Warrior")) {
                monsterTypes.add(WARRIOR);
            } else if (type.equals("Beast")) {
                monsterTypes.add(BEAST);
            } else if (type.equals("Fiend")) {
                monsterTypes.add(FIEND);
            } else if (type.equals("Aqua")) {
                monsterTypes.add(AQUA);
            } else if (type.equals("Pyro")) {
                monsterTypes.add(PYRO);
            } else if (type.equals("Spellcaster")) {
                monsterTypes.add(SPELLCASTER);
            } else if (type.equals("Thunder")) {
                monsterTypes.add(THUNDER);
            } else if (type.equals("Dragon")) {
                monsterTypes.add(DRAGON);
            } else if (type.equals("Machine")) {
                monsterTypes.add(MACHINE);
            } else if (type.equals("Rock")) {
                monsterTypes.add(ROCK);
            } else if (type.equals("Insect")) {
                monsterTypes.add(INSECT);
            } else if (type.equals("Cyber")) {
                monsterTypes.add(CYBER);
            } else if (type.equals("Fairy")) {
                monsterTypes.add(FAIRY);
            } else if (type.equals("Sea Serpent")) {
                monsterTypes.add(SEA_SERPENT);
            } else
                monsterTypes.add(null);
        }

        return (MonsterType[]) monsterTypes.toArray();
    }
}
