package JCMoveGuessR;

import java.util.Scanner;

public class GameMenu {
    public static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("Welcome to JCMoveGuessR!");
            System.out.println();

            String asciiArt = "       _  _____ __  __                 _____                     _____  \n" +
                    "      | |/ ____|  \\/  |               / ____|                   |  __ \\ \n" +
                    "      | | |    | \\  / | _____   _____| |  __ _   _  ___  ___ ___| |__) |\n" +
                    "  _   | | |    | |\\/| |/ _ \\ \\ / / _ \\ | |_ | | | |/ _ \\/ __/ __|  _  / \n" +
                    " | |__| | |____| |  | | (_) \\ V /  __/ |__| | |_| |  __/\\__ \\__ \\ | \\ \\ \n" +
                    "  \\____/ \\_____|_|  |_|\\___/ \\_/ \\___|\\_____\\__,_|\\___||___/___/_|  \\_\\ \n";
            System.out.println(asciiArt);

            System.out.println("\nPlease choose an option:");
            System.out.println("1) Play a game");
            System.out.println("2) Read in a new PGN file");
            System.out.println("3) View highscores");
            System.out.println("4) Exit");

            int choice = getValidChoice(scanner, 1, 4);

            switch (choice) {
                case 1:
                    System.out.println("\nStarting a new game...");
                    // Add game logic here
                    break;
                case 2:
                    System.out.println("\nReading in a new PGN...");
                    // Add file reading logic
                    break;
                case 3:
                    System.out.println("\nViewing highscores...");
                    HighScores highscore = new HighScores();
                    highscore.printHighScores();
                    break;
                case 4:
                    if (confirmExit(scanner)) {
                        System.out.println("\nClosing the program. Goodbye! \uD83D\uDC4B");
                        isRunning = false;
                    }
                    break;
            }
        }

        scanner.close();
    }

    private static int getValidChoice(Scanner scanner, int min, int max) {
        int choice;
        do {
            System.out.print("Enter your choice (" + min + "-" + max + "): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            choice = scanner.nextInt();
            if (choice < min || choice > max) {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (choice < min || choice > max);
        return choice;
    }

    private static boolean confirmExit(Scanner scanner) {
        System.out.print("Are you sure you want to exit? (y/n): ");
        String confirm = scanner.next().toLowerCase();
        return confirm.equals("y");
    }
}
