package JCMoveGuessR;

import java.util.Scanner;

public class ChessMoveInput {

    public static void enterNextMove() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your chess move in PGN notation: ");
        String move = scanner.nextLine().trim();

        if (isValidPgnMove(move)) {
            System.out.println("Valid PGN move: " + move);
        } else {
            System.out.println("Invalid PGN move. Please try again.");
        }
    }

    private static boolean isValidPgnMove(String move) {
        // basic validation for move length and format
        if (move.length() < 2) {
            return false;
        }

        // check if the move is a pawn promotion (e.g., e4=Q)
        if (move.contains("=")) {
            String[] parts = move.split("=");
            return parts[0].matches("[a-h][1-8]") && parts[1].matches("[Q|R|B|N]");
        }

        // check for the standard move format (e.g., Nf3, O-O)
        return move.matches("[K|Q|R|B|N|P][a-h][1-8]") || move.equals("O-O") || move.equals("O-O-O");
    }
}