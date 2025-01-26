package world_sim.creatures;

import world_sim.creatures.exceptions.InvalidCreatureParameterException;

public class VWCreatureFactory {
    public static VWCreature getCreature(String symbol) {
        switch (symbol.toUpperCase()) {
            case "LIS":
                return new Fox();
            case "GUARANA":
                return new Guarana();
            case "MLECZ":
                return new Sonchus();
            case "KOMAR":
                return new Mosquito();
            case "OWCA":
                return new Sheep();
            case "WILK":
                return new Wolf();
            case "CZŁOWIEK":
                return new Human();
            case "TRAWA":
                return new Grass();
        }

        return null;
    }

    public static VWCreature getCreature(int strength, int initiative, int age, int cloneChance, String symbol)
            throws InvalidCreatureParameterException {
        switch (symbol.toUpperCase()) {
            case "LIS":
                return new Fox(strength, initiative, age, cloneChance);
            case "GUARANA":
                return new Guarana(strength, initiative, age, cloneChance);
            case "MLECZ":
                return new Sonchus(strength, initiative, age, cloneChance);
            case "KOMAR":
                return new Mosquito(strength, initiative, age, cloneChance);
            case "OWCA":
                return new Sheep(strength, initiative, age, cloneChance);
            case "WILK":
                return new Wolf(strength, initiative, age, cloneChance);
            case "CZŁOWIEK":
                return new Human(strength, initiative, age, cloneChance);
            case "TRAWA":
                return new Grass(strength, initiative, age, cloneChance);
        }

        return null;
    }
}
