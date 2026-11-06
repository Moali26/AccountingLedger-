package org.example;

// Simple ANSI colors for terminal output. No external libraries needed.
public class CliColors {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String BOLD = "\u001B[1m";

    public static void title(String message) {
        System.out.println("\n" + BOLD + BLUE + message + RESET);
    }

    public static void option(String message) {
        System.out.println(CYAN + message + RESET);
    }

    public static void prompt(String message) {
        System.out.println(YELLOW + message + RESET);
    }

    public static void success(String message) {
        System.out.println(GREEN + message + RESET);
    }

    public static void error(String message) {
        System.out.println(RED + message + RESET);
    }

    public static void info(String message) {
        System.out.println(BLUE + message + RESET);
    }

    public static void transaction(Transaction transaction) {
        // Only color the display; Transaction.toString() remains unchanged for CSV saving.
        String color = transaction.getAmount() >= 0 ? GREEN : RED;
        System.out.println(color + transaction + RESET);
    }
}
