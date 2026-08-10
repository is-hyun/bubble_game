package demo01;

import lombok.*;

// 롬복 사용
//@Getter
//@Setter
//@ToString
// 단축 버전
@Data

@NoArgsConstructor // 매개변수 없는 기본 생성자
@AllArgsConstructor // 전체 매개변수 생성자
public class Person {

    private String name;
    private Integer age;



}
