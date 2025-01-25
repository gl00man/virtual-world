package world_sim.creatures;

import world_sim.behaviors.DefaultBehavior;

public class Sheep extends VWCreature {
    public Sheep() {
        super(4, 4, new DefaultBehavior(), "s");
    }
}
