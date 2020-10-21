package cn.dyg.methodref;

import java.util.Arrays;
import java.util.Comparator;

/**
 * StaticMethod 类是 静态方法的方法引用
 *
 * @author dongyinggang
 * @date 2020-10-20 18:14
 **/
public class StaticMethod {

    /**
     * staticMethod 方法是 通过双冒号 引用静态方法 ContainingClass::staticMethodName
     * 本例是引用Person类的 compareByAge 方法
     *
     * @author dongyinggang
     * @date 2020/10/10 9:43
     */
    static void staticMethod(Person[] rosterAsArray) {
        System.out.println("1.调用静态方法：");

        /**
         * 年龄比较器类
         *
         * 实现了函数式接口Comparator
         *
         * 注：Comparator接口 有两个抽象方法 compare 和 equals方法
         * 而它被称为函数式接口的原因是：
         *    如果接口声明了一个覆盖java.lang.Object的全局方法之一的抽象方法，
         * 那么它不会计入接口的抽象方法数量中，因为接口的任何实现都将具有java.lang.Object
         * 或其他地方的实现。
         */
        class PersonAgeComparator implements Comparator<Person> {
            @Override
            public int compare(Person a, Person b) {
                //这里如果 参数1 > 参数2 时返回正数,则为升序排列,否则为降序排列
                return a.getBirthday().compareTo(b.getBirthday());
            }
        }

        /**
         * 1.通过局部内部类作为sort方法的比较器实现类
         */
        Arrays.sort(rosterAsArray, new PersonAgeComparator());
        System.out.println("通过局部内部类的方式,排序完成：");
        for (Person person : rosterAsArray) {
            person.printPerson();
        }

        /**
         * 2.通过匿名内部类方式实现
         *
         * 将泛型指定为Person类，并重写compare方法
         * 参数类型变为Person是可行的，因为是Object的子类
         */
        Arrays.sort(rosterAsArray, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return Person.compareByAge(o1, o2);
            }
        });

        /**
         * 3. 通过lambda表达式的方式作为Comparator的实现类，替代匿名内部类
         */
        Arrays.sort(rosterAsArray,
                (Person a, Person b) -> a.getBirthday().compareTo(b.getBirthday()));

        /**
         * 4.通过已存在的方法简化lambda表达式
         * 和3的区别只是变成调用已存在方法而非重写比较逻辑
         * 实际还是将通过lambda表达式替代了Comparator的匿名内部类的生成
         */
        Arrays.sort(rosterAsArray, (a, b) -> {
            return Person.compareByAge(a, b);
        });

        /**
         * 和上方等价，因为是一句话方法体，进行格式简化
         */
        Arrays.sort(rosterAsArray, (a, b) -> Person.compareByAge(a, b));

        /**
         * 4.调用已经存在的方法的最简形式
         *
         * 方法引用Person::compareByAge在语义上与lambda表达式相同(a, b) -> Person.compareByAge(a, b)。每个都有以下特征：
         *
         * - 它的形参列表是从复制Comparator<Person>.compare，这里是(Person, Person)。
         * - 它的主体调用该方法Person.compareByAge。
         */
        Arrays.sort(rosterAsArray, Person::compareByAge);
    }
}
