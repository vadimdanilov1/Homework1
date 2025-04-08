package ru.cifra;

import java.util.Scanner;

public class Calculator {
    private static final String HISTORY_FILE = "historyOperations.txt";
    private static final String LOG_FILE = "error.log";
    private static final int ADD = 1;
    private static final int SUBTRACT = 2;
    private static final int MULTIPLY = 3;
    private static final int DIVIDE = 4;
    private static final int EXP = 5;

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.start();
    }

    public void start(){
        HistoryKeeper historyKeeper = new HistoryKeeper(HISTORY_FILE);
        ErrLogger errLogger = new ErrLogger(LOG_FILE);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("""
                     \nКалькулятор 
                    1. Выполнить операцию
                    2. Показать историю операций
                    3. Очистить историю операций
                    4. Показать историю ошибок
                    5. Очистить историю операций
                    6. Выход
                    Ввод:\t""");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    makeOperation(historyKeeper, scanner, errLogger);
                    break;
                case "2":
                    historyKeeper.showHistory();
                    break;
                case "3":
                    historyKeeper.clearHistory();
                    break;
                case "4":
                    errLogger.showHistory();
                    break;
                case "5":
                    errLogger.clearHistory();
                    break;
                case "6":
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    public void makeOperation(HistoryKeeper historyKeeper, Scanner scanner, ErrLogger errLogger) {
        int operation = getOperation(scanner);

        String op = switch (operation){
            case ADD -> "+";
            case SUBTRACT -> "-";
            case MULTIPLY -> "*";
            case DIVIDE -> "/";
            case EXP -> "^";
            default -> "Неверная операция";
        };
        double a = getNumber(scanner, "первое");
        double b = getNumber(scanner, "второе");

        try {
            double result = calculate(operation, a, b);
            String output = String.format("%.2f %s %.2f = %.2f", a, op, b, result);
            printResult(output, historyKeeper);
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
            errLogger.log("Ошибка вычисления: " + e.getMessage());
        }
    }

    private int getOperation(Scanner scanner) {
        System.out.println("Введите " + ADD + " для сложения, " + SUBTRACT + " для вычитания, "
                + MULTIPLY + " для умножения, " + DIVIDE + " для деления " + EXP + " для возведения в степень:");
        return scanner.nextInt();
    }

    private double getNumber(Scanner scanner, String order) {
        System.out.println("Введите " + order + " число:");
        return scanner.nextDouble();
    }

    private double calculate(int operation, double a, double b) {
        switch (operation) {
            case ADD: return a + b;
            case SUBTRACT: return a - b;
            case MULTIPLY: return a * b;
            case DIVIDE:
                if (b == 0) throw new IllegalArgumentException("Деление на ноль!");
                return a / b;
            case EXP: return Math.pow(a, b);
            default: throw new IllegalArgumentException("Неверная операция!");
        }
    }

    private void printResult(String output, HistoryKeeper historyKeeper) {
        System.out.println("Результат: " + output);

        historyKeeper.saveToHistory(output);
    }
}
