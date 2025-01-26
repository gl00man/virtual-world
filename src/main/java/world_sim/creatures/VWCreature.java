package world_sim.creatures;

import java.util.concurrent.ThreadLocalRandom;

import world_sim.creatures.exceptions.InvalidCreatureParameterException;
import world_sim.creatures.exceptions.OccupiedFieldInsertException;
import world_sim.utils.WorldLogger;

public abstract class VWCreature implements Cloneable {
    protected int _strength, _initiative, _age = 0, _cloneChance = 25;
    protected String _symbol;

    public VWCreature(int strength, int initiative, String symbol) {
        _strength = strength;
        _initiative = initiative;
        _symbol = symbol;
    }

    public VWCreature(int strength, int initiative, int age, int cloneChance, String symbol)
            throws InvalidCreatureParameterException {
        this(strength, initiative, symbol);
        if (age < 0)
            throw new InvalidCreatureParameterException("Creature age cannot be smaller than 0.");
        else if (age >= 6)
            _symbol = symbol.toUpperCase();

        _age = age;
    }

    public int getStrength() {
        return _strength;
    }

    public void setStrength(int value) {
        _strength = value;
    }

    public int getInitiative() {
        return _initiative;
    }

    public void setInitiative(int value) {
        _initiative = value;
    }

    public int getAge() {
        return _age;
    }

    public int getCloneChance() {
        return _cloneChance;
    }

    public void incrementAge() {
        _age++;
        if (_age == 6)
            _symbol = _symbol.toUpperCase();
    }

    public String getSymbol() {
        return _symbol;
    }

    public void move(CreatureMapField creatureField, int newX, int newY, CreatureMap creatureMap)
            throws OccupiedFieldInsertException, CloneNotSupportedException {
        var creature = creatureField.getCreature();
        var creatureOnField = creatureMap.getCreature(newX, newY);
        if (creatureOnField == null) {
            creatureField.setX(newX);
            creatureField.setY(newY);
            WorldLogger.logMessage(String.format("%s rusza się na pole %s %s", getSymbol(), newX, newY));
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

    public void attack(VWCreature victim, CreatureMapField attackerField, int newX, int newY, CreatureMap creatureMap) {
        var winner = victim.defend(this);

        if (winner == this) {
            creatureMap.removeCreature(newX, newY);
            attackerField.setX(newX);
            attackerField.setY(newY);
        } else if (winner != null) {
            creatureMap.removeCreature(attackerField.getX(), attackerField.getY());
            var winnerField = creatureMap.getField(newX, newY);
            winnerField.setX(attackerField.getX());
            winnerField.setY(attackerField.getY());
        }

        WorldLogger.logMessage(
                String.format("%s walczy z %s a zwyciezca to %s", getSymbol(), victim.getSymbol(), winner.getSymbol()));
    }

    public VWCreature defend(VWCreature attacker) {
        return getStrength() == attacker.getStrength()
                ? this
                : getStrength() > attacker.getStrength()
                        ? this
                        : attacker;
    }

    public void multiply(CreatureMapField creatureField, CreatureMap creatureMap)
            throws OccupiedFieldInsertException, CloneNotSupportedException {
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

                creatureMap.addCreature(checkX, checkY, (VWCreature) clone());
                spawned = true;
                WorldLogger.logMessage(String.format("%s rozmnaza się na pole %s %s", getSymbol(), checkX, checkY));
                break;
            }
            x++;
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
