package choMiniProject;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private JLabel scoreLabel = new JLabel("점수 : 0");
    private ImageIcon backgroundIamgeIcon = new ImageIcon("images/scoreBoard.png");
    private Image backgroundIamge = backgroundIamgeIcon.getImage();
    private int score;
    public  ScorePanel(){
        setLayout(null); // 레이아웃 null로 설정
        scoreLabel.setBounds(60, 65, 200, 100);
        scoreLabel.setFont(new Font("MalgunGothic", Font.BOLD, 40)); // 폰트 크기 설정
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER); // 텍스트 가운데 정렬
        add(scoreLabel); // 점수 라벨 추가
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
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // 기본 그리기
        g.drawImage(backgroundIamge, 0, 0, getWidth(), getHeight(), null); // 배경 이미지 그리기
    }
}
