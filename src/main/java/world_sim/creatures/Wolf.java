package world_sim.creatures;

import world_sim.creatures.exceptions.InvalidCreatureParameterException;

public class Wolf extends VWCreature {
    public Wolf() {
        super(9, 5, "w");
    }

    public Wolf(int strength, int initiative, int age, int cloneChance) throws InvalidCreatureParameterException {
        super(strength, initiative, age, cloneChance, "w");
    }
}
