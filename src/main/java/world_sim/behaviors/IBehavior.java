package world_sim.behaviors;

import world_sim.creatures.CreatureMap;
import world_sim.creatures.CreatureMapField;
import world_sim.creatures.exceptions.OccupiedFieldInsertException;

public interface IBehavior {
    public void Execute(CreatureMapField creatureField, int newX, int newY, int cloneChance,
            CreatureMap creatureMap)
            throws OccupiedFieldInsertException, CloneNotSupportedException;
}