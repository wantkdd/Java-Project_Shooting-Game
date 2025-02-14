package choMiniProject;

import javax.swing.*;
import java.awt.*;

class Shooter extends JLabel {
    private static final int MOVE_SPEED = 5;
    public Shooter(GameGroundPanel panel) {
        setOpaque(true);
        setSize(50, 70);
        setLocation(panel.getWidth()/2 - 10, panel.getHeight() - 10);
        panel.add(this);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        g2d.fillRect(0,0,getWidth(),getHeight());
    }
    public void moveLeft() {
        int newX = getX() - MOVE_SPEED;
        if (newX > 0) {  // 왼쪽 경계 체크
            setLocation(newX, getY());
        }
    }
    public void moveRight() {
        int newX = getX() + MOVE_SPEED;
        if (newX < getParent().getWidth() - getWidth()) {  // 오른쪽 경계 체크
            setLocation(newX, getY());
        }
    }
}