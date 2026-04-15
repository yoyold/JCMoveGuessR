package JCMoveGuessR;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ChessBoardTest {

    private ChessBoard board;

    @BeforeEach
    void setUp() {
        board = new ChessBoard();
    }

    // ── Initial board state ──────────────────────────────────────────────────

    @Test
    void initialBoard_whiteBackRankAllOccupied() {
        for (char file = 'a'; file <= 'h'; file++) {
            assertTrue(board.isOccupied("" + file + "1"), file + "1 should be occupied");
        }
    }

    @Test
    void initialBoard_whitePawnsOnRank2() {
        for (char file = 'a'; file <= 'h'; file++) {
            assertTrue(board.isOccupied("" + file + "2"), file + "2 should have white pawn");
        }
    }

    @Test
    void initialBoard_blackBackRankAllOccupied() {
        for (char file = 'a'; file <= 'h'; file++) {
            assertTrue(board.isOccupied("" + file + "8"), file + "8 should be occupied");
        }
    }

    @Test
    void initialBoard_blackPawnsOnRank7() {
        for (char file = 'a'; file <= 'h'; file++) {
            assertTrue(board.isOccupied("" + file + "7"), file + "7 should have black pawn");
        }
    }

    @Test
    void initialBoard_ranks3to6AreEmpty() {
        for (char file = 'a'; file <= 'h'; file++) {
            for (char rank = '3'; rank <= '6'; rank++) {
                assertFalse(board.isOccupied("" + file + rank), file + rank + " should be empty");
            }
        }
    }

    @Test
    void initialBoard_exactly32PiecesOccupied() {
        assertEquals(32, board.getOccupiedSquares().size());
    }

    // ── isOccupied edge cases ────────────────────────────────────────────────

    @Test
    void isOccupied_nullInput_returnsFalse() {
        assertFalse(board.isOccupied(null));
    }

    @Test
    void isOccupied_emptyString_returnsFalse() {
        assertFalse(board.isOccupied(""));
    }

    @Test
    void isOccupied_singleChar_returnsFalse() {
        assertFalse(board.isOccupied("a"));
    }

    @Test
    void isOccupied_outOfBoundsSquare_returnsFalse() {
        assertFalse(board.isOccupied("z9"));
        assertFalse(board.isOccupied("a0"));
        assertFalse(board.isOccupied("i1"));
    }

    // ── Pawn moves ───────────────────────────────────────────────────────────

    @Test
    void applyMove_whitePawnSinglePush() {
        board.applyMove("e3", true);
        assertTrue(board.isOccupied("e3"));
        assertFalse(board.isOccupied("e2"));
    }

    @Test
    void applyMove_whitePawnDoublePush() {
        board.applyMove("e4", true);
        assertTrue(board.isOccupied("e4"));
        assertFalse(board.isOccupied("e2"));
        assertFalse(board.isOccupied("e3"));
    }

    @Test
    void applyMove_blackPawnDoublePush() {
        board.applyMove("e5", false);
        assertTrue(board.isOccupied("e5"));
        assertFalse(board.isOccupied("e7"));
        assertFalse(board.isOccupied("e6"));
    }

    @Test
    void applyMove_pawnCapture_removesCapturingPawnAndCapturedPiece() {
        board.applyMove("e4", true);
        board.applyMove("d5", false);
        board.applyMove("exd5", true);

        assertTrue(board.isOccupied("d5"));   // white pawn lands on d5
        assertFalse(board.isOccupied("e4"));  // white pawn left e4
    }

    @Test
    void applyMove_pawnCapture_reducesPieceCountByOne() {
        board.applyMove("e4", true);
        board.applyMove("d5", false);
        int before = board.getOccupiedSquares().size();
        board.applyMove("exd5", true);
        assertEquals(before - 1, board.getOccupiedSquares().size());
    }

    // ── Knight moves ─────────────────────────────────────────────────────────

    @Test
    void applyMove_whiteKnightNf3() {
        board.applyMove("Nf3", true);
        assertTrue(board.isOccupied("f3"));
        assertFalse(board.isOccupied("g1"));
    }

    @Test
    void applyMove_whiteKnightNc3() {
        board.applyMove("Nc3", true);
        assertTrue(board.isOccupied("c3"));
        assertFalse(board.isOccupied("b1"));
    }

    @Test
    void applyMove_blackKnightNf6() {
        board.applyMove("Nf6", false);
        assertTrue(board.isOccupied("f6"));
        assertFalse(board.isOccupied("g8"));
    }

    @Test
    void applyMove_blackKnightNc6() {
        board.applyMove("Nc6", false);
        assertTrue(board.isOccupied("c6"));
        assertFalse(board.isOccupied("b8"));
    }

    // ── Bishop moves ─────────────────────────────────────────────────────────

    @Test
    void applyMove_whiteBishopAfterPawnOpening() {
        board.applyMove("e4", true);
        board.applyMove("Bc4", true);   // f1 → c4 (diagonal clear after e2 pawn moved)
        assertTrue(board.isOccupied("c4"));
        assertFalse(board.isOccupied("f1"));
    }

    // ── Rook moves ───────────────────────────────────────────────────────────

    @Test
    void applyMove_whiteRookAfterPawnAndKnightClearPath() {
        board.applyMove("a4", true);    // a2 pawn to a4
        board.applyMove("Ra3", true);   // Ra1 → a3 (a2 now empty)
        assertTrue(board.isOccupied("a3"));
        assertFalse(board.isOccupied("a1"));
    }

    // ── Check / checkmate suffix stripping ───────────────────────────────────

    @Test
    void applyMove_checkSuffixIsStripped() {
        board.applyMove("Nf3+", true);
        assertTrue(board.isOccupied("f3"));
        assertFalse(board.isOccupied("g1"));
    }

    @Test
    void applyMove_checkmateSuffixIsStripped() {
        board.applyMove("Nc3#", true);
        assertTrue(board.isOccupied("c3"));
    }

    @Test
    void applyMove_annotationSuffixesAreStripped() {
        board.applyMove("Nf3!?", true);
        assertTrue(board.isOccupied("f3"));
    }

    // ── Castling ─────────────────────────────────────────────────────────────

    @Test
    void applyMove_whiteKingsideCastle() {
        board.applyMove("O-O", true);
        assertTrue(board.isOccupied("g1"));   // king
        assertTrue(board.isOccupied("f1"));   // rook
        assertFalse(board.isOccupied("e1"));  // king left
        assertFalse(board.isOccupied("h1"));  // rook left
    }

    @Test
    void applyMove_whiteQueensideCastle() {
        board.applyMove("O-O-O", true);
        assertTrue(board.isOccupied("c1"));   // king
        assertTrue(board.isOccupied("d1"));   // rook
        assertFalse(board.isOccupied("e1"));
        assertFalse(board.isOccupied("a1"));
    }

    @Test
    void applyMove_blackKingsideCastle() {
        board.applyMove("O-O", false);
        assertTrue(board.isOccupied("g8"));
        assertTrue(board.isOccupied("f8"));
        assertFalse(board.isOccupied("e8"));
        assertFalse(board.isOccupied("h8"));
    }

    @Test
    void applyMove_blackQueensideCastle() {
        board.applyMove("O-O-O", false);
        assertTrue(board.isOccupied("c8"));
        assertTrue(board.isOccupied("d8"));
        assertFalse(board.isOccupied("e8"));
        assertFalse(board.isOccupied("a8"));
    }

    // ── getOccupiedSquares ───────────────────────────────────────────────────

    @Test
    void getOccupiedSquares_containsKnownInitialSquares() {
        Set<String> occupied = board.getOccupiedSquares();
        assertTrue(occupied.contains("e1")); // white king
        assertTrue(occupied.contains("e8")); // black king
        assertTrue(occupied.contains("d1")); // white queen
        assertTrue(occupied.contains("d8")); // black queen
    }

    @Test
    void getOccupiedSquares_doesNotContainEmptySquares() {
        Set<String> occupied = board.getOccupiedSquares();
        assertFalse(occupied.contains("e4"));
        assertFalse(occupied.contains("d5"));
    }

    @Test
    void getOccupiedSquares_afterCastle_reflectsNewPositions() {
        board.applyMove("O-O", true);
        Set<String> occupied = board.getOccupiedSquares();
        assertTrue(occupied.contains("g1"));
        assertTrue(occupied.contains("f1"));
        assertFalse(occupied.contains("e1"));
        assertFalse(occupied.contains("h1"));
    }

    // ── Ruy Lopez opening sequence (integration-style) ───────────────────────

    @Test
    void applyMoves_ruyLopezOpening_boardStateIsCorrect() {
        board.applyMove("e4", true);
        board.applyMove("e5", false);
        board.applyMove("Nf3", true);
        board.applyMove("Nc6", false);
        board.applyMove("Bb5", true);

        assertTrue(board.isOccupied("e4"));  // white pawn
        assertTrue(board.isOccupied("e5"));  // black pawn
        assertTrue(board.isOccupied("f3"));  // white knight
        assertTrue(board.isOccupied("c6"));  // black knight
        assertTrue(board.isOccupied("b5"));  // white bishop
        assertFalse(board.isOccupied("g1")); // knight left g1
        assertFalse(board.isOccupied("f1")); // bishop left f1
    }
}
