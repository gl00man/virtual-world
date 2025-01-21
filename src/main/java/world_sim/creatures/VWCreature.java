package world_sim.creatures;

public class VWCreature implements ICreature {
    protected int _strength, _initiative, _age = 0;
    protected String _symbol;

    public VWCreature(int strength, int initiative, String symbol) {
        _strength = strength;
        _initiative = initiative;
        _symbol = symbol;
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
