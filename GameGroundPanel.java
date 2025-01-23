package choMiniProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

public class GameGroundPanel extends JPanel {
    private ImageIcon backgroundIamgeIcon = new ImageIcon("images/gameGroundBackground.png");
    private Image backgroundIamge = backgroundIamgeIcon.getImage();
    private Target target;
    private ProfilePanel profilePanel;
    private ScorePanel scorePanel;
    private BulletThread bullet;
    private Shooter shooter;

    public GameGroundPanel(Target target, ProfilePanel profilePanel, ScorePanel scorePanel){
        setLayout(null);
        this.target = target;
        this.profilePanel = profilePanel;
        this.scorePanel = scorePanel;

        JTextField jt = new JTextField(100);
        jt.setBackground(Color.BLACK);
        jt.setOpaque(true);
        jt.setLocation(100,100);
        add(jt);
        setFocusable(true);

        // 컴포넌트 생성 후 크기와 위치 설정
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (shooter == null) {
                    shooter = new Shooter(GameGroundPanel.this);
                }
                shooter.setLocation(getWidth()/2 - shooter.getWidth()/2, getHeight() - 150);
            }
        });

        addKeyListener(new MyKeyListener());

        addTargetLabels(target.generateRandomLabel());

    }
    class MyKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_ENTER:
                    shoot();
                    break;
                case KeyEvent.VK_A:
                    shooter.moveLeft();
                    break;
                case KeyEvent.VK_D:
                    shooter.moveRight();
                    break;
            }
        }
    }
    public void addTargetLabels(Vector<JLabel> targets){
        for(int i=0;i<targets.size();i++){
            if(i<6){
                targets.get(i).setLocation(200*i,20);
                add(targets.get(i));
                repaint();
            }
        }
    }
    public void shoot() {
        if (bullet == null || !bullet.isAlive()) {
            bullet = new BulletThread(this,
                    shooter.getX() + shooter.getWidth()/2,
                    shooter.getY(),
                    target);
            bullet.start();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // 기본 그리기
        g.drawImage(backgroundIamge, 0, 0, getWidth(), getHeight(), null); // 배경 이미지 그리기
    }
}
