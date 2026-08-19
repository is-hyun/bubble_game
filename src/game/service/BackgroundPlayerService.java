package game.service;

import game.components.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// 플레이어의 벽 충돌 감지
// 메인 스레드 대신 백그라운드에서 계속 실행
public class BackgroundPlayerService implements Runnable {

    // Image / ImageIcoe - 좌표 값으로 픽셀 값 추출 불가

    private BufferedImage image;
    // >> 메모리에 픽셀 배열로 저장된 이미지
    // getRGB(x, y)로 특정 좌표의 픽셀 색상 추출 가능
    private Player player;

    // 생성자 주임(DI Dependency Injection)
    public BackgroundPlayerService(Player player) {
        this.player = player;

        try {
            image = ImageIO.read(new File("images/backgroundMapservice.png"));
        } catch (IOException e) {
            System.out.println("이미지 경로 및 파일명을 확인하세요");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        // 게임이 종료될 때까지 계속 실행

        while (true) {
            Color leftColor = new Color(image.getRGB(player.getX() + 5, player.getY() + 25));
            Color rightColor = new Color(image.getRGB(player.getX() + 60, player.getY() + 25));

            // =========================================================
            // TODO 1 - 바닥 인식을 위한 하단 RGB 추출
            // 플레이어 좌측 하단 RGB 추출
            int bottomLeft = image.getRGB(player.getX(), player.getY() + 55);
            // 플레이어 우측 하단 RGB 추출
            int bottomRight = image.getRGB(player.getX() + 60, player.getY() + 55);

            // System.out.println("좌측 하단 색상 감지 : " + bottomLeft);
            // >> -16776961(파랑), -65536(빨강), -1(하양)

            if (bottomLeft + bottomRight == -2) { // 둘 다 흰 색
                // 발 아래가 허공
                // 현재 점프/낙하 중이 아니라면 낙하 시작
                if (!player.isUp() && !player.isDown()) {
                    player.down();
                }
                // 발 아래가 바닥/층
                // 낙하를 중지
            } else {
                player.setDown(false);
            }
            // =========================================================


            // 왼쪽 벽 감지 판단 - 빨간색일 때 플레이어가 충돌
            if (isRed(leftColor)) {
                player.setLeftWallCrash(true);
                player.setLeft(false); // 움직임 해제
            } else {
                player.setLeftWallCrash(false);
            }

            // 오른쪽 벽 감지 판단 - 빨간색일 때 플레이어가 충돌
            if (isRed(rightColor)) {
                player.setRightWallCrash(true);
                player.setRight(false);
            } else {
                player.setRightWallCrash(false);
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private boolean isRed(Color color) {
        return color.getRed() == 255 && color.getGreen() == 0 && color.getBlue() == 0;
    }

}
