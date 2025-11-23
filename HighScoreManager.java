package choMiniProject;

import java.io.*;

public class HighScoreManager {
    private static final String HIGH_SCORE_FILE = "highscore.txt";
    private int highScore;

    public HighScoreManager() {
        loadHighScore();
    }

    // 하이스코어 불러오기
    private void loadHighScore() {
        try {
            File file = new File(HIGH_SCORE_FILE);
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                if (line != null) {
                    highScore = Integer.parseInt(line.trim());
                }
                reader.close();
            } else {
                highScore = 0;
            }
        } catch (IOException | NumberFormatException e) {
            highScore = 0;
        }
    }

    // 하이스코어 저장
    private void saveHighScore() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(HIGH_SCORE_FILE));
            writer.write(String.valueOf(highScore));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 현재 하이스코어 반환
    public int getHighScore() {
        return highScore;
    }

    // 새 점수 체크 및 하이스코어 갱신
    public boolean checkAndUpdateHighScore(int score) {
        if (score > highScore) {
            highScore = score;
            saveHighScore();
            return true;  // 신기록!
        }
        return false;
    }
}
