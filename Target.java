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
    private static final int ROWS = 3;
    private static final int COLS = 6;

    public Target() {
        redMonsterIcon = new ImageIcon(redMonsterIcon.getImage().getScaledInstance(150,150, Image.SCALE_SMOOTH));
        greenMonsterIcon = new ImageIcon(greenMonsterIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        yellowMonsterIcon = new ImageIcon(yellowMonsterIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        brickIcon = new ImageIcon(brickIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        generateInitialTargets();
    }
    public Vector<JLabel> getMonsters() {
        return monsters;
    }

    private JLabel createMonsterLabel(ImageIcon monster, String name) {
        JLabel monsterLabel = new JLabel(monster);
        monsterLabel.setSize(150, 150);
        monsterLabel.setName(name);
        return monsterLabel;
    }
    public void generateInitialTargets() {
        monsters.clear();
        // 정확히 3줄의 타겟 생성
        for(int row = 0; row < ROWS; row++) {
            for(int col = 0; col < COLS; col++) {
                double random = Math.random();
                JLabel monsterLabel;

                if (random < 0.3) {
                    monsterLabel = createMonsterLabel(redMonsterIcon, "red");
                } else if (random < 0.6) {
                    monsterLabel = createMonsterLabel(greenMonsterIcon, "green");
                } else if (random < 0.9) {
                    monsterLabel = createMonsterLabel(yellowMonsterIcon, "yellow");
                } else {
                    monsterLabel = createMonsterLabel(brickIcon, "brick");
                }
                monsters.add(monsterLabel);
            }
        }
    }

}


