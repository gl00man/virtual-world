package world_sim.creatures;

import java.util.ArrayList;
import java.util.Comparator;

import world_sim.creatures.exceptions.OccupiedFieldInsertException;

public class CreatureMap {
    private final ArrayList<CreatureMapField> _map;

    public CreatureMap() {
        _map = new ArrayList<CreatureMapField>();
    }

    public int getCreaturesCount() {
        return _map.size();
    }

    public ICreature getCreature(int x, int y) {
        CreatureMapField field = _map.stream()
                .filter(f -> f.getX() == x && f.getY() == y)
                .findFirst()
                .orElse(null);

        return field != null ? field.getCreature() : null;
    }

    public ICreature getCreature(int index) {
        return _map.get(index).getCreature();
    }

    public CreatureMapField getField(int index) {
        return _map.get(index);
    }

    public void addCreature(int x, int y, ICreature creature) throws OccupiedFieldInsertException {
        if (getCreature(x, y) != null)
            throw new OccupiedFieldInsertException("Cannot insert creature on occupied field.");

        _map.add(new CreatureMapField(x, y, creature));
        // _map.sort(Comparator.comparingInt(ICreature::getInitiative).reversed());
    }

    public void removeCreature(int x, int y) {
        CreatureMapField field = _map.stream()
                .filter(f -> f.getX() == x && f.getY() == y)
                .findFirst()
                .orElse(null);

        _map.remove(field);
    }
}
