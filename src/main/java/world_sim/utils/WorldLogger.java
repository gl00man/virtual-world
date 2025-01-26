package world_sim.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JTextArea;

public class WorldLogger {
    private static JTextArea _logTextArea;

    public static void setTextArea(JTextArea logTextArea) {
        _logTextArea = logTextArea;
    }

    public static void logMessage(String message) {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        _logTextArea.append("[" + now.format(formatter) + "]" + " " + message + "\n");
        _logTextArea.setCaretPosition(_logTextArea.getDocument().getLength());
    }
}
