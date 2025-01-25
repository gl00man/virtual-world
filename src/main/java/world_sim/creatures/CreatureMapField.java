package world_sim.creatures;

public class CreatureMapField {
    private int _x, _y;
    private VWCreature _creature;

    public CreatureMapField(int x, int y, VWCreature creature) {
        _x = x;
        _y = y;
        _creature = creature;
    }

    public int getX() {
        return _x;
    }

    public void setX(int value) {
        _x = value;
    }

    public int getY() {
        return _y;
    }

    public void setY(int value) {
        _y = value;
    }

    public VWCreature getCreature() {
        return _creature;
    }
}
