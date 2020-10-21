package cn.dyg.methodref;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Person 类是 测试对象类
 *
 * @author dongyinggang
 * @date 2020-10-09 14:46
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    /**
     * 性别
     */
    public enum Sex {
        //男
        MALE,
        //女
        FEMALE
    }

    /**
     * 名字
     */
    private String name;
    /**
     * 生日
     */
    private LocalDate birthday;
    /**
     * 性别
     */
    private Sex gender;
    /**
     * 邮箱地址
     */
    private String emailAddress;

    /**
     * compareByAge 方法是 静态的年龄比较
     *
     * @param a Person a
     * @param b Person b
     * @return 年龄比较结果
     * @author dongyinggang
     * @date 2020/10/10 11:25
     */
    public static int compareByAge(Person a, Person b) {
        return a.birthday.compareTo(b.birthday);
    }

    /**
     * compareByAgeNonStatic 方法是 非静态的年龄比较（即实例方法）
     *
     * @param a Person a
     * @param b Person b
     * @return 年龄比较结果
     * @author dongyinggang
     * @date 2020/10/10 11:22
     */
    public int compareByAgeNonStatic(Person a, Person b) {
        return a.birthday.compareTo(b.birthday);
    }

    /**
     * compareByAgeNonStatic 方法是 非静态的年龄比较（即实例方法）
     * 只有一个参数
     *
     * @param b Person b
     * @return 年龄比较结果
     * @author dongyinggang
     * @date 2020/10/10 11:22
     */
    public int compareByAgeNonStatic(Person b) {
        return this.birthday.compareTo(b.birthday);
    }

    /**
     * printPerson 方法是 输出人员信息
     *
     * @author dongyinggang
     * @date 2020/10/10 11:25
     */
    public void printPerson() {
        System.out.println("name:" + name + ",birthday:" + birthday);
    }

    /**
     * createRoster 方法是 初始化人员列表
     *
     * @return 人员列表
     * @author dongyinggang
     * @date 2020/10/10 11:26
     */
    public static List<Person> createRoster() {
        List<Person> list = new ArrayList<>();
        Person person = new Person("张三", LocalDate.now().minusYears(22), Sex.MALE, "zhangsan@163.com");
        Person person1 = new Person("李四", LocalDate.now().minusYears(23), Sex.FEMALE, "lisi@163.com");
        list.add(person);
        list.add(person1);
        return list;
    }
}

