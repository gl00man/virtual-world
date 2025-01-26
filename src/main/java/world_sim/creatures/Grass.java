package world_sim.creatures;

import java.util.concurrent.ThreadLocalRandom;

import world_sim.creatures.exceptions.InvalidCreatureParameterException;
import world_sim.creatures.exceptions.OccupiedFieldInsertException;

public class Grass extends VWCreature {
    public Grass() {
        super(0, 0, "trawa");
    }

    public Grass(int strength, int initiative, int age, int cloneChance) throws InvalidCreatureParameterException {
        super(strength, initiative, age, cloneChance, "trawa");
    }

    @Override
    public void move(CreatureMapField creatureField, int newX, int newY, CreatureMap creatureMap)
            throws OccupiedFieldInsertException, CloneNotSupportedException {
        if (ThreadLocalRandom.current().nextInt(0, 100) < _cloneChance) {
            multiply(creatureField, creatureMap);
        }

        creatureField.getCreature().incrementAge();
    }
}
