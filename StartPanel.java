package choMiniProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartPanel extends JPanel {
    private ImageIcon backgroundIamgeIcon = new ImageIcon("images/introBackground.png");
    private Image backgroundIamge = backgroundIamgeIcon.getImage();
    private ImageIcon titleButton = new ImageIcon("images/title.png"); // 타이틀 이미지
    private JLabel title = new JLabel(titleButton); // 타이틀 라벨 생성
    private ImageIcon startButton = new ImageIcon("images/startButton.png"); // 시작 버튼 이미지
    private JButton start = new JButton(startButton); // 시작 버튼 생성

    private GamePanel gamePanel;
    private GameFrame gameFrame;
    private GameGroundPanel gameGroundPanel;

    public StartPanel(GamePanel gamePanel, GameFrame gameFrame, GameGroundPanel gameGroundPanel){

        this.gamePanel = gamePanel;
        this.gameFrame = gameFrame;
        this.gameGroundPanel = gameGroundPanel;
        setLayout(null); // 레이아웃을 null로 설정

        title.setSize(1100, 300); // 타이틀 크기 설정
        title.setLocation(200, 50); // 타이틀 위치 설정
        add(title); // 타이틀 추가

        start.setSize(330, 140); // 버튼 크기 설정
        start.setLocation(590, 700); // 버튼 위치 설정
        addHoverEffect(start); // 마우스 호버 효과 추가
        start.setBorderPainted(false); // 테두리 제거
        start.setContentAreaFilled(false); // 배경 제거
        start.addMouseListener(new MouseAdapter() { //마우스 리스너 추가
            @Override
            public void mouseClicked(MouseEvent e) { //클릭 시
                gameFrame.showPanel("gamePanel"); // GamePanel로 화면 전환
                gameGroundPanel.requestFocusInWindow();
            }
        });
        add(start); // start 추가

        setVisible(true);
    }

    private void addHoverEffect(JButton button) { //호버 효과
        button.addMouseListener(new MouseAdapter() { // 마우스리스너 추가
            @Override
            public void mouseEntered(MouseEvent e) { // 마우스 올렸을 때
                button.setSize(new Dimension(button.getWidth() + 15, button.getHeight() + 15)); // 버튼 크기 확대
            }

            @Override
            public void mouseExited(MouseEvent e) { // 마우스 내렸을 때
                button.setSize(new Dimension(button.getWidth() - 15, button.getHeight() - 15)); // 버튼 크기 복원
            }
        });
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // 기본 그리기
        g.drawImage(backgroundIamge, 0, 0, getWidth(), getHeight(), null); // 배경 이미지 그리기
    }

}
