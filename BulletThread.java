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
            boolean hasHit = false; // 이미 충돌했는지 추적

            while (currentY > -20 && !hasHit) {
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

                JLabel hitTarget = null;

                synchronized (target.getMonsters()) {
                    for (JLabel targetLabel : target.getMonsters()) {
                        if (targetLabel.isVisible() &&
                                bulletBounds.intersects(targetLabel.getBounds())) {
                            hitTarget = targetLabel;
                            hasHit = true; // 충돌 플래그 설정
                            targetLabel.setVisible(false); // 즉시 invisible 처리로 중복 충돌 방지
                            break;  // 첫 번째 충돌 발견 시 즉시 종료
                        }
                    }
                }

                if (hasHit && hitTarget != null) {
                    final JLabel finalHitTarget = hitTarget;
                    SwingUtilities.invokeLater(() -> {
                        // 타겟 처리
                        String targetColor = finalHitTarget.getName();
                        Color currentTargetColor = gamePanel.getProfilePanel().getCurrentColor();

                        if (targetColor.equals("brick")) {
                            gamePanel.getProfilePanel().updateColors();
                        } else {
                            if (targetColor.equals(getColorName(currentTargetColor))) {
                                // 올바른 색상 맞춤
                                gamePanel.getScorePanel().updateScore(targetColor, currentTargetColor);
                                gamePanel.getProfilePanel().updateColors();
                            } else {
                                // 잘못된 색상 맞춤 - 점수 감소 및 생명 감소
                                gamePanel.getScorePanel().updateScore(targetColor, currentTargetColor);
                                gamePanel.getScorePanel().decreaseLife();

                                // 부기 표정 업데이트
                                int currentLife = gamePanel.getScorePanel().getLife();
                                gamePanel.getProfilePanel().updateBoogieExpression(currentLife);

                                // 생명이 0이 되면 게임 오버 체크
                                if (currentLife <= 0) {
                                    gamePanel.gameOver();
                                }
                            }
                        }

                        gamePanel.remove(finalHitTarget);
                        gamePanel.repaint();
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