package choMiniProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {

    private ScorePanel scorePanel;
    private ProfilePanel profilePanel;
    private GameGroundPanel gameGroundPanel;
    private Target target;

    public GamePanel(ProfilePanel profilePanel, ScorePanel scorePanel, Target target, GameGroundPanel gameGroundPanel){

        setLayout(new BorderLayout()); // 레이아웃 BorderLayout으로 설정
        setSize(1520, 1080); // 패널의 크기 설정

        this.profilePanel = profilePanel;
        this.scorePanel = scorePanel;
        this.target = target;
        this.gameGroundPanel = gameGroundPanel;

        splitPanel(); // 패널을 화면에 분리하여 추가
    }

    private void splitPanel() {
        JSplitPane rPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JSplitPane hPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); // 좌우 분리 패널 생성
        rPane.setDividerLocation(100);
        OnOffPanel onOffPanel = new OnOffPanel();
        rPane.setTopComponent(onOffPanel);
        rPane.setBottomComponent(hPane);

        hPane.setDividerLocation(350); // 좌우 분리 기준 위치 설정
        hPane.setDividerSize(1); // 구분선 두께 설정
        add(rPane); // 메인 패널에 좌우 분리 패널 추가

        JSplitPane vPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // 상하 분리 패널 생성
        vPane.setDividerLocation(300); // 상하 분리 기준 위치 설정
        vPane.setDividerSize(1); // 구분선 두께 설정
        vPane.setTopComponent(scorePanel); // 상단 패널에 점수 패널 추가
        vPane.setBottomComponent(profilePanel); // 하단 패널에 고양이 상태 패널 추가
        profilePanel.setDefaultBoogie(); // 초기 고양이 평화 고양이로 설정
        profilePanel.setGreen();

        hPane.setLeftComponent(vPane); // 오른쪽에 상하 분리 패널 추가
        hPane.setRightComponent(gameGroundPanel); // 왼쪽에 게임 영역 패널 추가
    }
    public void startGame(){

    }
    public class OnOffPanel extends JPanel{
        private ImageIcon onOffImageIcon = new ImageIcon("images/onOffButton.png");
        public OnOffPanel(){

            setLayout(null);

            Image originalImage = onOffImageIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            JButton rButton = new JButton(scaledIcon);
            rButton.addMouseListener(new MouseAdapter() { //마우스 리스너 추가
                @Override
                public void mouseClicked(MouseEvent e) { //클릭 시
                    startGame(); // 게임 시작
                }
            });

            rButton.setBounds(0,0,100,100);
            add(rButton);
            setVisible(true);
        }

    }

}
