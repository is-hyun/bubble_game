package _test05;

public interface Moveable {
    // 추상 메서드
    void left();
    void right();
    void up();

    // Adapter 클래스가 너무 많이 생겨서 defalut 문법을 인터페이스에서 사용할 수 있도록 함
    // 즉, default 키워드를 사용하면 인터페이스 내에서 일반 메서드 구현 가능

    default void down() {};
}
