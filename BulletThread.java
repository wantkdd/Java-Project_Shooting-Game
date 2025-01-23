package choMiniProject;

import javax.swing.*;
import java.awt.*;
class BulletThread extends Thread {
    private JLabel bulletLabel;
    private GameGroundPanel gamePanel;
    private boolean hit = false;
    private Target target;

    public BulletThread(GameGroundPanel panel, int startX, int startY, Target target) {
        this.gamePanel = panel; // gamePanel 초기화
        this.target = target;
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
        bulletLabel.setLocation(startX - 5, startY);
        panel.add(bulletLabel);
        panel.repaint();
    }

    @Override
    public void run() {
        while (bulletLabel.getY() > 0 && !hit) {
            bulletLabel.setLocation(bulletLabel.getX(), bulletLabel.getY() - 5);
            Rectangle bulletBounds = bulletLabel.getBounds();
            for(JLabel targetLabel : target.generateRandomLabel()){
                Rectangle targetBounds = targetLabel.getBounds();
                if (bulletBounds.intersects(targetBounds)) {
                    hit = true;
                    gamePanel.remove(targetLabel);
                    gamePanel.repaint();
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