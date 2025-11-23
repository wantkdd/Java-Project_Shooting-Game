package choMiniProject;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private JLabel scoreLabel = new JLabel("점수 : 0");
    private JLabel lifeLabel = new JLabel("생명 : 5");
    private JLabel comboLabel = new JLabel("");
    private ImageIcon backgroundIamgeIcon = new ImageIcon("images/scoreBoard.png");
    private Image backgroundIamge = backgroundIamgeIcon.getImage();
    private int score;
    private int life = 5;
    private int combo = 0;  // 콤보 카운터
    private static final int CORRECT_COLOR_SCORE = 100;    // 맞는 색상 맞췄을 때
    private static final int WRONG_COLOR_PENALTY = 50;     // 잘못된 색상 맞췄을 때

    public  ScorePanel(){
        setLayout(null); // 레이아웃 null로 설정
        scoreLabel.setBounds(60, 15, 200, 100);
        scoreLabel.setFont(new Font("MalgunGothic", Font.BOLD, 32)); // 폰트 크기 설정
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER); // 텍스트 가운데 정렬
        add(scoreLabel); // 점수 라벨 추가

        lifeLabel.setBounds(60, 95, 200, 100);
        lifeLabel.setFont(new Font("MalgunGothic", Font.BOLD, 32)); // 폰트 크기 설정
        lifeLabel.setHorizontalAlignment(SwingConstants.CENTER); // 텍스트 가운데 정렬
        add(lifeLabel); // 생명 라벨 추가

        comboLabel.setBounds(60, 175, 200, 100);
        comboLabel.setFont(new Font("MalgunGothic", Font.BOLD, 28)); // 폰트 크기 설정
        comboLabel.setForeground(Color.ORANGE);  // 주황색으로 표시
        comboLabel.setHorizontalAlignment(SwingConstants.CENTER); // 텍스트 가운데 정렬
        add(comboLabel); // 콤보 라벨 추가
    }
    public void increase() { //점수 증가
        score++;
        scoreLabel.setText("점수: " + score); // 점수 라벨 갱신
    }
    public void increase(int amount) { //bigFish 위한 증가 메소드
        score += amount; // 점수 증가
        scoreLabel.setText("점수: " + score); // 점수 라벨 갱신
    }
    public int getScore() { // 현재 점수 반환
        return score;
    }

    public int getLife() { // 현재 생명 반환
        return life;
    }

    public void decreaseLife() { // 생명 감소
        life--;
        lifeLabel.setText("생명: " + life);
    }

    public void resetLife() { // 생명 초기화
        life = 5;
        lifeLabel.setText("생명: " + life);
    }

    public void resetScore() { // 점수 초기화
        score = 0;
        scoreLabel.setText("점수: " + score);
    }

    public int getCombo() {  // 콤보 반환
        return combo;
    }

    public void resetCombo() {  // 콤보 초기화
        combo = 0;
        updateComboLabel();
    }

    private void updateComboLabel() {  // 콤보 라벨 업데이트
        if (combo >= 3) {
            comboLabel.setText(combo + " COMBO!");
        } else {
            comboLabel.setText("");
        }
    }
    public void updateScore(String hitColor, Color currentTargetColor) {
        if (hitColor.equals("brick")) {
            // brick 맞췄을 때는 점수 변화 없음
            return;
        }

        Color hitColorObj = null;
        switch (hitColor) {
            case "red":
                hitColorObj = Color.RED;
                break;
            case "green":
                hitColorObj = Color.GREEN;
                break;
            case "yellow":
                hitColorObj = Color.YELLOW;
                break;
        }

        if (hitColorObj != null && hitColorObj.equals(currentTargetColor)) {
            // 맞는 색상 맞췄을 때
            combo++;  // 콤보 증가
            int baseScore = CORRECT_COLOR_SCORE;

            // 콤보 보너스 점수 계산
            if (combo >= 10) {
                baseScore = (int)(CORRECT_COLOR_SCORE * 3.0);  // 10콤보 이상: 300점
            } else if (combo >= 5) {
                baseScore = (int)(CORRECT_COLOR_SCORE * 2.0);  // 5콤보 이상: 200점
            } else if (combo >= 3) {
                baseScore = (int)(CORRECT_COLOR_SCORE * 1.5);  // 3콤보 이상: 150점
            }

            score += baseScore;
            updateComboLabel();
        } else {
            // 잘못된 색상 맞췄을 때
            score = Math.max(0, score - WRONG_COLOR_PENALTY); // 음수 방지
            combo = 0;  // 콤보 리셋
            updateComboLabel();
        }

        scoreLabel.setText("점수: " + score);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // 기본 그리기
        g.drawImage(backgroundIamge, 0, 0, getWidth(), getHeight(), null); // 배경 이미지 그리기
    }
}
