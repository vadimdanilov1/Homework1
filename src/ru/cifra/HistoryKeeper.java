package ru.cifra;

import java.io.*;

public class HistoryKeeper {
    private final String filename;

    public HistoryKeeper(String filename) {
        this.filename = filename;
    }

    public void saveToHistory(String record) {
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(record + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении истории.");
        }
    }

    public void showHistory() {
        System.out.println("\nИстория операций:");
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isEmpty = true;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                isEmpty = false;
            }
            if (isEmpty) {
                System.out.println("История операций пуста.");
            }
        } catch (IOException e) {
            System.out.println("Не удалось прочитать историю операций.");
        }
    }

    public void clearHistory() {
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.print("");
            System.out.println("История операций очищена.");
        } catch (IOException e) {
            System.out.println("Ошибка при очистке истории.");
        }
    }
}

