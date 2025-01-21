package world_sim.creatures;

import java.util.ArrayList;
import java.util.Comparator;

import world_sim.creatures.exceptions.OccupiedFieldInsertException;

public class CreatureMap {
    private final ArrayList<ICreature> _map;

    public CreatureMap() {
        _map = new ArrayList<ICreature>();
    }

    public int getCreaturesCount() {
        return _map.size();
    }

    public ICreature getCreature(int x, int y) {
        return _map.stream()
                .filter(c -> c.getX() == x && c.getY() == y)
                .findFirst()
                .orElse(null);
    }

    public ICreature getCreature(int index) {
        return _map.get(index);
    }

    public void addCreature(ICreature creature) throws OccupiedFieldInsertException {
        if (getCreature(creature.getX(), creature.getY()) != null)
            throw new OccupiedFieldInsertException("Cannot insert creature on occupied field.");

        _map.add(creature);
        _map.sort(Comparator.comparingInt(ICreature::getInitiative).reversed());
    }

    public void removeCreature(int x, int y) {
        _map.remove(getCreature(x, y));
    }
}
