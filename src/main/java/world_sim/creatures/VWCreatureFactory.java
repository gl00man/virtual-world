package world_sim.creatures;

import world_sim.creatures.exceptions.InvalidCreatureParameterException;

public class VWCreatureFactory {
    public static VWCreature getCreature(String symbol) {
        switch (symbol.toUpperCase()) {
            case "L":
                return new Fox();
            case "G":
                return new Guarana();
            case "M":
                return new Sonchus();
            case "K":
                return new Mosquito();
            case "O":
                return new Sheep();
            case "W":
                return new Wolf();
            case "C":
                return new Human();
            case "T":
                return new Grass();
        }

        return null;
    }

    public static VWCreature getCreature(int strength, int initiative, int age, int cloneChance, String symbol)
            throws InvalidCreatureParameterException {
        switch (symbol.toUpperCase()) {
            case "L":
                return new Fox(strength, initiative, age, cloneChance);
            case "G":
                return new Guarana(strength, initiative, age, cloneChance);
            case "M":
                return new Sonchus(strength, initiative, age, cloneChance);
            case "K":
                return new Mosquito(strength, initiative, age, cloneChance);
            case "O":
                return new Sheep(strength, initiative, age, cloneChance);
            case "W":
                return new Wolf(strength, initiative, age, cloneChance);
            case "C":
                return new Human();
            case "T":
                return new Grass();
        }

        return null;
    }
}
