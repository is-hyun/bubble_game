package _test04;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.image.BufferedImage;

@Getter
public class Player extends JLabel implements Moveable {

    private int x;
    private int y;

    private ImageIcon playerR;
    private ImageIcon playerL;

    // 플레이어 속도
    private final int SPEED = 4;
    private final int JUMP_SPPED = 2;
    private final int JUMP_HEIGHT = 65;

    // 플레이어 움직임 상태
    @Setter
    private boolean left;
    @Setter
    private boolean right;
    private boolean up;
    private boolean down;

    // 플레이어 벽 충돌 상태
    @Setter
    private boolean leftWallCrash;
    @Setter
    private boolean rightWallCrash;

    public Player() {
        initData();
        setInitLayout();
    }

    private void initData() {
        x = 55;
        y = 535;
        // 명시젹으로 false 할당
        left = false;
        right = false;
        up = false;
        down = false;

        playerR = new ImageIcon("images/playerR.png");
        playerL = new ImageIcon("images/playerL.png");
    }

    private void setInitLayout() {
        setSize(50, 50);
        setLocation(x, y);
        setIcon(playerR);
    }

    @Override
    public void left() {
        left = true;
        setIcon(playerL);
        new Thread(() -> {
            while (left) {
                x -= SPEED;
                setLocation(x, y);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @Override
    public void right() {
        right = true;
        setIcon(playerR);

        new Thread(() -> {
            while (right) {
                x += SPEED;
                setLocation(x, y);

                try {
                        Thread.sleep(10);
                } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @Override
    public void up() {
        up = true;
        new Thread(() -> {
           for (int i =0; i < JUMP_HEIGHT; i++) {
               y -= JUMP_SPPED;
               setLocation(x, y);
               try {
                   Thread.sleep(5); // 낙하속도보다 느리게
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
           up = false;
           down();
        }).start();
    }

    @Override
    public void down() {
        down = true;
        new Thread(()-> {
            for (int i =0; i < JUMP_HEIGHT; i++) {
                y += JUMP_SPPED;
                setLocation(x, y);
                try {
                    Thread.sleep(5); // 낙하속도보다 느리게
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            down = false;
        }).start();
    }

    public void fireBubble(BubbleFrame bubbleFrame) {
        Bubble bubble = new Bubble(this);
        bubbleFrame.add(bubble);
    }
}
