package world_sim.creatures;

import world_sim.behaviors.IBehavior;
import world_sim.creatures.exceptions.InvalidCreatureParameterException;

public class VWCreature implements ICreature {
    protected int _strength, _initiative, _age = 0;
    IBehavior _behavior;
    protected String _symbol;

    public VWCreature(int strength, int initiative, IBehavior behavior, String symbol) {
        _strength = strength;
        _initiative = initiative;
        _behavior = behavior;
        _symbol = symbol;
    }

    public VWCreature(int strength, int initiative, int age, IBehavior behavior, String symbol)
            throws InvalidCreatureParameterException {
        this(strength, initiative, behavior, symbol);
        if (age < 0)
            throw new InvalidCreatureParameterException("Creature age cannot be smaller than 0.");
        else if (age >= 6)
            _symbol = symbol.toUpperCase();

        _age = age;
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
        if (_age == 6)
            _symbol = _symbol.toUpperCase();
    }

    @Override
    public String getSymbol() {
        return _symbol;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
