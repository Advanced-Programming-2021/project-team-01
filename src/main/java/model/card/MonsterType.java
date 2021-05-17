package model.card;

import java.util.ArrayList;

//
public enum MonsterType {
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
    CYBERSE,
    FAIRY,
    BEAST_WARRIOR,
    SEA_SERPENT;

    public static ArrayList<MonsterType> getMonsterTypesArray(String types) {
        ArrayList<MonsterType> monsterTypes = new ArrayList<>();
        String[] monsterTypeStringArray = types.split("-");

        for (String type : monsterTypeStringArray) {
            switch (type) {
                case "Warrior":
                    monsterTypes.add(WARRIOR);
                    break;
                case "Beast":
                    monsterTypes.add(BEAST);
                    break;
                case "Fiend":
                    monsterTypes.add(FIEND);
                    break;
                case "Aqua":
                    monsterTypes.add(AQUA);
                    break;
                case "Pyro":
                    monsterTypes.add(PYRO);
                    break;
                case "Spellcaster":
                    monsterTypes.add(SPELLCASTER);
                    break;
                case "Thunder":
                    monsterTypes.add(THUNDER);
                    break;
                case "Dragon":
                    monsterTypes.add(DRAGON);
                    break;
                case "Machine":
                    monsterTypes.add(MACHINE);
                    break;
                case "Rock":
                    monsterTypes.add(ROCK);
                    break;
                case "Insect":
                    monsterTypes.add(INSECT);
                    break;
                case "Cyberse":
                    monsterTypes.add(CYBERSE);
                    break;
                case "Fairy":
                    monsterTypes.add(FAIRY);
                    break;
                case "Sea Serpent":
                    monsterTypes.add(SEA_SERPENT);
                    break;
                case "Beast-Warrior":
                    monsterTypes.add(BEAST_WARRIOR);
                    break;
                default:
                    monsterTypes.add(null);
                    break;
            }
        }

        return monsterTypes;
    }
}
