package choMiniProject;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Target {
    private ImageIcon redMonsterIcon = new ImageIcon("images/redMonster.png");
    private ImageIcon greenMonsterIcon = new ImageIcon("images/greenMonster.png");
    private ImageIcon yellowMonsterIcon = new ImageIcon("images/yellowMonster.png");
    private ImageIcon brickIcon = new ImageIcon("images/brick.png");
    private Vector<JLabel> monsters = new Vector<>();

    public Target(){
        redMonsterIcon = new ImageIcon(redMonsterIcon.getImage().getScaledInstance(150,150, Image.SCALE_SMOOTH));
        greenMonsterIcon = new ImageIcon(greenMonsterIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        yellowMonsterIcon = new ImageIcon(yellowMonsterIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        brickIcon = new ImageIcon(brickIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));


    }
    public JLabel createMonsterLabel(ImageIcon monster) {
        JLabel monsterLabel = new JLabel(monster);
        monsterLabel.setSize(150, 150);
        return monsterLabel;
    }
    public Vector<JLabel> generateRandomLabel() { //랜덤 라벨 생성
        for(int i =0;i<6;i++){
            double random = Math.random(); // 랜덤 확률 생성
            if (random < 0.3) {
                JLabel redMonsterLabel = createMonsterLabel(redMonsterIcon);
                redMonsterLabel.setName("red");
                monsters.add(redMonsterLabel);
            } else if (random < 0.6) {
                JLabel greenMonsterLabel = createMonsterLabel(greenMonsterIcon);
                greenMonsterLabel.setName("green");
                monsters.add(greenMonsterLabel);
            } else if (random < 0.9) {
                JLabel yellowMonsterLabel = createMonsterLabel(yellowMonsterIcon);
                yellowMonsterLabel.setName("yellow");
                monsters.add(yellowMonsterLabel);
            }else if (random < 1) {
                JLabel brickLabel = createMonsterLabel(brickIcon);
                brickLabel.setName("brick");
                monsters.add(brickLabel);
            }
        }
        return monsters;
    }

}
