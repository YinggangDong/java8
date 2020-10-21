package cn.dyg.methodref;

import java.util.Arrays;
import java.util.Comparator;

/**
 * ParticularObjInstanceMethod 类是 特定对象的实例方法的方法引用
 *
 * @author dongyinggang
 * @date 2020-10-20 18:17
 **/
public class ParticularObjInstanceMethod {

    /**
     * particularObjInstanceMethod 方法是 通过双冒号 调用 特定对象的实例方法
     *
     * @author dongyinggang
     * @date 2020/10/10 10:23
     */
    static void particularObjInstanceMethod(Person[] rosterAsArray) {
        System.out.println("2.调用特定对象的实例方法:");

        /**
         * 1.使用Comparator的实现类作为比较器
         *
         * 声明comparator时必须用Comparator<Person> ，不能是Comparator，否则编译期报错
         * wrong 1st argument type  Found Person[] required T[]
         * 意指rosterAsArray参数不满足sort方法的参数类型，默认参数类型是泛型T[] 而非实际类型Person[]
         *
         * 当comparator未指明其泛型的类型时，尽管其使用实现类进行实例化，编译期也不能通过这种隐式的关系推断出
         * 实际类型，因此期望入参是T[]
         *
         * 当比较器的泛型被显式的指明为Person时，这时sort方法会期望两个参数分别为
         * Person[]和Comparator<Person> c
         *
         * 疑问：
         * 1. Arrays.sort方法的参数类型jre推断过程是如何的？是通过比较器的泛型的实际类型来推断么？
         *  -- Java8中对Lambda表达式中方法参数的类型推断（一） ：https://blog.csdn.net/u013096088/article/details/69367260
         */
        Comparator<Person> comparator = new ComparatorImpl();
        Arrays.sort(rosterAsArray, comparator::compare);
        //作为Comparator的实现类,可以简写为以下形式,上面的方式实际在运行过程中会生成匿名类
        Arrays.sort(rosterAsArray, comparator);
        /**
         * 2.引用一个普通类的方法作为比较器
         *
         * 通过双冒号引用特定对象 comparatorProvider 的实例方法 compareByAge
         * 如果直接使用 ComparatorProvider::compareByAge 会提示在 static 上下文中调用非静态方法
         */
        ComparatorProvider comparatorProvider = new ComparatorProvider();
        Arrays.sort(rosterAsArray, comparatorProvider::compareByAge);
        System.out.println("排序完成：");
        for (Person person : rosterAsArray) {
            person.printPerson();
        }

    }
}

/**
 * Comparator接口的实现类
 */
class ComparatorImpl implements Comparator<Person> {

    @Override
    public int compare(Person a, Person b) {
        return a.getBirthday().compareTo(b.getBirthday());
    }
}

/**
 * 含compareByAge方法的类
 */
class ComparatorProvider {

    int compareByAge(Person a, Person b) {
        return a.getBirthday().compareTo(b.getBirthday());
    }
}
