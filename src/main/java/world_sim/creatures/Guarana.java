package world_sim.creatures;

import java.util.concurrent.ThreadLocalRandom;

import world_sim.creatures.exceptions.InvalidCreatureParameterException;
import world_sim.creatures.exceptions.OccupiedFieldInsertException;

public class Guarana extends VWCreature {
    public Guarana() {
        super(0, 0, "guarana");
    }

    public Guarana(int strength, int initiative, int age, int cloneChance) throws InvalidCreatureParameterException {
        super(strength, initiative, age, cloneChance, "guarana");
    }

    @Override
    public void move(CreatureMapField creatureField, int newX, int newY, CreatureMap creatureMap)
            throws OccupiedFieldInsertException, CloneNotSupportedException {
        if (ThreadLocalRandom.current().nextInt(0, 100) < _cloneChance) {
            multiply(creatureField, creatureMap);
        }

        creatureField.getCreature().incrementAge();
    }

    @Override
    public VWCreature defend(VWCreature attacker) {
        attacker.setStrength(attacker.getStrength() + 3);
        return attacker;
    }
}
