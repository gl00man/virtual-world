package world_sim.creatures;

import java.util.concurrent.ThreadLocalRandom;

import world_sim.creatures.exceptions.InvalidCreatureParameterException;
import world_sim.creatures.exceptions.OccupiedFieldInsertException;
import world_sim.utils.WorldLogger;

public class Fox extends VWCreature {
    public Fox() {
        super(3, 7, "lis");
    }

    public Fox(int strength, int initiative, int age, int cloneChance) throws InvalidCreatureParameterException {
        super(strength, initiative, age, cloneChance, "lis");
    }

    @Override
    public void move(CreatureMapField creatureField, int newX, int newY, CreatureMap creatureMap)
            throws OccupiedFieldInsertException, CloneNotSupportedException {
        var creature = creatureField.getCreature();
        var creatureOnField = creatureMap.getCreature(newX, newY);
        if (creatureOnField == null) {
            creatureField.setX(newX);
            creatureField.setY(newY);
            WorldLogger.logMessage(String.format("%s rusza się na pole %s %s", getSymbol(), newX, newY));
        } else if (creatureOnField.getClass() == creature.getClass()) {
            if (creatureOnField.getAge() >= 6 || creature.getAge() >= 6) {
                if (ThreadLocalRandom.current().nextInt(0, 100) < _cloneChance) {
                    multiply(creatureField, creatureMap);
                }
            }
        } else if (creatureOnField.getStrength() < getStrength()) {
            attack(creatureOnField, creatureField, newX, newY, creatureMap);
        }

        creatureField.getCreature().incrementAge();
    }
}
