package choMiniProject;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

class BulletThread extends Thread {
    private JLabel bulletLabel;
    private GameGroundPanel gamePanel;
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

    private void removeBullet() {
        SwingUtilities.invokeLater(() -> {
            if (bulletLabel.getParent() != null) {
                gamePanel.remove(bulletLabel);
                gamePanel.repaint();
            }
        });
    }

    private synchronized void checkAndRegenerateTargets() {
        Vector<JLabel> monsters = target.getMonsters();
        boolean allInvisible = true;

        for (JLabel monster : monsters) {
            if (monster.isVisible()) {
                allInvisible = false;
                break;
            }
        }

        if (allInvisible) {
            SwingUtilities.invokeLater(() -> {
                target.generateInitialTargets();
                gamePanel.addTargetLabels(target.getMonsters());
                gamePanel.revalidate();
                gamePanel.repaint();
            });
        }
    }

    @Override
    public void run() {
        try {
            while (currentY > -20) {
                SwingUtilities.invokeLater(() -> {
                    bulletLabel.setLocation(initialX - 10, currentY);
                });

                // 현재 총알의 위치에서 충돌 체크
                Rectangle bulletBounds = new Rectangle(
                        initialX - 10,
                        currentY,
                        bulletLabel.getWidth(),
                        bulletLabel.getHeight()
                );

                boolean collision = false;
                JLabel hitTarget = null;

                synchronized (target.getMonsters()) {
                    for (JLabel targetLabel : target.getMonsters()) {
                        if (targetLabel.isVisible() &&
                                bulletBounds.intersects(targetLabel.getBounds())) {
                            hitTarget = targetLabel;
                            collision = true;
                            break;  // 첫 번째 충돌 발견 시 즉시 종료
                        }
                    }
                }

                if (collision && hitTarget != null) {
                    final JLabel finalHitTarget = hitTarget;
                    SwingUtilities.invokeLater(() -> {
                        // 타겟 처리
                        String targetColor = finalHitTarget.getName();
                        Color currentTargetColor = gamePanel.getProfilePanel().getCurrentColor();

                        if (targetColor.equals("brick")) {
                            gamePanel.getProfilePanel().updateColors();
                        } else {
                            if (targetColor.equals(getColorName(currentTargetColor))) {
                                gamePanel.getScorePanel().updateScore(targetColor, currentTargetColor);
                                gamePanel.getProfilePanel().updateColors();
                            } else {
                                gamePanel.getScorePanel().updateScore(targetColor, currentTargetColor);
                            }
                        }

                        finalHitTarget.setVisible(false);
                        gamePanel.remove(finalHitTarget);
                    });

                    // 총알 제거
                    removeBullet();

                    // 새로운 타겟 생성 체크
                    checkAndRegenerateTargets();

                    return;  // 쓰레드 즉시 종료
                }

                currentY -= BULLET_SPEED;
                Thread.sleep(3);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            removeBullet();
        }
    }
}