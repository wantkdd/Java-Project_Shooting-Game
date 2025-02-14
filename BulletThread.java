package choMiniProject;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

class BulletThread extends Thread {
    private JLabel bulletLabel;
    private GameGroundPanel gamePanel;
    private boolean hit = false;
    private Target target;
    private final int initialX;
    private int currentY;
    private static final int BULLET_SPEED = 5;

    public BulletThread(GameGroundPanel panel, int startX, int startY, Target target) {
        this.gamePanel = panel;
        this.target = target;
        this.initialX = startX;
        this.currentY = startY;

        bulletLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.RED);
                g2d.fillOval(0, 0, getWidth(), getHeight());
            }
        };
        bulletLabel.setOpaque(false);
        bulletLabel.setSize(20, 20);
        bulletLabel.setLocation(initialX - 10, currentY);
        panel.add(bulletLabel);
        panel.repaint();
    }
    private String getColorName(Color color) {
        if (color.equals(Color.RED)) return "red";
        if (color.equals(Color.GREEN)) return "green";
        if (color.equals(Color.YELLOW)) return "yellow";
        return "";
    }

    private void checkAndRegenerateTargets() {
        boolean allInvisible = true;
        for (JLabel monster : target.getMonsters()) {
            if (monster.isVisible()) {
                allInvisible = false;
                break;
            }
        }

        if (allInvisible) {
            // 새로운 타겟 줄 생성
            Vector<JLabel> newRow = target.generateNewRow();
            // 게임 패널에 새로운 줄 추가
            gamePanel.addTargetLabels(target.getMonsters());
        }
    }
    @Override
    public void run() {
        while (currentY > -20 && !hit) {
            currentY -= BULLET_SPEED;
            bulletLabel.setLocation(initialX - 10, currentY);

            Rectangle bulletBounds = bulletLabel.getBounds();
            for(JLabel targetLabel : target.getMonsters()) {
                if (targetLabel.isVisible() && bulletBounds.intersects(targetLabel.getBounds())) {
                    hit = true;

                    String targetColor = targetLabel.getName();
                    Color currentTargetColor = gamePanel.getProfilePanel().getCurrentColor();

                    if (targetColor.equals("brick")) {
                        // brick을 맞췄을 때는 색상만 변경하고 점수는 변경하지 않음
                        gamePanel.getProfilePanel().updateColors();
                    } else {
                        // 일반 몬스터를 맞췄을 때
                        if (targetColor.equals(getColorName(currentTargetColor))) {
                            // 색상이 맞을 때만 점수 증가 및 색상 변경
                            gamePanel.getScorePanel().updateScore(targetColor, currentTargetColor);
                            gamePanel.getProfilePanel().updateColors();
                        } else {
                            // 색상이 틀릴 때는 감점만
                            gamePanel.getScorePanel().updateScore(targetColor, currentTargetColor);
                        }
                    }

                    targetLabel.setVisible(false);
                    gamePanel.remove(targetLabel);
                    gamePanel.repaint();

                    // 모든 타겟이 사라졌는지 확인
                    checkAndRegenerateTargets();
                    break;
                }
            }

            try {
                sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        gamePanel.remove(bulletLabel);
        gamePanel.repaint();
    }
}