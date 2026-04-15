package JCMoveGuessR;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class HighScoresTest {

    // HighScores reads "highscores.csv" / "highscores.txt" from the working
    // directory (Maven runs tests from the project root). We clean up after each
    // test so the files do not persist between runs.

    private static final Path CSV_FILE = Path.of("highscores.csv");
    private static final Path TXT_FILE = Path.of("highscores.txt");

    @AfterEach
    void cleanup() throws IOException {
        Files.deleteIfExists(CSV_FILE);
        Files.deleteIfExists(TXT_FILE);
    }

    // ── No score file present ─────────────────────────────────────────────────

    @Test
    void printHighScores_noFile_printsNoScoresMessage() {
        // Ensure neither file exists before the test
        try { Files.deleteIfExists(CSV_FILE); Files.deleteIfExists(TXT_FILE); }
        catch (IOException ignored) {}

        String output = captureOutput(() -> new HighScores().printHighScores());
        assertTrue(output.contains("No high scores found"));
    }

    // ── CSV file with valid rows ──────────────────────────────────────────────

    @Test
    void printHighScores_withCsvFile_displaysAllRows() throws IOException {
        Files.writeString(CSV_FILE,
            "Alice;2024-01-01;42;GAME001\n" +
            "Bob;2024-01-02;37;GAME002\n");

        String output = captureOutput(() -> new HighScores().printHighScores());
        assertTrue(output.contains("Alice"));
        assertTrue(output.contains("Bob"));
        assertTrue(output.contains("42"));
        assertTrue(output.contains("GAME001"));
    }

    @Test
    void printHighScores_withCsvFile_displaysHeader() throws IOException {
        Files.writeString(CSV_FILE, "Alice;2024-01-01;42;GAME001\n");

        String output = captureOutput(() -> new HighScores().printHighScores());
        assertTrue(output.contains("Name"));
        assertTrue(output.contains("Score"));
    }

    // ── Rows with too few columns are silently skipped ────────────────────────

    @Test
    void printHighScores_incompleteRow_isSkipped() throws IOException {
        Files.writeString(CSV_FILE,
            "Alice;2024-01-01;42;GAME001\n" +  // valid
            "BadRow\n");                         // only 1 column

        // Should not throw and should still print Alice
        String output = captureOutput(() -> new HighScores().printHighScores());
        assertTrue(output.contains("Alice"));
    }

    // ── TXT fallback when no CSV exists ──────────────────────────────────────

    @Test
    void printHighScores_txtFallback_whenNoCsvExists() throws IOException {
        Files.writeString(TXT_FILE, "Charlie;2024-03-15;99;GAME003\n");

        String output = captureOutput(() -> new HighScores().printHighScores());
        assertTrue(output.contains("Charlie"));
        assertTrue(output.contains("99"));
    }

    @Test
    void printHighScores_csvTakesPrecedenceOverTxt() throws IOException {
        Files.writeString(CSV_FILE, "Alice;2024-01-01;42;GAME001\n");
        Files.writeString(TXT_FILE, "Bob;2024-01-02;1;GAME002\n");

        String output = captureOutput(() -> new HighScores().printHighScores());
        assertTrue(output.contains("Alice"));
        // Bob comes from txt which should be skipped once csv is found
        assertFalse(output.contains("Bob"));
    }

    // ── Helper ───────────────────────────────────────────────────────────────

    private String captureOutput(Runnable action) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));
        try {
            action.run();
        } finally {
            System.setOut(original);
        }
        return buffer.toString();
    }
}
