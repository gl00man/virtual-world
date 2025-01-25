package world_sim.components.virtualWorld;

import javax.swing.*;

import world_sim.components.virtualWorld.exceptions.InvalidWorldParameterException;
import world_sim.components.virtualWorld.exceptions.PointOutOfWorldSizeException;
import world_sim.creatures.CreatureMap;
import world_sim.creatures.ICreature;
import world_sim.creatures.exceptions.OccupiedFieldInsertException;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class VirtualWorld {
    private int _round = 0, _cloneChance = 25;
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

    public VirtualWorld(int sizeX, int sizeY, int cloneChance) throws InvalidWorldParameterException {
        this(sizeX, sizeY);

        if (cloneChance < 0 || cloneChance > 100)
            throw new InvalidWorldParameterException("Clone chance must be from 0 to 100.");
        _cloneChance = cloneChance;
    }

    public void setCell(int x, int y, ICreature creature)
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
            var creatureOnField = _creatureMap.getCreature(newX, newY);
            if (creatureOnField == null) {
                creatureField.setX(newX);
                creatureField.setY(newY);
            } else if (creatureOnField.getClass() == creature.getClass()) {
                if (creatureOnField.getAge() <= 6 || creature.getAge() <= 6)
                    continue;
                if (ThreadLocalRandom.current().nextInt(0, 100) < _cloneChance) {
                    var spawned = false;
                    int x = -1, y = -1;
                    while (!spawned && x <= 1) {
                        var checkX = creatureField.getX() + x;
                        if (checkX < 0 || checkX >= _creatureMap.getSizeX()) {
                            x++;
                            continue;
                        }

                        while (y <= 1) {
                            var checkY = creatureField.getY() + y;
                            if (checkY < 0 || checkY >= _creatureMap.getSizeY()) {
                                y++;
                                continue;
                            }

                            var field = _creatureMap.getCreature(checkX, checkY);
                            if (field != null) {
                                y++;
                                continue;
                            }

                            _creatureMap.addCreature(checkX, checkY, (ICreature) creature.clone());
                            spawned = true;
                            break;
                        }
                        x++;
                    }
                }
            } else {
                var winner = creature.getStrength() == creatureOnField.getStrength()
                        ? creature
                        : creature.getStrength() > creatureOnField.getStrength()
                                ? creature
                                : creatureOnField;

                if (winner == creature) {
                    _creatureMap.removeCreature(newX, newY);
                    creatureField.setX(newX);
                    creatureField.setY(newY);
                }
            }

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
