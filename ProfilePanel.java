package choMiniProject;

import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {
    private ImageIcon defaultBoogie = new ImageIcon("images/defaultBoogie.png");
    private ImageIcon funnyBoogie = new ImageIcon("images/funnyBoogie.png");
    private ImageIcon sadBoogie = new ImageIcon("images/sadBoogie.png");
    public ImageIcon profileImage = null;
    private JLabel colorBox = new JLabel();

    public ProfilePanel(){
        setBackground(Color.white); // 배경색을 흰색으로 설정
        setDefaultBoogie();
        setLayout(null);
        JLabel profile = new JLabel(profileImage);
        profile.setBounds(60,20,200,300);
        add(profile);

        colorBox.setBounds(110,400,100,100);
        colorBox.setBackground(Color.green);
        colorBox.setOpaque(true);
        setGreen();
        add(colorBox);
    }

    public void setDefaultBoogie() {
        profileImage = defaultBoogie;
        repaint(); // 다시 그리기
        revalidate(); // 레이아웃을 다시 그리기
    }
    public void setFunnyBoogie() {
        profileImage = funnyBoogie;
        repaint(); // 다시 그리기
        revalidate(); // 레이아웃을 다시 그리기
    }
    public void setSadBoogie() {
        profileImage = sadBoogie;
        repaint(); // 다시 그리기
        revalidate(); // 레이아웃을 다시 그리기
    }
    public void setNextColor(){
        double random = Math.random();
        Color nextColor;
        if(random < 0.33){
        }

    }
    public void setGreen() {
        colorBox.setBackground(Color.green);
        repaint(); // 다시 그리기
        revalidate(); // 레이아웃을 다시 그리기
    }
    public void setYellow() {
        colorBox.setBackground(Color.yellow);
        repaint(); // 다시 그리기
        revalidate(); // 레이아웃을 다시 그리기
    }

    public void setRed() {
        colorBox.setBackground(Color.red);
        repaint(); // 다시 그리기
        revalidate(); // 레이아웃을 다시 그리기
    }
}
