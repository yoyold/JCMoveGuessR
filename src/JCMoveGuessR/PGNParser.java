package JCMoveGuessR;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PGNParser {
    // parse the game moves
    public static void parsePGN() {
        try (BufferedReader reader = new BufferedReader(new FileReader("game.pgn"))) {
            String line;
            String moveText = "";
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("[")) {
                    // metadata tag
                    int index = line.indexOf("\"");
                    String key = line.substring(1, index);
                    String value = line.substring(index + 1, line.length() - 2);
                    System.out.println(key + " = " + value);
                } else if (!line.isEmpty()) {
                    // move or comment
                    if (line.charAt(0) == '{') {
                        // comment within the game
                        continue;
                    } else {
                        // move
                        moveText += line + " ";
                    }
                }
            }
            // parse the move text
            String[] moves = moveText.split("\\s+");
            for (String move : moves) {
                System.out.print(move + " ");
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // parse the meta data for the game
    public static void ParseMetaData() {

    }
}