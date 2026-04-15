package JCMoveGuessR;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessMoveInputTest {

    // ── Pawn moves ────────────────────────────────────────────────────────────

    @Test
    void isValidPgnMove_pawnPush_valid() {
        assertTrue(ChessMoveInput.isValidPgnMove("e4"));
        assertTrue(ChessMoveInput.isValidPgnMove("a1"));
        assertTrue(ChessMoveInput.isValidPgnMove("h8"));
    }

    @Test
    void isValidPgnMove_pawnCapture_valid() {
        assertTrue(ChessMoveInput.isValidPgnMove("exd5"));
        assertTrue(ChessMoveInput.isValidPgnMove("axb4"));
        assertTrue(ChessMoveInput.isValidPgnMove("hxg6"));
    }

    // ── Piece moves ───────────────────────────────────────────────────────────

    @Test
    void isValidPgnMove_knightMove_valid() {
        assertTrue(ChessMoveInput.isValidPgnMove("Nf3"));
        assertTrue(ChessMoveInput.isValidPgnMove("Nc6"));
    }

    @Test
    void isValidPgnMove_bishopMove_valid() {
        assertTrue(ChessMoveInput.isValidPgnMove("Bc4"));
        assertTrue(ChessMoveInput.isValidPgnMove("Bb5"));
    }

    @Test
    void isValidPgnMove_rookMove_valid() {
        assertTrue(ChessMoveInput.isValidPgnMove("Rd1"));
        assertTrue(ChessMoveInput.isValidPgnMove("Ra8"));
    }

    @Test
    void isValidPgnMove_queenMove_valid() {
        assertTrue(ChessMoveInput.isValidPgnMove("Qd4"));
        assertTrue(ChessMoveInput.isValidPgnMove("Qh5"));
    }

    @Test
    void isValidPgnMove_kingMove_valid() {
        assertTrue(ChessMoveInput.isValidPgnMove("Ke2"));
    }

    @Test
    void isValidPgnMove_pieceCapture_valid() {
        assertTrue(ChessMoveInput.isValidPgnMove("Nxf3"));
        assertTrue(ChessMoveInput.isValidPgnMove("Bxc6"));
        assertTrue(ChessMoveInput.isValidPgnMove("Rxd4"));
    }

    @Test
    void isValidPgnMove_disambiguatedPieceMove_valid() {
        assertTrue(ChessMoveInput.isValidPgnMove("Nbd2")); // file disambiguation
        assertTrue(ChessMoveInput.isValidPgnMove("R1d3")); // rank disambiguation
    }

    // ── Castling ──────────────────────────────────────────────────────────────

    @Test
    void isValidPgnMove_kingsideCastle_valid() {
        assertTrue(ChessMoveInput.isValidPgnMove("O-O"));
    }

    @Test
    void isValidPgnMove_queensideCastle_valid() {
        assertTrue(ChessMoveInput.isValidPgnMove("O-O-O"));
    }

    // ── Promotion ─────────────────────────────────────────────────────────────

    @Test
    void isValidPgnMove_promotion_valid() {
        assertTrue(ChessMoveInput.isValidPgnMove("e8=Q"));
        assertTrue(ChessMoveInput.isValidPgnMove("a1=R"));
        assertTrue(ChessMoveInput.isValidPgnMove("h8=N"));
        assertTrue(ChessMoveInput.isValidPgnMove("b1=B"));
    }

    // ── Check / checkmate suffixes ─────────────────────────────────────────────

    @Test
    void isValidPgnMove_withCheckSuffix_valid() {
        assertTrue(ChessMoveInput.isValidPgnMove("Nf3+"));
        assertTrue(ChessMoveInput.isValidPgnMove("Qh5+"));
    }

    @Test
    void isValidPgnMove_withCheckmateSuffix_valid() {
        assertTrue(ChessMoveInput.isValidPgnMove("Qxf7#"));
    }

    // ── Invalid inputs ────────────────────────────────────────────────────────

    @Test
    void isValidPgnMove_emptyString_invalid() {
        assertFalse(ChessMoveInput.isValidPgnMove(""));
    }

    @Test
    void isValidPgnMove_singleChar_invalid() {
        assertFalse(ChessMoveInput.isValidPgnMove("e"));
        assertFalse(ChessMoveInput.isValidPgnMove("N"));
    }

    @Test
    void isValidPgnMove_garbage_invalid() {
        assertFalse(ChessMoveInput.isValidPgnMove("z9"));
        assertFalse(ChessMoveInput.isValidPgnMove("1234"));
        assertFalse(ChessMoveInput.isValidPgnMove("hello"));
    }

    @Test
    void isValidPgnMove_resultTokens_invalid() {
        assertFalse(ChessMoveInput.isValidPgnMove("1-0"));
        assertFalse(ChessMoveInput.isValidPgnMove("0-1"));
        assertFalse(ChessMoveInput.isValidPgnMove("*"));
    }
}
