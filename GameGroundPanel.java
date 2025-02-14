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
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private MovementThread movementThread;

    public GameGroundPanel(Target target, ProfilePanel profilePanel, ScorePanel scorePanel) {
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

        movementThread = new MovementThread();
        movementThread.start();

        addKeyListener(new MyKeyListener());

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

        // 초기 타겟 추가
        addTargetLabels(target.getMonsters());
    }
    class MyKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    shoot();
                    break;
                case KeyEvent.VK_A:
                    leftPressed = true;
                    break;
                case KeyEvent.VK_D:
                    rightPressed = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    leftPressed = false;
                    break;
                case KeyEvent.VK_D:
                    rightPressed = false;
                    break;
            }
        }
    }

    public void addTargetLabels(Vector<JLabel> targets) {
        removeAll(); // 기존 타겟들 제거
        if (shooter != null) {
            add(shooter); // shooter 다시 추가
        }

        int rowCount = targets.size() / 6; // 몇 줄인지 계산
        for(int row = 0; row < rowCount; row++) {
            for(int col = 0; col < 6; col++) {
                int index = row * 6 + col;
                if(index < targets.size()) {
                    JLabel target = targets.get(index);
                    target.setLocation(200 * col, 20 + (row * 160)); // 160은 행간 간격
                    add(target);
                    target.setVisible(true);
                }
            }
        }
        revalidate();
        repaint();
    }
    public void shoot() {
        BulletThread bullet = new BulletThread(this,
                shooter.getX() + shooter.getWidth()/2,
                shooter.getY(),
                target);
        bullet.start();
    }
    private class MovementThread extends Thread {
        private boolean running = true;

        @Override
        public void run() {
            while (running) {
                if (leftPressed) {
                    shooter.moveLeft();
                }
                if (rightPressed) {
                    shooter.moveRight();
                }
                try {
                    Thread.sleep(10); // 움직임 갱신 간격
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void stopThread() {
            running = false;
        }
    }
    public void cleanup() {
        if (movementThread != null) {
            movementThread.stopThread();
        }
    }
    public ScorePanel getScorePanel() {
        return scorePanel;
    }

    public ProfilePanel getProfilePanel() {
        return profilePanel;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // 기본 그리기
        g.drawImage(backgroundIamge, 0, 0, getWidth(), getHeight(), null); // 배경 이미지 그리기
    }
}
