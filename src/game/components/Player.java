package game.components;


import game.BubbleFrame;
import game.interfaces.Moveable;
import game.state.PlayerWay;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

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
    // =========================================================
    // setter 추가
    @Setter
    private boolean down;
    // =========================================================

    // 플레이어 벽 충돌 상태
    @Setter
    private boolean leftWallCrash;
    @Setter
    private boolean rightWallCrash;

    // 플레이어 방향 상태
    private PlayerWay playerWay;

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

        playerWay = PlayerWay.RIGHT;
    }

    private void setInitLayout() {
        setSize(50, 50);
        setLocation(x, y);
        setIcon(playerR);
    }

    @Override
    public void left() {
        left = true;
        playerWay = PlayerWay.LEFT;

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
        playerWay = PlayerWay.RIGHT;

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
            for (int i = 0; i < JUMP_HEIGHT; i++) {
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
        new Thread(() -> {
            // TODO 0 - while문으로 교체
            // =========================================================
            while (down) {
                y += JUMP_SPPED;
                setLocation(x, y);
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // =========================================================
            down = false;
        }).start();
    }

    // 물방울 발사
    public void fireBubble(BubbleFrame bubbleFrame) {
        Bubble bubble = new Bubble(this);
        bubbleFrame.add(bubble);
    }
}
