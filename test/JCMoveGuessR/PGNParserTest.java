package JCMoveGuessR;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PGNParserTest {

    @TempDir
    Path tempDir;

    private PGNParser parserFor(String content) throws IOException {
        Path file = tempDir.resolve("game.pgn");
        Files.writeString(file, content);
        PGNParser parser = new PGNParser();
        parser.setFilePath(file.toString());
        return parser;
    }

    // ── Basic move parsing ────────────────────────────────────────────────────

    @Test
    void getMoves_simpleMoves_returnsMoveTokens() throws IOException {
        PGNParser parser = parserFor("1. e4 e5 2. Nf3 Nc6 *");
        List<String> moves = parser.getMoves();
        assertEquals(List.of("e4", "e5", "Nf3", "Nc6"), moves);
    }

    @Test
    void getMoves_moveNumbers_areFiltered() throws IOException {
        PGNParser parser = parserFor("1. d4 2. Nf3");
        List<String> moves = parser.getMoves();
        assertFalse(moves.stream().anyMatch(t -> t.matches("\\d+\\.+")));
    }

    @Test
    void getMoves_resultTokens_areFiltered() throws IOException {
        PGNParser parser = parserFor("1. e4 e5 1-0");
        assertFalse(parser.getMoves().contains("1-0"));

        parser = parserFor("1. e4 e5 0-1");
        assertFalse(parser.getMoves().contains("0-1"));

        parser = parserFor("1. e4 e5 1/2-1/2");
        assertFalse(parser.getMoves().contains("1/2-1/2"));

        parser = parserFor("1. e4 e5 *");
        assertFalse(parser.getMoves().contains("*"));
    }

    @Test
    void getMoves_inlineComments_areStripped() throws IOException {
        PGNParser parser = parserFor("1. e4 {A strong opening move} e5 {Black mirrors} *");
        List<String> moves = parser.getMoves();
        assertEquals(List.of("e4", "e5"), moves);
    }

    @Test
    void getMoves_nagAnnotations_areStripped() throws IOException {
        PGNParser parser = parserFor("1. e4 $1 e5 $2 *");
        List<String> moves = parser.getMoves();
        assertEquals(List.of("e4", "e5"), moves);
    }

    @Test
    void getMoves_metadataHeaders_areIgnored() throws IOException {
        String pgn = "[Event \"Test\"]\n[Site \"Local\"]\n\n1. e4 e5 *";
        PGNParser parser = parserFor(pgn);
        List<String> moves = parser.getMoves();
        assertEquals(List.of("e4", "e5"), moves);
    }

    // ── Edge cases ────────────────────────────────────────────────────────────

    @Test
    void getMoves_emptyFile_returnsEmptyList() throws IOException {
        PGNParser parser = parserFor("");
        assertTrue(parser.getMoves().isEmpty());
    }

    @Test
    void getMoves_onlyMetadata_returnsEmptyList() throws IOException {
        PGNParser parser = parserFor("[Event \"Test\"]\n[Site \"Local\"]\n");
        assertTrue(parser.getMoves().isEmpty());
    }

    @Test
    void getMoves_multilineGame_returnsAllMoves() throws IOException {
        String pgn = "1. e4 e5\n2. Nf3 Nc6\n3. Bb5 a6\n*";
        PGNParser parser = parserFor(pgn);
        List<String> moves = parser.getMoves();
        assertEquals(List.of("e4", "e5", "Nf3", "Nc6", "Bb5", "a6"), moves);
    }

    @Test
    void getMoves_castling_isTreatedAsMove() throws IOException {
        PGNParser parser = parserFor("1. e4 e5 2. Nf3 Nc6 3. Bb5 a6 4. O-O *");
        List<String> moves = parser.getMoves();
        assertTrue(moves.contains("O-O"));
    }

    @Test
    void getMoves_nonexistentFile_returnsEmptyList() {
        PGNParser parser = new PGNParser();
        parser.setFilePath("/nonexistent/path/game.pgn");
        assertTrue(parser.getMoves().isEmpty());
    }

    @Test
    void getMoves_samplePgnGame_returnsExpectedMoveCount() throws IOException {
        // Mirrors the structure of sample.pgn shipped with the project
        String pgn =
            "[Event \"Sample\"]\n[Result \"1-0\"]\n\n" +
            "1. e4 e5 2. Nf3 Nc6 3. Bb5 a6 4. Ba4 Nf6 5. O-O Be7 " +
            "6. Re1 b5 7. Bb3 d6 8. c3 O-O 9. h3 Nb8 10. d4 Nbd7 1-0";
        PGNParser parser = parserFor(pgn);
        List<String> moves = parser.getMoves();
        // 10 moves × 2 sides = 20 move tokens
        assertEquals(20, moves.size());
    }
}
