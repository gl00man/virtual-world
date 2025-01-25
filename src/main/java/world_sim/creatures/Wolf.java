package world_sim.creatures;

import world_sim.behaviors.DefaultBehavior;

public class Wolf extends VWCreature {
    public Wolf() {
        super(9, 5, new DefaultBehavior(), "w");
    }
}
