package JCMoveGuessR;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// high score list functionality: read and write from a text-based file
// the table has the format: name | date | points | GameID
public class HighScores {

    private static final String[] FILE_EXTENSIONS = {"csv", "txt"};

    public void printHighScores() {
        List<String[]> highScores = readHighScoresFromFiles(FILE_EXTENSIONS);
        if (highScores.isEmpty()) {
            System.out.println("No high scores found.");
            return;
        }

        displayHighScores(highScores);
    }

    private List<String[]> readHighScoresFromFiles(String[] fileExtensions) {
        List<String[]> highScores = new ArrayList<>();
        for (String extension : fileExtensions) {
            String fileName = "highscores." + extension;
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(";");
                    highScores.add(data);
                }
                // If high scores are found in a file, break the loop
                if (!highScores.isEmpty()) {
                    break;
                }
            } catch (IOException e) {
                System.err.println("Error reading high scores from " + fileName + ": " + e.getMessage());
            }
        }
        return highScores;
    }

    private void displayHighScores(List<String[]> highScores) {
        System.out.println("High Scores:");
        System.out.println("──────────────");
        printTableHeader();
        System.out.println("──────────────────────────────────────");
        printScores(highScores);
    }

    private void printTableHeader() {
        System.out.printf("%-20s%-12s%-8s%-10s%n", "Name", "Date", "Score", "GameID");
    }

    private void printScores(List<String[]> highScores) {
        for (String[] score : highScores) {
            System.out.printf("%-20s%-12s%-8s%-10s%n", score[0], score[1], score[2], score[3]);
        }
    }
}
