//package choMiniProject;
//
//import javax.swing.*;
//import java.util.Vector;
//
//public class GenerateTargetThread extends Thread {
//    private boolean runFlag = true;
//    private Target target;
//    private GameGroundPanel gameGroundPanel;
//    private Vector<JLabel> targets = new Vector<>();
//    private ProfilePanel profilePanel;
//    private ScorePanel scorePanel;
//
//    public GenerateTargetThread(Target target, GameGroundPanel gameGroundPanel, ProfilePanel profilePanel,ScorePanel scorePanel){
//        this.target = target;
//        this.gameGroundPanel = gameGroundPanel;
//        this.profilePanel = profilePanel;
//        this.scorePanel = scorePanel;
//    }
//    @Override
//    public void run() { // 쓰레드 실행
//        try {
//            while (true) {
//                int gap = 1;
//                for(JLabel monster : target.generateRandomLabel()){
//                    if (monster != null) {
//                        monster.setLocation(10+(50*gap++),10); //위치 설정
//                        gameGroundPanel.addFallingLabels(monster); // 패널에 라벨 추가
//                        FallingThread fallingThread = new FallingThread(monster, gameGroundPanel, scorePanel, profilePanel); //FallingThread 생성
//                        gameGroundPanel.addFallingThreads(fallingThread); //게임 그라운드에 쓰레드 추가
//                        fallingThread.start(); //쓰레드 시작
//                    }
//                }
//                Thread.sleep(5000); // 5초마다 라벨 생성
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt(); // 인터럽트 상태 설정
//        }
//    }
//
//    synchronized public void stopRunning() {
//        runFlag = false;
//    }
//
//    synchronized public void checkStop() {
//        if (!runFlag) {
//            this.interrupt();
//        }
//    }
//
//}
