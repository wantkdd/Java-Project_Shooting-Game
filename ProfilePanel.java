package choMiniProject;

import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {
    private ImageIcon defaultBoogie = new ImageIcon("images/defaultBoogie.png");
    private ImageIcon funnyBoogie = new ImageIcon("images/funnyBoogie.png");
    private ImageIcon sadBoogie = new ImageIcon("images/sadBoogie.png");
    public ImageIcon profileImage = null;
    private JLabel currentColorBox = new JLabel();  // 현재 색상
    private JLabel nextColorBox = new JLabel();     // 다음 색상
    private Color currentColor;
    private Color nextColor;
    public static final Color[] GAME_COLORS = {
            Color.RED,
            Color.GREEN,
            Color.YELLOW
    };

    public ProfilePanel() {
        setBackground(Color.white);
        setDefaultBoogie();
        setLayout(null);

        // 프로필 이미지 설정
        JLabel profile = new JLabel(profileImage);
        profile.setBounds(60, 20, 200, 300);
        add(profile);

        // 현재 색상 박스 (큰 박스)
        currentColorBox.setBounds(60, 340, 200, 200);
        currentColorBox.setOpaque(true);
        add(currentColorBox);

        // 다음 색상 박스 (작은 박스)
        nextColorBox.setBounds(110, 560, 100, 100);
        nextColorBox.setOpaque(true);
        add(nextColorBox);

        // 초기 색상 설정
        setRandomColors();
    }

    public void setDefaultBoogie() {
        profileImage = defaultBoogie;
        repaint();
        revalidate();
    }

    public void setFunnyBoogie() {
        profileImage = funnyBoogie;
        repaint();
        revalidate();
    }

    public void setSadBoogie() {
        profileImage = sadBoogie;
        repaint();
        revalidate();
    }

    // 현재 색상 설정 메소드들 - 이전 colorBox 관련 메소드들을 대체
    public void setCurrentColor(Color color) {
        currentColor = color;
        currentColorBox.setBackground(color);
        repaint();
        revalidate();
    }

    // GamePanel에서 호출하는 초기 색상 설정
    public void setGreen() {
        setCurrentColor(Color.GREEN);
    }

    public void setYellow() {
        setCurrentColor(Color.YELLOW);
    }

    public void setRed() {
        setCurrentColor(Color.RED);
    }

    // 랜덤 색상 설정
    public void setRandomColors() {
        // 다음 색상이 있다면 현재 색상으로 설정
        if (nextColor != null) {
            currentColor = nextColor;
        } else {
            currentColor = getRandomColor();
        }

        // 새로운 다음 색상 설정
        nextColor = getRandomColor();

        // UI 업데이트
        currentColorBox.setBackground(currentColor);
        nextColorBox.setBackground(nextColor);
        repaint();
    }

    // 랜덤 색상 반환
    private Color getRandomColor() {
        int index = (int)(Math.random() * GAME_COLORS.length);
        return GAME_COLORS[index];
    }

    // 현재 색상 가져오기
    public Color getCurrentColor() {
        return currentColor;
    }

    // 다음 색상 가져오기
    public Color getNextColor() {
        return nextColor;
    }

    // 색상 변경 (brick 히트 시 호출)
    public void updateColors() {
        setRandomColors();
    }
}