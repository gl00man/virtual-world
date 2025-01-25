// package world_sim.creatures;

// import world_sim.creatures.exceptions.OccupiedFieldInsertException;

// public interface VWCreature extends Cloneable {
// public int getStrength();

// public void setStrength(int value);

// public int getInitiative();

// public void setInitiative(int value);

// public int getAge();

// public void incrementAge();

// public String getSymbol();

// public void move(CreatureMapField creatureField, int newX, int newY,
// CreatureMap creatureMap)
// throws OccupiedFieldInsertException, CloneNotSupportedException;

// public void attack(VWCreature victim, CreatureMapField attackerField, int
// newX, int newY, CreatureMap creatureMap);

// //
// public VWCreature defend(VWCreature attacker);

// public void multiply(CreatureMapField creatureField, CreatureMap creatureMap)
// throws OccupiedFieldInsertException, CloneNotSupportedException;

// public Object clone() throws CloneNotSupportedException;
// }