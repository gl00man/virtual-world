package world_sim.creatures;

import world_sim.creatures.exceptions.InvalidCreatureParameterException;

public class Sheep extends VWCreature {
    public Sheep() {
        super(4, 4, "o");
    }

    public Sheep(int strength, int initiative, int age, int cloneChance) throws InvalidCreatureParameterException {
        super(strength, initiative, age, cloneChance, "o");
    }
}
