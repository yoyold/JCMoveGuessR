package JCMoveGuessR;

import java.util.Scanner;

public class GameMenu {
    public static void displayMenu(){
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            // Splash screen with ASCII art

            // Print start screen
            System.out.println("Welcome to JCMoveGuessR!");
            System.out.println("1. Play a Game");
            System.out.println("2. Read in a new PGN");
            System.out.println("3. View Highscores");
            System.out.println("4. Close the Program");
            System.out.print("Enter your choice: ");

            // Get user input for menu selection
            int choice = scanner.nextInt();

            // Process user choice
            switch (choice) {
                case 1:
                    // Logic to play a game
                    System.out.println("Starting a new game...");
                    // Add your game logic here
                    break;
                case 2:
                    // Logic to read in a new PGN
                    System.out.println("Reading in a new PGN...");
                    // Add your file reading logic here
                    break;
                case 3:
                    // Logic to view highscores
                    System.out.println("Viewing highscores...");
                    // Add your highscore viewing logic here
                    break;
                case 4:
                    // Exit the program
                    System.out.println("Closing the program. Goodbye!");
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
