package choMiniProject;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private final ScorePanel scorePanel;
    private final ProfilePanel profilePanel;
    private final Target target;
    private final CardLayout cardLayout = new CardLayout();
    private final JMenuItem on = new JMenuItem("On");
    private final JMenuItem off = new JMenuItem("Off");
    private final Container c;

    private final int onOff = 0;
    private Clip clip;
    private final GameGroundPanel gameGroundPanel;

    public GameFrame() {
        setTitle("Monster Block Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(cardLayout);
        setSize(1520, 1080);

        profilePanel = new ProfilePanel();
        scorePanel = new ScorePanel();
        target = new Target();

        gameGroundPanel = new GameGroundPanel(target, profilePanel, scorePanel);
        GamePanel gamePanel = new GamePanel(profilePanel, scorePanel, target,gameGroundPanel);

        c = getContentPane();
        c.add("startPanel", new StartPanel(gamePanel, this, gameGroundPanel));
        c.add("gamePanel", gamePanel);
        cardLayout.show(c, "startPanel");

        makeMenu();

        setResizable(false);
        setVisible(true);
    }

    public void makeMenu() {
        JMenuBar mBar = new JMenuBar();
        setJMenuBar(mBar);

        JMenu soundMenu = new JMenu("Sound");
//        on.addActionListener(new MenuActionListener());
//        off.addActionListener(new MenuActionListener());
        soundMenu.add(on);
        soundMenu.addSeparator();
        soundMenu.add(off);
        mBar.add(soundMenu);
    }

    public void showPanel(String panel) {
        cardLayout.show(c, panel);
    }
//    public class MenuActionListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String soundSwitch = e.getActionCommand();
//            switch (soundSwitch) {
//                case "On":
//                    if (onOff == 0) {
//                        clip.start();
//                        onOff = 1;
//                    }
//                    break;
//                case "Off":
//                    if (onOff == 1) {
//                        clip.stop();
//                        onOff = 0;
//                    }
//                    break;
//            }
//        }
//    }
}

