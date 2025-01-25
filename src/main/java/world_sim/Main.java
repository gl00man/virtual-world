package world_sim;

import javax.swing.*;

import world_sim.components.virtualWorld.VirtualWorld;
import world_sim.creatures.*;
import world_sim.creatures.exceptions.OccupiedFieldInsertException;

import java.awt.BorderLayout;
import java.awt.event.*;

public class Main {
    private static final String windowTitle = "Maciej Bereda 200808";
    private static final int gridSizeX = 5;
    private static final int gridSizeY = 5;
    private static ICreature selectedCreature;
    private static JFrame mainFrame;

    public static void main(String[] args) throws Exception {
        mainFrame = new JFrame(windowTitle);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 800);

        setupMenuBar();

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        var roundLabel = new JLabel();
        var selectedCreatureLabel = new JLabel();
        selectedCreatureLabel.setText("Zwierze: None");

        sidePanel.add(roundLabel);
        sidePanel.add(selectedCreatureLabel);

        mainFrame.add(sidePanel, BorderLayout.EAST);

        var world = new VirtualWorld(gridSizeX, gridSizeY);
        for (int i = 0; i < gridSizeX; i++) {
            for (int j = 0; j < gridSizeY; j++) {
                var cell = world.getCell(i, j);
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (selectedCreature == null)
                            return;
                        int x = (int) cell.getClientProperty("x");
                        int y = (int) cell.getClientProperty("y");

                        System.out.println(x + " " + y);

                        try {
                            world.setCell(x, y, selectedCreature);
                        } catch (OccupiedFieldInsertException ex) {
                            JOptionPane.showMessageDialog(mainFrame, "Pole jest już zajęte.");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        }

        roundLabel.setText("Round: " + world.getRound());

        mainFrame.add(world.getJPanel(), BorderLayout.CENTER);

        mainFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case 32:
                        try {
                            world.nextRound();
                        } catch (OccupiedFieldInsertException | CloneNotSupportedException e1) {
                            e1.printStackTrace();
                        }
                        roundLabel.setText("Round: " + world.getRound());
                        break;
                    case 83:
                        selectedCreature = new Sheep();
                        break;
                    case 87:
                        selectedCreature = new Wolf();
                        break;
                }
                selectedCreatureLabel.setText("Zwierze: " + selectedCreature.getClass().getSimpleName());
            }
        });

        mainFrame.setVisible(true);
    }

    private static void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Symulacja");
        JMenuItem newItem = new JMenuItem("Nowa");
        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Opcja 'Nowy' została wybrana.");
                JOptionPane.showMessageDialog(mainFrame, "Nowy dokument został utworzony!");
            }
        });

        JMenuItem openItem = new JMenuItem("Otwórz");
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Opcja 'Nowy' została wybrana.");
                JOptionPane.showMessageDialog(mainFrame, "Nowy dokument został utworzony!");
            }
        });

        JMenuItem saveItem = new JMenuItem("Zapisz");
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Opcja 'Nowy' została wybrana.");
                JOptionPane.showMessageDialog(mainFrame, "Nowy dokument został utworzony!");
            }
        });

        JMenuItem exitItem = new JMenuItem("Zakończ");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);

        mainFrame.setJMenuBar(menuBar);
    }
}