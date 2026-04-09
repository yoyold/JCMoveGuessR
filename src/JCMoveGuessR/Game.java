package JCMoveGuessR;

import java.util.List;
import java.util.Scanner;
import java.util.NoSuchElementException;

/**
 * Core game loop for JCMoveGuessR.
 * The player is a neutral piece on the board. After each PGN move they must
 * stay on (or move to) an unoccupied square without seeing the board.
 */
public class Game {
    private final String pgnFilePath;

    public Game(String pgnFilePath) {
        this.pgnFilePath = pgnFilePath;
    }

    public void start(Scanner scanner) {

        PGNParser parser = new PGNParser();
        parser.setFilePath(pgnFilePath);
        List<String> moves = parser.getMoves();

        if (moves.isEmpty()) {
            System.out.println("No moves found in the PGN file. Make sure the file is valid.");
            return;
        }

        ChessBoard board = new ChessBoard();
        int score = 0;

        System.out.println("\n--- Game Start ---");
        System.out.println("You are a neutral piece. After each chess move, enter a square to move to.");
        System.out.println("Avoid squares occupied by pieces. You cannot see the board - memorize it!");
        System.out.println("Valid input: a1-h8  |  Enter 'q' to quit.\n");

        String playerSquare = "d4"; // safe starting square (empty in initial position)
        System.out.println("You start on: " + playerSquare);

        boolean isWhite = true;
        for (String move : moves) {
            System.out.println("\n>> Chess move: " + move + " (" + (isWhite ? "White" : "Black") + ")");
            board.applyMove(move, isWhite);
            isWhite = !isWhite;

            // Check if a piece moved onto the player
            if (board.isOccupied(playerSquare)) {
                System.out.println("A piece moved onto your square " + playerSquare + "! Game over!");
                System.out.println("Score: " + score);
                return;
            }

            // Ask player for their next square
            String input;
            while (true) {
                System.out.print("You are on [" + playerSquare + "]. Enter your next square: ");
                input = scanner.nextLine().trim().toLowerCase();

                if (input.equals("q")) {
                    System.out.println("You quit. Score: " + score);
                    return;
                }
                if (!input.matches("[a-h][1-8]")) {
                    System.out.println("Invalid square. Enter a square like 'e4'.");
                    continue;
                }
                break;
            }

            if (board.isOccupied(input)) {
                System.out.println("Square " + input + " is occupied by a piece! Game over!");
                System.out.println("Score: " + score);
                return;
            }

            playerSquare = input;
            score++;
            System.out.println("Moved to " + playerSquare + ".  Score: " + score);
        }

        System.out.println("\nYou survived the entire game! Final score: " + score);
    }
}
