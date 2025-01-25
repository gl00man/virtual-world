package world_sim.components.virtualWorld;

import javax.swing.*;

import world_sim.components.virtualWorld.exceptions.InvalidWorldParameterException;
import world_sim.components.virtualWorld.exceptions.PointOutOfWorldSizeException;
import world_sim.creatures.CreatureMap;
import world_sim.creatures.VWCreature;
import world_sim.creatures.exceptions.OccupiedFieldInsertException;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class VirtualWorld {
    private int _round = 0;
    private final JPanel _gridPanel;
    private final JLabel[][] _gridCells;
    private final CreatureMap _creatureMap;
    private final Random _random = new Random();

    public VirtualWorld(int sizeX, int sizeY) throws InvalidWorldParameterException {
        _creatureMap = new CreatureMap(sizeX, sizeY);
        _gridPanel = new JPanel(new GridLayout(sizeX, sizeY, 10, 10));
        _gridCells = new JLabel[sizeX][sizeY];

        for (var x = 0; x < sizeX; x++) {
            for (var y = 0; y < sizeY; y++) {
                var cell = new JLabel("", SwingConstants.CENTER);
                cell.setOpaque(true);
                cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                cell.putClientProperty("x", x);
                cell.putClientProperty("y", y);

                _gridCells[x][y] = cell;
                _gridPanel.add(_gridCells[x][y]);
            }
        }

        _random.setSeed(System.currentTimeMillis());
    }

    public void setCell(int x, int y, VWCreature creature)
            throws PointOutOfWorldSizeException, OccupiedFieldInsertException {
        if (x < 0 || x > _creatureMap.getSizeX() - 1 || y < 0 || y > _creatureMap.getSizeY() - 1)
            throw new PointOutOfWorldSizeException("Given cell is out of world bounds.");

        _creatureMap.addCreature(x, y, creature);
        _gridCells[x][y].setText(creature.getSymbol());
    }

    public JLabel getCell(int x, int y)
            throws PointOutOfWorldSizeException, OccupiedFieldInsertException {
        if (x < 0 || x > _creatureMap.getSizeX() - 1 || y < 0 || y > _creatureMap.getSizeY() - 1)
            throw new PointOutOfWorldSizeException("Given cell is out of world bounds.");

        return _gridCells[x][y];
    }

    public JPanel getJPanel() {
        return _gridPanel;
    }

    public int getRound() {
        return _round;
    }

    public void nextRound() throws OccupiedFieldInsertException, CloneNotSupportedException {
        for (int i = 0; i < _creatureMap.getCreaturesCount(); i++) {
            var creatureField = _creatureMap.getField(i);
            var randomX = ThreadLocalRandom.current().nextInt(-1, 2);
            var randomY = ThreadLocalRandom.current().nextInt(-1, 2);

            while (randomX == 0 && randomY == 0) {
                randomY = ThreadLocalRandom.current().nextInt(-1, 2);
            }

            var newX = creatureField.getX() + randomX;
            var newY = creatureField.getY() + randomY;

            if (newX < 0 || newX >= _creatureMap.getSizeX())
                continue;
            if (newY < 0 || newY >= _creatureMap.getSizeY())
                continue;

            var creature = creatureField.getCreature();
            creature.move(creatureField, newX, newY, _creatureMap);

            creatureField.getCreature().incrementAge();
        }

        updateCells();
        _round++;
    }

    private void updateCells() {
        for (int i = 0; i < _creatureMap.getSizeX(); i++) {
            for (int j = 0; j < _creatureMap.getSizeY(); j++) {
                var cellCreature = _creatureMap.getCreature(i, j);
                var cellContent = cellCreature != null ? cellCreature.getSymbol() : "";
                _gridCells[i][j].setText(cellContent);
            }
        }
    }
}
