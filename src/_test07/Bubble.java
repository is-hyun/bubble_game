package _test07;

import lombok.Getter;

import javax.swing.*;

@Getter
public class Bubble extends JLabel implements Moveable {

    private int x;
    private int y;
    private Player player;
    private ImageIcon bubbleIcon;
    private ImageIcon bombIcon;
    private BackgroundBubbleService backgroundBubbleService;

    // 버블 이동 상태 플래스
    private static final int HORIZONTAL_DISTANCE = 400; // 최대 수평 이동거리
    private static final int BUBBLE_SPEED_MS = 1; // 이동 간격 (ms)
    private static final int SCREEN_TOP = 0; // 화면 상단 경계

    private boolean leftMoving;
    private boolean rightMoving;
    private boolean upMoving;

    public Bubble(Player player) {
        this.player = player;
        this.backgroundBubbleService = new BackgroundBubbleService(this);
        initData();
        setInitLayout();

        // 플레이어의 방향 상태에 따라 물방울 이동 방향 지정
        new Thread(() -> {
            if (player.getPlayerWay() == PlayerWay.LEFT) {
                left();
            } else if (player.getPlayerWay() == PlayerWay.RIGHT) {
                right();
            }
        }).start();
    }

    private void initData() {
        bubbleIcon = new ImageIcon("images/bubble.png");
        bombIcon = new ImageIcon("images/bomb.png");
    }

    private void setInitLayout() {
        x = player.getX();
        y = player.getY();
        setSize(50, 50);
        setLocation(x, y);
        setIcon(bubbleIcon);
    }

    @Override
    public void left() {
        leftMoving = true;
        for (int i = 0; i < HORIZONTAL_DISTANCE; i++) {
            if (backgroundBubbleService.leftWall()) {
                break;
            }
            x--;
            setLocation(x, y);
            try {
                Thread.sleep(BUBBLE_SPEED_MS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        leftMoving = false;
        up();
    }

    @Override
    public void right() {
        rightMoving = true;
        for (int i = 0; i < HORIZONTAL_DISTANCE; i++) {
            if (backgroundBubbleService.rightWall()) break;
            x++;
            setLocation(x, y);
            try {
                Thread.sleep(BUBBLE_SPEED_MS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        rightMoving = false;
        up();
    }

    @Override
    public void up() {
        upMoving = true;

        while (true) {
            if (backgroundBubbleService.topWall()) break;
            y--;
            setLocation(x, y);
            try {
                Thread.sleep(BUBBLE_SPEED_MS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        upMoving = false;
        explode();
    }

    private void explode() {
        try {
            Thread.sleep(3000);
            setIcon(bombIcon);

            Thread.sleep(1000);
            // 부모 컨포넌트에서 제거
            if (getParent() != null) {
                 this.setVisible(false);
                getParent().remove(this); // 메모리해제

                // 새로고침
                // getParent().repaint();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}