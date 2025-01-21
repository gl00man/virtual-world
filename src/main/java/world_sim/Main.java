package world_sim;

import javax.swing.*;

import world_sim.components.virtualWorld.VirtualWorld;
import world_sim.creatures.*;

import java.awt.BorderLayout;
import java.awt.event.*;

public class Main {
    private static final String windowTitle = "Maciej Bereda 200808";
    private static final int gridSizeX = 10;
    private static final int gridSizeY = 10;

    public static void main(String[] args) throws Exception {
        var frame = new JFrame(windowTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

        var roundLabel = new JLabel();
        frame.add(roundLabel, BorderLayout.EAST);

        var world = new VirtualWorld(gridSizeX, gridSizeY);
        roundLabel.setText("Round: " + world.getRound());

        world.setCell(new Wolf(1, 2));
        world.setCell(new Sheep(4, 6));

        frame.add(world.getJPanel(), BorderLayout.CENTER);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 32) {
                    world.nextRound();
                    roundLabel.setText("Round: " + world.getRound());
                }
            }
        });

        frame.setVisible(true);
    }
}