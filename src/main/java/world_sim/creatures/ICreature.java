package world_sim.creatures;

public interface ICreature extends Cloneable {
    public int getStrength();

    public void setStrength(int value);

    public int getInitiative();

    public void setInitiative(int value);

    public int getAge();

    public void incrementAge();

    public String getSymbol();

    public Object clone() throws CloneNotSupportedException;
}