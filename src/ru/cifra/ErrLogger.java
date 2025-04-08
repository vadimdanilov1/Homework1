package ru.cifra;

import java.io.*;
import java.time.LocalDateTime;

public class ErrLogger {
    private final String logFile;

    public ErrLogger(String logFile) {
        this.logFile = logFile;
    }

    public void log(String message) {
        try (FileWriter fw = new FileWriter(logFile, true)) {
            String timeStamped = LocalDateTime.now() + " - " + message;
            fw.write(timeStamped + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Не удалось записать лог.");
        }
    }

    public void showHistory() {
        System.out.println("\nИстория ошибок:");
        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            boolean isEmpty = true;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                isEmpty = false;
            }
            if (isEmpty) {
                System.out.println("История ошибок пуста.");
            }
        } catch (IOException e) {
            System.out.println("Не удалось прочитать историю ошибок.");
        }
    }

    public void clearHistory() {
        try (PrintWriter writer = new PrintWriter(logFile)) {
            writer.print("");
            System.out.println("История ошибок очищена.");
        } catch (IOException e) {
            System.out.println("Ошибка при очистке истории.");
        }
    }
}