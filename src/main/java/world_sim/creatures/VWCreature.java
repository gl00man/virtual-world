package world_sim.creatures;

public class VWCreature implements ICreature {
    protected int _x, _y, _strength, _initiative, _age = 0;
    protected String _symbol;

    public VWCreature(int x, int y, int strength, int initiative, String symbol) {
        _x = x;
        _y = y;
        _strength = strength;
        _initiative = initiative;
        _symbol = symbol;
    }

    @Override
    public int getX() {
        return _x;
    }

    @Override
    public void setX(int value) {
        _x = value;
    }

    @Override
    public int getY() {
        return _y;
    }

    @Override
    public void setY(int value) {
        _y = value;
    }

    @Override
    public int getStrength() {
        return _strength;
    }

    @Override
    public void setStrength(int value) {
        _strength = value;
    }

    @Override
    public int getInitiative() {
        return _initiative;
    }

    @Override
    public void setInitiative(int value) {
        _initiative = value;
    }

    @Override
    public int getAge() {
        return _age;
    }

    @Override
    public void incrementAge() {
        _age++;
    }

    @Override
    public String getSymbol() {
        return _symbol;
    }
}
