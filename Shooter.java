package choMiniProject;

import javax.swing.*;
import java.awt.*;

class Shooter extends JLabel {
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
    public void moveLeft(){
        this.setLocation(getX()-5, getY());
        repaint();
    }
    public void moveRight(){
        this.setLocation(getX()+5, getY());
        repaint();
    }
}