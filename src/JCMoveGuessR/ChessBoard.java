package JCMoveGuessR;

import java.util.HashSet;
import java.util.Set;

/**
 * Tracks chess board state and applies PGN moves.
 * board[file][rank]: file 0=a..7=h, rank 0=1..7=8
 * Uppercase = white pieces, lowercase = black, '.' = empty.
 */
public class ChessBoard {
    private final char[][] board = new char[8][8];

    public ChessBoard() {
        initBoard();
    }

    private void initBoard() {
        for (int f = 0; f < 8; f++)
            for (int r = 0; r < 8; r++)
                board[f][r] = '.';

        // White (rank 1 = index 0)
        board[0][0] = 'R'; board[7][0] = 'R';
        board[1][0] = 'N'; board[6][0] = 'N';
        board[2][0] = 'B'; board[5][0] = 'B';
        board[3][0] = 'Q'; board[4][0] = 'K';
        for (int f = 0; f < 8; f++) board[f][1] = 'P';

        // Black (rank 8 = index 7)
        board[0][7] = 'r'; board[7][7] = 'r';
        board[1][7] = 'n'; board[6][7] = 'n';
        board[2][7] = 'b'; board[5][7] = 'b';
        board[3][7] = 'q'; board[4][7] = 'k';
        for (int f = 0; f < 8; f++) board[f][6] = 'p';
    }

    /** Apply a PGN move token (e.g. "e4", "Nf3", "O-O"). isWhite = whose turn. */
    public void applyMove(String move, boolean isWhite) {
        move = move.replaceAll("[+#!?]", "");

        if (move.equals("O-O")) { applyCastle(isWhite, false); return; }
        if (move.equals("O-O-O")) { applyCastle(isWhite, true); return; }

        // Promotion: e8=Q
        char promotionPiece = 0;
        if (move.contains("=")) {
            char promo = move.charAt(move.length() - 1);
            promotionPiece = isWhite ? promo : Character.toLowerCase(promo);
            move = move.substring(0, move.indexOf('='));
        }

        // Determine piece type
        char piece;
        String rest;
        if (Character.isUpperCase(move.charAt(0))) {
            piece = isWhite ? move.charAt(0) : Character.toLowerCase(move.charAt(0));
            rest = move.substring(1);
        } else {
            piece = isWhite ? 'P' : 'p';
            rest = move;
        }

        rest = rest.replace("x", "");
        if (rest.length() < 2) return;

        String destStr = rest.substring(rest.length() - 2);
        int df = destStr.charAt(0) - 'a';
        int dr = destStr.charAt(1) - '1';
        if (df < 0 || df > 7 || dr < 0 || dr > 7) return;

        String disambig = rest.substring(0, rest.length() - 2);

        // Find the source square
        int sf = -1, sr = -1;
        outer:
        for (int f = 0; f < 8; f++) {
            for (int r = 0; r < 8; r++) {
                if (board[f][r] != piece) continue;
                if (!matchesDisambig(disambig, f, r)) continue;
                if (canReach(piece, f, r, df, dr, isWhite)) {
                    sf = f; sr = r;
                    break outer;
                }
            }
        }
        if (sf == -1) return;

        // En passant: pawn captures diagonally onto empty square
        if (Character.toUpperCase(piece) == 'P' && sf != df && board[df][dr] == '.') {
            board[df][sr] = '.';
        }

        board[df][dr] = (promotionPiece != 0) ? promotionPiece : piece;
        board[sf][sr] = '.';
    }

    private boolean matchesDisambig(String disambig, int f, int r) {
        if (disambig.isEmpty()) return true;
        if (disambig.length() == 2) {
            return f == disambig.charAt(0) - 'a' && r == disambig.charAt(1) - '1';
        }
        if (Character.isLetter(disambig.charAt(0))) {
            return f == disambig.charAt(0) - 'a';
        }
        return r == disambig.charAt(0) - '1';
    }

    private boolean canReach(char piece, int sf, int sr, int df, int dr, boolean isWhite) {
        int fileDiff = Math.abs(df - sf);
        int rankDiff = Math.abs(dr - sr);
        switch (Character.toUpperCase(piece)) {
            case 'P': {
                int dir = isWhite ? 1 : -1;
                int startRank = isWhite ? 1 : 6;
                if (sf == df) {
                    if (dr == sr + dir && board[df][dr] == '.') return true;
                    return sr == startRank && dr == sr + 2 * dir
                            && board[df][dr] == '.' && board[sf][sr + dir] == '.';
                } else {
                    return fileDiff == 1 && dr == sr + dir; // capture
                }
            }
            case 'N': return (fileDiff == 1 && rankDiff == 2) || (fileDiff == 2 && rankDiff == 1);
            case 'B': return fileDiff == rankDiff && pathClear(sf, sr, df, dr);
            case 'R': return (fileDiff == 0 || rankDiff == 0) && pathClear(sf, sr, df, dr);
            case 'Q': return ((fileDiff == 0 || rankDiff == 0) || fileDiff == rankDiff) && pathClear(sf, sr, df, dr);
            case 'K': return fileDiff <= 1 && rankDiff <= 1;
            default: return false;
        }
    }

    private boolean pathClear(int sf, int sr, int df, int dr) {
        int fs = Integer.signum(df - sf);
        int rs = Integer.signum(dr - sr);
        int f = sf + fs, r = sr + rs;
        while (f != df || r != dr) {
            if (board[f][r] != '.') return false;
            f += fs; r += rs;
        }
        return true;
    }

    private void applyCastle(boolean isWhite, boolean queenside) {
        int rank = isWhite ? 0 : 7;
        char king = isWhite ? 'K' : 'k';
        char rook = isWhite ? 'R' : 'r';
        board[4][rank] = '.';
        if (queenside) {
            board[2][rank] = king; board[3][rank] = rook; board[0][rank] = '.';
        } else {
            board[6][rank] = king; board[5][rank] = rook; board[7][rank] = '.';
        }
    }

    public boolean isOccupied(String square) {
        if (square == null || square.length() < 2) return false;
        int f = square.charAt(0) - 'a';
        int r = square.charAt(1) - '1';
        if (f < 0 || f > 7 || r < 0 || r > 7) return false;
        return board[f][r] != '.';
    }

    public Set<String> getOccupiedSquares() {
        Set<String> occupied = new HashSet<>();
        for (int f = 0; f < 8; f++)
            for (int r = 0; r < 8; r++)
                if (board[f][r] != '.')
                    occupied.add("" + (char)('a' + f) + (char)('1' + r));
        return occupied;
    }
}
