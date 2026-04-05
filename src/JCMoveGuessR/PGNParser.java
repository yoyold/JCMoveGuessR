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
            StringBuilder moveText = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("[")) continue;
                moveText.append(line).append(" ");
            }
            // Remove inline comments {...} and NAG annotations like $1
            String text = moveText.toString().replaceAll("\\{[^}]*\\}", " ").replaceAll("\\$\\d+", "");
            for (String token : text.split("\\s+")) {
                if (token.isEmpty()) continue;
                if (token.matches("\\d+\\.+")) continue; // move numbers: 1. 1... 12.
                if (token.equals("1-0") || token.equals("0-1") || token.equals("1/2-1/2") || token.equals("*")) continue;
                moves.add(token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moves;
    }
}