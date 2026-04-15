package JCMoveGuessR;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @TempDir
    Path tempDir;

    private Path writePgn(String content) throws IOException {
        Path file = tempDir.resolve("game.pgn");
        Files.writeString(file, content);
        return file;
    }

    // ── Empty PGN ─────────────────────────────────────────────────────────────

    @Test
    void start_emptyPgn_returnsWithoutReadingInput() throws IOException {
        Path pgn = writePgn("");
        // No scanner input needed — the game should return early
        Scanner scanner = new Scanner("");
        assertDoesNotThrow(() -> new Game(pgn.toString()).start(scanner));
    }

    @Test
    void start_onlyMetadataPgn_returnsWithoutReadingInput() throws IOException {
        Path pgn = writePgn("[Event \"Test\"]\n[Result \"*\"]\n\n");
        Scanner scanner = new Scanner("");
        assertDoesNotThrow(() -> new Game(pgn.toString()).start(scanner));
    }

    // ── Player quits ─────────────────────────────────────────────────────────

    @Test
    void start_playerQuitsAfterFirstMove() throws IOException {
        // 1. e4 — white pawn to e4; player starts on d4 (safe). Player enters 'q'.
        Path pgn = writePgn("1. e4 *");
        Scanner scanner = new Scanner("q\n");
        assertDoesNotThrow(() -> new Game(pgn.toString()).start(scanner));
    }

    // ── Player survives all moves ─────────────────────────────────────────────

    @Test
    void start_playerSurvivesShortGame() throws IOException {
        // Two moves: e4 (white), e5 (black). Player stays on safe squares throughout.
        Path pgn = writePgn("1. e4 e5 *");
        // After e4: player on d4 (safe). Player moves to f5 (safe).
        // After e5: player on f5 (safe). Player moves to f4 (safe).
        Scanner scanner = new Scanner("f5\nf4\n");
        assertDoesNotThrow(() -> new Game(pgn.toString()).start(scanner));
    }

    // ── Piece lands on player ─────────────────────────────────────────────────

    @Test
    void start_pieceLandsOnPlayerSquare_gameEndsImmediately() throws IOException {
        // Player starts on d4. White's first move is d4 (pawn to d4).
        // The piece lands on the player's square → game over with no user input.
        Path pgn = writePgn("1. d4 *");
        Scanner scanner = new Scanner(""); // no input should be needed
        assertDoesNotThrow(() -> new Game(pgn.toString()).start(scanner));
    }

    // ── Player moves onto occupied square ────────────────────────────────────

    @Test
    void start_playerMovesOntoOccupiedSquare_gameEnds() throws IOException {
        // After e4, e4 is occupied. Player enters e4.
        Path pgn = writePgn("1. e4 *");
        Scanner scanner = new Scanner("e4\n");
        assertDoesNotThrow(() -> new Game(pgn.toString()).start(scanner));
    }

    // ── Invalid input is re-prompted ──────────────────────────────────────────

    @Test
    void start_invalidSquareInputRetried_thenQuit() throws IOException {
        Path pgn = writePgn("1. e4 *");
        // First entry is invalid; second is 'q' to quit
        Scanner scanner = new Scanner("zz\nq\n");
        assertDoesNotThrow(() -> new Game(pgn.toString()).start(scanner));
    }
}
