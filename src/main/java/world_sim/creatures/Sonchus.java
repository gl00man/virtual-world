package world_sim.creatures;

import java.util.concurrent.ThreadLocalRandom;

import world_sim.creatures.exceptions.OccupiedFieldInsertException;

public class Sonchus extends VWCreature {
    public Sonchus() {
        super(0, 0, "so");
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
