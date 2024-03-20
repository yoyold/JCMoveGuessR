package JCMoveGuessR;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// highscore list functionality: read and write from a textbased file
// the table has the format: name | date | points | GameID
public class HighScores {

    public void printHighScores() {
        List<String[]> highScores = readHighScoresFromFile("highscores.csv");
        if (highScores.isEmpty()) {
            highScores = readHighScoresFromFile("highscores.txt");
        }
        if (highScores.isEmpty()) {
            System.out.println("No highscores found.");
            return;
        }

        System.out.println("High Scores:");
        System.out.println("──────────────");

        // Print table header
        System.out.printf("%-20s%-12s%-8s%-10s%n", "Name", "Date", "Score", "GameID");
        System.out.println("──────────────────────────────────────");

        // Print high scores
        for (String[] score : highScores) {
            System.out.printf("%-20s%-12s%-8s%-10s%n", score[0], score[1], score[2], score[3]);
        }
    }

    private List<String[]> readHighScoresFromFile(String fileName) {
        List<String[]> highScores = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");
                highScores.add(data);
            }
        } catch (IOException e) {
            System.err.println("Error reading high scores: " + e.getMessage());
        }

        return highScores;
    }
}
