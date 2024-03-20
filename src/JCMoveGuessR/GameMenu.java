package JCMoveGuessR;

import java.util.Scanner;

public class GameMenu {
    public static void displayMenu(){
        System.out.println("Welcome to JCMoveGuessR!");
        System.out.println("");
        String asciiArt =
                "       _  _____ __  __                 _____                     _____  \n" +
                        "      | |/ ____|  \\/  |               / ____|                   |  __ \\ \n" +
                        "      | | |    | \\  / | _____   _____| |  __ _   _  ___  ___ ___| |__) |\n" +
                        "  _   | | |    | |\\/| |/ _ \\ \\ / / _ \\ | |_ | | | |/ _ \\/ __/ __|  _  / \n" +
                        " | |__| | |____| |  | | (_) \\ V /  __/ |__| | |_| |  __/\\__ \\__ \\ | \\ \\ \n" +
                        "  \\____/ \\_____|_|  |_|\\___/ \\_/ \\___|\\_____\\__,_|\\___||___/___/_|  \\_\\ \n" +
                        "                                                                         ";

        System.out.println(asciiArt);
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            // Splash screen with ASCII art

            // Print start screen
            System.out.println("");
            System.out.println("Enter one of the following options:");
            System.out.println("1) to play a game");
            System.out.println("2) to read in a new pgn file");
            System.out.println("3) to view highscores");
            System.out.println("4) to close the program");
            System.out.print("Enter your choice: ");

            // Get user input for menu selection
            int choice = scanner.nextInt();

            // Process user choice
            switch (choice) {
                case 1:
                    // Logic to play a game
                    System.out.println("");
                    System.out.println("Starting a new game...");
                    // Add game logic here
                    break;
                case 2:
                    // Logic to read in a new PGN
                    System.out.println("");
                    System.out.println("Reading in a new PGN...");
                    // Add file reading logic
                    break;
                case 3:
                    // Logic to view highscores
                    System.out.println("");
                    System.out.println("Viewing highscores...");
                    HighScores Highscore = new HighScores();
                    Highscore.printHighScores();
                    break;
                case 4:
                    // Exit the program
                    System.out.println("");
                    System.out.println("Closing the program. Goodbye! \uD83D\uDC4B");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        // Close the scanner
        scanner.close();
    }
}
