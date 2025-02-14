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

    public Target() {
        redMonsterIcon = new ImageIcon(redMonsterIcon.getImage().getScaledInstance(150,150, Image.SCALE_SMOOTH));
        greenMonsterIcon = new ImageIcon(greenMonsterIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        yellowMonsterIcon = new ImageIcon(yellowMonsterIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        brickIcon = new ImageIcon(brickIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        generateInitialTargets(); // 초기 3줄의 타겟 생성
    }
    public Vector<JLabel> getMonsters() {
        return monsters;
    }

    public JLabel createMonsterLabel(ImageIcon monster) {
        JLabel monsterLabel = new JLabel(monster);
        monsterLabel.setSize(150, 150);
        return monsterLabel;
    }
    public void generateInitialTargets() {
        monsters.clear();
        // 3줄의 타겟 생성
        for(int row = 0; row < 3; row++) {
            generateNewRow();
        }
    }

    public void generateRandomLabel() {
        monsters.clear();
        for(int i = 0; i < 6; i++) {
            double random = Math.random();
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
            } else {
                JLabel brickLabel = createMonsterLabel(brickIcon);
                brickLabel.setName("brick");
                monsters.add(brickLabel);
            }
        }
    }
    public Vector<JLabel> generateNewRow() {
        Vector<JLabel> newRow = new Vector<>();
        for(int i = 0; i < 6; i++) {
            double random = Math.random();
            JLabel monsterLabel;

            if (random < 0.3) {
                monsterLabel = createMonsterLabel(redMonsterIcon);
                monsterLabel.setName("red");
            } else if (random < 0.6) {
                monsterLabel = createMonsterLabel(greenMonsterIcon);
                monsterLabel.setName("green");
            } else if (random < 0.9) {
                monsterLabel = createMonsterLabel(yellowMonsterIcon);
                monsterLabel.setName("yellow");
            } else {
                monsterLabel = createMonsterLabel(brickIcon);
                monsterLabel.setName("brick");
            }
            newRow.add(monsterLabel);
        }
        monsters.addAll(newRow);
        return newRow;
    }

}


