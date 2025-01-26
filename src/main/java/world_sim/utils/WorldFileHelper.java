package world_sim.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import world_sim.components.virtualWorld.exceptions.InvalidWorldParameterException;
import world_sim.creatures.CreatureMap;
import world_sim.creatures.VWCreatureFactory;
import world_sim.creatures.exceptions.InvalidCreatureParameterException;
import world_sim.creatures.exceptions.OccupiedFieldInsertException;

public class WorldFileHelper {
    public static void WriteWorld(String filePath, CreatureMap creatureMap) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(String.format("%s %s", creatureMap.getSizeX(), creatureMap.getSizeY()));
            writer.newLine();
            for (int i = 0; i < creatureMap.getCreaturesCount(); i++) {
                var creatureField = creatureMap.getField(i);
                var creature = creatureField.getCreature();
                writer.write(String.format("%s %s %s %s %s %s %s", creatureField.getX(), creatureField.getY(),
                        creature.getSymbol(), creature.getStrength(), creature.getInitiative(), creature.getAge(),
                        creature.getCloneChance()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Wystąpił błąd podczas zapisu: " + e.getMessage());
        }
    }

    public static CreatureMap ReadWorld(String filePath) throws NumberFormatException, InvalidWorldParameterException,
            OccupiedFieldInsertException, InvalidCreatureParameterException {
        CreatureMap creatureMap = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            boolean firstLine = true;
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineParts = line.split(" ");
                if (firstLine) {
                    creatureMap = new CreatureMap(Integer.parseInt(lineParts[0]),
                            Integer.parseInt(lineParts[0]));
                    firstLine = false;
                    continue;
                }

                creatureMap.addCreature(Integer.parseInt(lineParts[0]),
                        Integer.parseInt(lineParts[1]),
                        VWCreatureFactory.getCreature(Integer.parseInt(lineParts[3]), Integer.parseInt(lineParts[4]),
                                Integer.parseInt(lineParts[5]), Integer.parseInt(lineParts[6]),
                                lineParts[2]));
            }
        } catch (IOException e) {
            System.out.println("Błąd podczas wczytywania pliku: " + e.getMessage());
        }
        return creatureMap;
    }
}
