package MyTest.ch02;

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

    // 플레이어 움직임 상태
    @Setter
    private boolean left;
    @Setter
    private boolean right;
    private boolean up;
    private boolean down;

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
        right = true;
    }

    @Override
    public void left() {
        if (left) {
            return;
        }

        left = true;
        setIcon(playerL);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (left) {
                    x -= SPEED;
                    setLocation(x, y);

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void right() {
        if (right) {
            return;
        }

        right = true;
        setIcon(playerR);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (right) {
                    x += SPEED;
                    setLocation(x, y);

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void up() {
        if (up) {
            return;
        }

        up = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (true) {
                    for (int i = 0; i < 60; i++) {
                        y -= JUMP_SPPED;
                        setLocation(x, y);
                        try {
                            Thread.sleep(8);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    for (int i = 0; i < 60; i++) {
                        y += JUMP_SPPED;
                        setLocation(x, y);
                        try {
                            Thread.sleep(8);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    up = false;
                }
            }
        }).start();
    }

    @Override
    public void down() {

    }
}
