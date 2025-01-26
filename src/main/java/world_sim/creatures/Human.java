package world_sim.creatures;

import java.util.concurrent.ThreadLocalRandom;

import world_sim.creatures.exceptions.InvalidCreatureParameterException;
import world_sim.creatures.exceptions.OccupiedFieldInsertException;
import world_sim.utils.WorldLogger;

public class Human extends VWCreature {
    public Human() {
        super(3, 5, "człowiek");
    }

    public Human(int strength, int initiative, int age, int cloneChance) throws InvalidCreatureParameterException {
        super(strength, initiative, age, cloneChance, "człowiek");
    }

    @Override
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
        } else if (creatureOnField.getStrength() < getStrength()) {
            attack(creatureOnField, creatureField, newX, newY, creatureMap);
        }

        creatureField.getCreature().incrementAge();

        if (_age % 5 == 0) {
            _strength *= 2;
            _initiative *= 2;
        }
    }

    @Override
    public void attack(VWCreature victim, CreatureMapField attackerField, int newX, int newY, CreatureMap creatureMap) {
        var winner = victim.defend(this);

        if (winner == this) {
            creatureMap.removeCreature(newX, newY);
            attackerField.setX(newX);
            attackerField.setY(newY);
            WorldLogger.logMessage(
                    String.format("%s zabija z procy %s", getSymbol(), victim.getSymbol()));
        } else if (winner != null) {
            creatureMap.removeCreature(attackerField.getX(), attackerField.getY());
            var winnerField = creatureMap.getField(newX, newY);
            winnerField.setX(attackerField.getX());
            winnerField.setY(attackerField.getY());
            WorldLogger.logMessage(
                    String.format("%s walczy z %s a zwyciezca to %s", getSymbol(), victim.getSymbol(),
                            winner.getSymbol()));
        }
    }
}
