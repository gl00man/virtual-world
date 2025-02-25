package world_sim.creatures;

import java.util.concurrent.ThreadLocalRandom;

import world_sim.creatures.exceptions.InvalidCreatureParameterException;
import world_sim.creatures.exceptions.OccupiedFieldInsertException;

public class Sonchus extends VWCreature {
    public Sonchus() {
        super(0, 0, "mlecz");
    }

    public Sonchus(int strength, int initiative, int age, int cloneChance) throws InvalidCreatureParameterException {
        super(strength, initiative, age, cloneChance, "mlecz");
    }

    @Override
    public void move(CreatureMapField creatureField, int newX, int newY, CreatureMap creatureMap)
            throws OccupiedFieldInsertException, CloneNotSupportedException {
        for (int i = 0; i < 3; i++)
            if (ThreadLocalRandom.current().nextInt(0, 100) < _cloneChance) {
                multiply(creatureField, creatureMap);
                break;
            }

        creatureField.getCreature().incrementAge();
    }
}
