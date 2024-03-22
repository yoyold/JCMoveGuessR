package JCMoveGuessR;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PGNParser {
    private String filePath;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, String> getMetadata() {
        Map<String, String> metadata = new HashMap<>();
        // Parsing logic for metadata
        return metadata;
    }

    public List<String> getMoves() {
        List<String> moves = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String moveText = "";
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    if (line.charAt(0) == '{') {
                        // Handle comments if needed
                    } else {
                        // move
                        moveText += line + " ";
                    }
                }
            }
            // Parse the move text
            String[] movesArray = moveText.split("\\s+");
            for (String move : movesArray) {
                moves.add(move);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moves;
    }
}