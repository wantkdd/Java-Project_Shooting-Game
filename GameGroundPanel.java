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
    private boolean isGameOver = false;
    private boolean isPaused = false;
    private HighScoreManager highScoreManager;
    private boolean isNewHighScore = false;

    public GameGroundPanel(Target target, ProfilePanel profilePanel, ScorePanel scorePanel) {
        setLayout(null);
        this.target = target;
        this.profilePanel = profilePanel;
        this.scorePanel = scorePanel;
        this.highScoreManager = new HighScoreManager();

        setFocusable(true);
        requestFocusInWindow();

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

        // 초기 타겟 추가
        addTargetLabels(target.getMonsters());
    }
    class MyKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (isGameOver) {
                // 게임 오버 상태에서는 R키로만 재시작 가능
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    restartGame();
                }
                return;
            }

            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    if (!isPaused) {
                        shoot();
                    }
                    break;
                case KeyEvent.VK_A:
                    if (!isPaused) {
                        leftPressed = true;
                    }
                    break;
                case KeyEvent.VK_D:
                    if (!isPaused) {
                        rightPressed = true;
                    }
                    break;
                case KeyEvent.VK_P:
                    togglePause();
                    break;
                case KeyEvent.VK_R:
                    restartGame();
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
        if (!isGameOver && !isPaused) {
            BulletThread bullet = new BulletThread(this,
                    shooter.getX() + shooter.getWidth()/2,
                    shooter.getY(),
                    target);
            bullet.start();
        }
    }

    public void gameOver() {
        isGameOver = true;
        isPaused = false;

        // 하이스코어 체크
        int currentScore = scorePanel.getScore();
        isNewHighScore = highScoreManager.checkAndUpdateHighScore(currentScore);

        repaint();
    }

    public void restartGame() {
        isGameOver = false;
        isPaused = false;
        isNewHighScore = false;
        scorePanel.resetScore();
        scorePanel.resetLife();
        scorePanel.resetCombo();
        profilePanel.setRandomColors();
        profilePanel.setFunnyBoogie();  // 재시작 시 웃는 얼굴로
        target.generateInitialTargets();
        addTargetLabels(target.getMonsters());
        repaint();
    }

    public void togglePause() {
        isPaused = !isPaused;
        repaint();
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

        // 게임 오버 화면
        if (isGameOver) {
            g.setColor(new Color(0, 0, 0, 180)); // 반투명 검은색
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.RED);
            g.setFont(new Font("MalgunGothic", Font.BOLD, 80));
            String gameOverText = "게임 오버";
            FontMetrics fm = g.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(gameOverText)) / 2;
            g.drawString(gameOverText, x, getHeight() / 2 - 100);

            // 신기록 표시
            if (isNewHighScore) {
                g.setColor(Color.YELLOW);
                g.setFont(new Font("MalgunGothic", Font.BOLD, 50));
                String newRecordText = "★ 신기록! ★";
                x = (getWidth() - g.getFontMetrics().stringWidth(newRecordText)) / 2;
                g.drawString(newRecordText, x, getHeight() / 2 - 20);
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("MalgunGothic", Font.PLAIN, 40));
            String scoreText = "최종 점수: " + scorePanel.getScore();
            x = (getWidth() - g.getFontMetrics().stringWidth(scoreText)) / 2;
            g.drawString(scoreText, x, getHeight() / 2 + 40);

            // 하이스코어 표시
            g.setFont(new Font("MalgunGothic", Font.PLAIN, 30));
            String highScoreText = "최고 기록: " + highScoreManager.getHighScore();
            x = (getWidth() - g.getFontMetrics().stringWidth(highScoreText)) / 2;
            g.drawString(highScoreText, x, getHeight() / 2 + 90);

            g.setFont(new Font("MalgunGothic", Font.PLAIN, 30));
            String restartText = "R키를 눌러 재시작";
            x = (getWidth() - g.getFontMetrics().stringWidth(restartText)) / 2;
            g.drawString(restartText, x, getHeight() / 2 + 150);
        }

        // 일시정지 화면
        if (isPaused && !isGameOver) {
            g.setColor(new Color(0, 0, 0, 150)); // 반투명 검은색
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.YELLOW);
            g.setFont(new Font("MalgunGothic", Font.BOLD, 80));
            String pauseText = "일시정지";
            FontMetrics fm = g.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(pauseText)) / 2;
            g.drawString(pauseText, x, getHeight() / 2);

            g.setColor(Color.WHITE);
            g.setFont(new Font("MalgunGothic", Font.PLAIN, 30));
            String resumeText = "P키를 눌러 계속하기";
            x = (getWidth() - g.getFontMetrics().stringWidth(resumeText)) / 2;
            g.drawString(resumeText, x, getHeight() / 2 + 80);
        }
    }
}
