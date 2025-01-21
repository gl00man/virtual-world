package world_sim.creatures;

public interface ICreature {
    public int getX();

    public void setX(int value);

    public int getY();

    public void setY(int value);

    public int getStrength();

    public void setStrength(int value);

    public int getInitiative();

    public void setInitiative(int value);

    public int getAge();

    public void incrementAge();

    public String getSymbol();
}