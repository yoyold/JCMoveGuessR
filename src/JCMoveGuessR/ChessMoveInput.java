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

    static boolean isValidPgnMove(String move) {
        if (move.length() < 2) return false;

        // Strip check/checkmate suffixes
        move = move.replaceAll("[+#]", "");

        // Castling
        if (move.equals("O-O") || move.equals("O-O-O")) return true;

        // Promotion (e.g., e8=Q)
        if (move.contains("=")) {
            String[] parts = move.split("=");
            return parts[0].matches("[a-h][1-8]") && parts[1].matches("[QRBN]");
        }

        // Piece move (e.g., Nf3, Bxc6, R1d3, Nbd2)
        if (Character.isUpperCase(move.charAt(0))) {
            return move.matches("[KQRBN][a-h1-8]?x?[a-h][1-8]");
        }

        // Pawn move (e.g., e4) or pawn capture (e.g., exd5)
        return move.matches("[a-h][1-8]") || move.matches("[a-h]x[a-h][1-8]");
    }
}