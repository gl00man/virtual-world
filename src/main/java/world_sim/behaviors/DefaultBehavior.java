package world_sim.behaviors;

import java.util.concurrent.ThreadLocalRandom;

import world_sim.creatures.CreatureMap;
import world_sim.creatures.CreatureMapField;
import world_sim.creatures.ICreature;
import world_sim.creatures.exceptions.OccupiedFieldInsertException;

public class DefaultBehavior implements IBehavior {
    public void Execute(CreatureMapField creatureField, int newX, int newY, int cloneChance,
            CreatureMap creatureMap)
            throws OccupiedFieldInsertException, CloneNotSupportedException {
        var creature = creatureField.getCreature();
        var creatureOnField = creatureMap.getCreature(newX, newY);
        if (creatureOnField == null) {
            creatureField.setX(newX);
            creatureField.setY(newY);
        } else if (creatureOnField.getClass() == creature.getClass()) {
            if (creatureOnField.getAge() <= 6 || creature.getAge() <= 6)
                return;
            if (ThreadLocalRandom.current().nextInt(0, 100) < cloneChance) {
                var spawned = false;
                int x = -1, y = -1;
                while (!spawned && x <= 1) {
                    var checkX = creatureField.getX() + x;
                    if (checkX < 0 || checkX >= creatureMap.getSizeX()) {
                        x++;
                        continue;
                    }

                    while (y <= 1) {
                        var checkY = creatureField.getY() + y;
                        if (checkY < 0 || checkY >= creatureMap.getSizeY()) {
                            y++;
                            continue;
                        }

                        var field = creatureMap.getCreature(checkX, checkY);
                        if (field != null) {
                            y++;
                            continue;
                        }

                        creatureMap.addCreature(checkX, checkY, (ICreature) creature.clone());
                        spawned = true;
                        break;
                    }
                    x++;
                }
            }
        } else {
            var winner = creature.getStrength() == creatureOnField.getStrength()
                    ? creature
                    : creature.getStrength() > creatureOnField.getStrength()
                            ? creature
                            : creatureOnField;

            if (winner == creature) {
                creatureMap.removeCreature(newX, newY);
                creatureField.setX(newX);
                creatureField.setY(newY);
            }
        }

        creatureField.getCreature().incrementAge();
    }
}
