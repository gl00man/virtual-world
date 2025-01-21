package world_sim.components.virtualWorld;

import javax.swing.*;

import world_sim.components.virtualWorld.exceptions.InvalidWorldSizeException;
import world_sim.components.virtualWorld.exceptions.PointOutOfWorldSizeException;
import world_sim.creatures.CreatureMap;
import world_sim.creatures.ICreature;
import world_sim.creatures.exceptions.OccupiedFieldInsertException;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class VirtualWorld {
    private final int _sizeX, _sizeY;
    private int _round = 0;
    private final JPanel _gridPanel;
    private final JLabel[][] _gridCells;
    private final CreatureMap _creatureMap;

    public VirtualWorld(int sizeX, int sizeY) throws InvalidWorldSizeException {
        if (sizeX < 2 || sizeY < 2)
            throw new InvalidWorldSizeException("Minimal world size is 2x2.");

        _sizeX = sizeX;
        _sizeY = sizeY;
        _creatureMap = new CreatureMap();
        _gridPanel = new JPanel(new GridLayout(sizeX, sizeY, 10, 10));
        _gridCells = new JLabel[sizeX][sizeY];

        for (var i = 0; i < sizeX; i++) {
            for (var j = 0; j < sizeY; j++) {
                var cell = new JLabel("", SwingConstants.CENTER);
                cell.setOpaque(true);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                cell.putClientProperty("x", i);
                cell.putClientProperty("y", j);

                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int x = (int) cell.getClientProperty("x");
                        int y = (int) cell.getClientProperty("y");
                        // tutaj dodanie stworka
                        updateCells();
                    }
                });

                _gridCells[i][j] = cell;
                _gridPanel.add(_gridCells[i][j]);
            }
        }
    }

    public void setCell(ICreature creature)
            throws PointOutOfWorldSizeException, OccupiedFieldInsertException {
        if (creature.getX() < 0 || creature.getX() > _sizeX - 1 || creature.getY() < 0 || creature.getY() > _sizeY - 1)
            throw new PointOutOfWorldSizeException("Given cell is out of world bounds.");

        _creatureMap.addCreature(creature);
        _gridCells[creature.getX()][creature.getY()].setText(creature.getSymbol());
    }

    public JPanel getJPanel() {
        return _gridPanel;
    }

    public int getRound() {
        return _round;
    }

    public void nextRound() {
        for (int i = 0; i < _creatureMap.getCreaturesCount(); i++) {
            var creature = _creatureMap.getCreature(i);
            var random = new Random();
            random.setSeed(System.currentTimeMillis() / 1000L);
            var randomX = random.nextInt(-1, 1);
            var randomY = random.nextInt(-1, 1);

            var newX = creature.getX() + randomX;
            var newY = creature.getY() + randomY;

            if (newX < 0 || newX >= _sizeX)
                newX = creature.getX();
            if (newY < 0 || newY >= _sizeY)
                newY = creature.getY();

            var creatureOnField = _creatureMap.getCreature(newX, newY);
            if (creatureOnField == null) {
                creature.setX(newX);
                creature.setY(newY);
            }
        }

        updateCells();
        _round++;
    }

    private void updateCells() {
        for (int i = 0; i < _sizeX; i++) {
            for (int j = 0; j < _sizeY; j++) {
                var cellCreature = _creatureMap.getCreature(i, j);
                var cellContent = cellCreature != null ? cellCreature.getSymbol() : "";
                _gridCells[i][j].setText(cellContent);
            }
        }
    }
}
