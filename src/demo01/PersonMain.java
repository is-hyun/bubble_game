package demo01;

public class PersonMain {
    public static void main(String[] args) {

        //Person person1 = new Person();
        Person person = new Person("홍",20);
        System.out.println(person.getAge());
        person.setName("홍길동");
        System.out.println(person.toString());

    }
}
