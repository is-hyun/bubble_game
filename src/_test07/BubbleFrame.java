package _test07;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BubbleFrame extends JFrame {

    private JLabel backgroundMap;
    private Player player;

    public BubbleFrame() {
        initData();
        setInitLayout();
        addEventListener();

        // 플레이어 위치에 따라 픽셀 감지하는 백그라운드 서비스 객체
        new Thread(new BackgroundPlayerService(player)).start();
    }

    private void initData() {
        setTitle("버블보블");
        setSize(1000, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        backgroundMap = new JLabel(new ImageIcon("images/backgroundMapService.png"));
        setContentPane(backgroundMap); // 루트 패널에 JLabel 설정

        player = new Player();
    }

    private void setInitLayout() {
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null); // JFrame은 화면 정가운데에 생성하도록 설정

        add(player); // 루트 패널에 background 설정되어 있어서 바로 add 가능
        setVisible(true);
    }

    private void addEventListener() {
        // 키보드 이벤트 등록
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if (!player.isLeft() && !player.isLeftWallCrash()) {
                            player.left();
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!player.isRight() && !player.isRightWallCrash()) {
                            player.right();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        // 점프 중 / 낙하 중이면 무시
                        if (!player.isUp() && !player.isDown()) {
                            player.up();
                        }
                        break;
                    case KeyEvent.VK_SPACE:
                        player.fireBubble(BubbleFrame.this);
                        break;

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        player.setLeft(false);
                        break;
                    case KeyEvent.VK_RIGHT:
                        player.setRight(false);

                        break;
                    case KeyEvent.VK_UP:

                        break;
                    case KeyEvent.VK_DOWN:

                        break;
                }
            }
        });
    }

    // 테스트 코드
    public static void main(String[] args) {
        new BubbleFrame();
    }
}
