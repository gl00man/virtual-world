package world_sim.creatures;

import java.util.concurrent.ThreadLocalRandom;

import world_sim.creatures.exceptions.OccupiedFieldInsertException;

public class Mosquito extends VWCreature {
    public Mosquito() {
        super(1, 1, "m");
    }

    @Override
    public void move(CreatureMapField creatureField, int newX, int newY, CreatureMap creatureMap)
            throws OccupiedFieldInsertException, CloneNotSupportedException {
        var creature = creatureField.getCreature();
        int groupMembersCount = 0;
        for (int x = -1; x <= 1; x++) {
            var checkX = creatureField.getX() + x;
            if (checkX < 0 || checkX >= creatureMap.getSizeX()) {
                continue;
            }

            for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0)
                    continue;
                var checkY = creatureField.getY() + y;
                if (checkY < 0 || checkY >= creatureMap.getSizeY()) {
                    continue;
                }

                var field = creatureMap.getCreature(checkX, checkY);
                if (field == null || creature.getClass() != field.getClass()) {
                    continue;
                }

                groupMembersCount++;
                break;
            }
            x++;
        }

        creature.setInitiative(1 + groupMembersCount);
        creature.setStrength(1 + groupMembersCount);

        var creatureOnField = creatureMap.getCreature(newX, newY);
        if (creatureOnField == null) {
            creatureField.setX(newX);
            creatureField.setY(newY);
        } else if (creatureOnField.getClass() == creature.getClass()) {
            if (creatureOnField.getAge() >= 6 || creature.getAge() >= 6) {
                if (ThreadLocalRandom.current().nextInt(0, 100) < _cloneChance) {
                    multiply(creatureField, creatureMap);
                }
            }
        } else {
            attack(creatureOnField, creatureField, newX, newY, creatureMap);
        }

        creatureField.getCreature().incrementAge();
    }

    @Override
    public void attack(VWCreature victim, CreatureMapField attackerField, int newX, int newY, CreatureMap creatureMap) {
        var winner = victim.defend(this);

        if (winner == this) {
            creatureMap.removeCreature(newX, newY);
            attackerField.setX(newX);
            attackerField.setY(newY);
        } else if (ThreadLocalRandom.current().nextInt(0, 100) < 50) {
            creatureMap.removeCreature(attackerField.getX(), attackerField.getY());
            var winnerField = creatureMap.getField(newX, newY);
            winnerField.setX(attackerField.getX());
            winnerField.setY(attackerField.getY());
        }
    }

    @Override
    public VWCreature defend(VWCreature attacker) {
        var winner = getStrength() == attacker.getStrength()
                ? this
                : getStrength() > attacker.getStrength()
                        ? this
                        : attacker;

        if (winner != this && ThreadLocalRandom.current().nextInt(0, 100) < 50)
            return winner;

        return null;
    }
}
