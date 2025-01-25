package world_sim.creatures;

import java.util.ArrayList;
import java.util.Comparator;

import world_sim.components.virtualWorld.exceptions.InvalidWorldParameterException;
import world_sim.creatures.exceptions.OccupiedFieldInsertException;

public class CreatureMap {
    private final int _sizeX, _sizeY;
    private final ArrayList<CreatureMapField> _map;

    public CreatureMap(int sizeX, int sizeY) throws InvalidWorldParameterException {
        if (sizeX < 2 || sizeY < 2)
            throw new InvalidWorldParameterException("Minimal world size is 2x2.");

        _sizeX = sizeX;
        _sizeY = sizeY;

        _map = new ArrayList<CreatureMapField>();
    }

    public int getCreaturesCount() {
        return _map.size();
    }

    public int getSizeX() {
        return _sizeX;
    }

    public int getSizeY() {
        return _sizeY;
    }

    public VWCreature getCreature(int x, int y) {
        CreatureMapField field = _map.stream()
                .filter(f -> f.getX() == x && f.getY() == y)
                .findFirst()
                .orElse(null);

        return field != null ? field.getCreature() : null;
    }

    public VWCreature getCreature(int index) {
        return _map.get(index).getCreature();
    }

    public CreatureMapField getField(int x, int y) {
        return _map.stream()
                .filter(f -> f.getX() == x && f.getY() == y)
                .findFirst()
                .orElse(null);
    }

    public CreatureMapField getField(int index) {
        return _map.get(index);
    }

    public void addCreature(int x, int y, VWCreature creature) throws OccupiedFieldInsertException {
        if (getCreature(x, y) != null)
            throw new OccupiedFieldInsertException("Cannot insert creature on occupied field.");

        _map.add(new CreatureMapField(x, y, creature));
        _map.sort(Comparator.comparingInt((CreatureMapField f) -> f.getCreature().getInitiative()).reversed()
                .thenComparingInt((CreatureMapField f) -> f.getCreature().getAge()));
    }

    public void removeCreature(int x, int y) {
        CreatureMapField field = _map.stream()
                .filter(f -> f.getX() == x && f.getY() == y)
                .findFirst()
                .orElse(null);

        _map.remove(field);
    }
}
