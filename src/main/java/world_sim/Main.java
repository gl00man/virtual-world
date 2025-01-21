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

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Symulacja");
        JMenuItem newItem = new JMenuItem("Nowa");
        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Opcja 'Nowy' została wybrana.");
                JOptionPane.showMessageDialog(frame, "Nowy dokument został utworzony!");
            }
        });

        JMenuItem openItem = new JMenuItem("Otwórz");
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Opcja 'Nowy' została wybrana.");
                JOptionPane.showMessageDialog(frame, "Nowy dokument został utworzony!");
            }
        });

        JMenuItem saveItem = new JMenuItem("Zapisz");
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Opcja 'Nowy' została wybrana.");
                JOptionPane.showMessageDialog(frame, "Nowy dokument został utworzony!");
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
        frame.setJMenuBar(menuBar);

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        var roundLabel = new JLabel();
        var selectedCreatureLabel = new JLabel();
        selectedCreatureLabel.setText("Zwierze: Wilk");

        sidePanel.add(roundLabel);
        sidePanel.add(selectedCreatureLabel);

        frame.add(sidePanel, BorderLayout.EAST);

        var world = new VirtualWorld(gridSizeX, gridSizeY);
        roundLabel.setText("Round: " + world.getRound());

        world.setCell(1, 2, new Wolf());
        world.setCell(4, 6, new Sheep());

        frame.add(world.getJPanel(), BorderLayout.CENTER);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case 32:
                        world.nextRound();
                        roundLabel.setText("Round: " + world.getRound());
                        break;
                    case 83:
                        world.setSelectedCreature(new Sheep());
                        selectedCreatureLabel.setText("Zwierze: Owca");
                        break;
                    case 87:
                        world.setSelectedCreature(new Wolf());
                        selectedCreatureLabel.setText("Zwierze: Wilk");
                        break;
                }
            }
        });

        frame.setVisible(true);
    }
}