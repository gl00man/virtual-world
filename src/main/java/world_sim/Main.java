package world_sim;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import world_sim.components.virtualWorld.VirtualWorld;
import world_sim.components.virtualWorld.exceptions.InvalidWorldParameterException;
import world_sim.creatures.*;
import world_sim.creatures.exceptions.OccupiedFieldInsertException;
import world_sim.utils.WorldFileHelper;
import world_sim.utils.WorldLogger;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.File;

public class Main {
    private static final String windowTitle = "Maciej Bereda 200808";
    private static final int gridSizeX = 5;
    private static final int gridSizeY = 5;
    private static VWCreature selectedCreature;
    private static JFrame mainFrame;
    private static VirtualWorld world;

    public static void main(String[] args) throws Exception {
        mainFrame = new JFrame(windowTitle);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1600, 800);

        // side panel
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        var roundLabel = new JLabel();
        var selectedCreatureLabel = new JLabel();
        selectedCreatureLabel.setText("Zwierze: ?");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton[] buttons = { new JButton("Wilk"), new JButton("Owca"), new JButton("Trawa"), new JButton("Guarana"),
                new JButton("Mlecz"), new JButton("Człowiek"), new JButton("Komar"), new JButton("Lis") };

        for (JButton button : buttons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedCreature = VWCreatureFactory.getCreature(button.getText());
                    selectedCreatureLabel.setText(button.getText());
                    mainFrame.requestFocusInWindow();
                }
            });
            buttonPanel.add(button);
        }

        JButton nextRoundButton = new JButton("Następna runda");

        // log panel
        JTextArea logTextArea = new JTextArea(10, 30);
        logTextArea.setEditable(false);
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);
        JScrollPane logScrollPane = new JScrollPane(logTextArea);
        WorldLogger.setTextArea((logTextArea));

        sidePanel.add(roundLabel);
        sidePanel.add(selectedCreatureLabel);
        sidePanel.add(buttonPanel);
        sidePanel.add(nextRoundButton);
        sidePanel.add(new JLabel("Logi:"));
        sidePanel.add(logScrollPane);

        mainFrame.add(sidePanel, BorderLayout.EAST);

        world = new VirtualWorld(gridSizeX, gridSizeY);
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

                        try {
                            world.setCell(x, y, selectedCreature);
                            selectedCreature = VWCreatureFactory.getCreature(selectedCreature.getSymbol());
                        } catch (OccupiedFieldInsertException ex) {
                            JOptionPane.showMessageDialog(mainFrame, "Pole jest już zajęte.");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
            }
        }

        roundLabel.setText("Runda: 0");
        nextRoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    world.nextRound();
                    roundLabel.setText("Runda: " + world.getRound());
                    mainFrame.requestFocusInWindow();
                } catch (OccupiedFieldInsertException | CloneNotSupportedException e1) {
                    e1.printStackTrace();
                }
            }
        });

        mainFrame.add(world.getJPanel(), BorderLayout.CENTER);

        mainFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 32) {
                    try {
                        world.nextRound();
                    } catch (OccupiedFieldInsertException | CloneNotSupportedException e1) {
                        e1.printStackTrace();
                    }
                    roundLabel.setText("Runda: " + world.getRound());
                } else {
                    char character = (char) e.getKeyCode();
                    String creatureSymbol = String.valueOf(character);
                    selectedCreature = VWCreatureFactory.getCreature(creatureSymbol);
                    var selectedCreatureSymbol = selectedCreature != null ? selectedCreature.getSymbol() : "?";
                    selectedCreatureLabel.setText("Zwierze: " + selectedCreatureSymbol);
                }
            }
        });

        // menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Symulacja");
        JMenuItem newItem = new JMenuItem("Nowa");
        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    world.setMap(new CreatureMap(gridSizeX, gridSizeY));
                    mainFrame.add(world.getJPanel(), BorderLayout.CENTER);
                    mainFrame.revalidate();
                    mainFrame.repaint();
                } catch (InvalidWorldParameterException e1) {
                    JOptionPane.showMessageDialog(mainFrame, "Świat ma niepoprawny rozmiar.");
                }
                roundLabel.setText("Runda: 0");
            }
        });

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("World Files (*.vwrld)", "vwrld");
        fileChooser.setFileFilter(filter);

        JMenuItem openItem = new JMenuItem("Otwórz");
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

                fileChooser.setDialogTitle("Wybierz plik z zapisem");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int userSelection = fileChooser.showOpenDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        var worldMap = WorldFileHelper.ReadWorld(file.getAbsolutePath());
                        mainFrame.remove(world.getJPanel());
                        world = new VirtualWorld(worldMap.getSizeX(), worldMap.getSizeY());
                        for (int i = 0; i < worldMap.getSizeX(); i++) {
                            for (int j = 0; j < worldMap.getSizeY(); j++) {
                                var cell = world.getCell(i, j);
                                cell.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                        if (selectedCreature == null)
                                            return;
                                        int x = (int) cell.getClientProperty("x");
                                        int y = (int) cell.getClientProperty("y");

                                        try {
                                            world.setCell(x, y, selectedCreature);
                                            selectedCreature = VWCreatureFactory
                                                    .getCreature(selectedCreature.getSymbol());
                                        } catch (OccupiedFieldInsertException ex) {
                                            JOptionPane.showMessageDialog(mainFrame, "Pole jest już zajęte.");
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                });
                            }
                        }
                        world.setMap(worldMap);
                        mainFrame.add(world.getJPanel(), BorderLayout.CENTER);
                        mainFrame.revalidate();
                        mainFrame.repaint();
                        roundLabel.setText("Runda: 0");
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(mainFrame, "Nie udało się wczytać pliku.");
                    }
                }
            }
        });

        JMenuItem saveItem = new JMenuItem("Zapisz");
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

                fileChooser.setDialogTitle("Wybierz miejsce zapisu");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    WorldFileHelper.WriteWorld(file.getAbsolutePath(), world.getMap());
                    JOptionPane.showMessageDialog(mainFrame, "Zapis utworzony pomyślnie!");
                }
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

        mainFrame.setVisible(true);

        mainFrame.requestFocusInWindow();
    }
}