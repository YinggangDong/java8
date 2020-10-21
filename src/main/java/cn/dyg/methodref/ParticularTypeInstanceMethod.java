package cn.dyg.methodref;

import java.util.Arrays;

/**
 * ParticularTypeInstanceMethod 类是 特定类型任意对象的实例方法的方法引用
 *
 * @author dongyinggang
 * @date 2020-10-20 18:47
 **/
public class ParticularTypeInstanceMethod {

    /**
     * particularTypeInstanceMethod 方法是 调用特定类型任意对象的实例方法
     *
     * @author dongyinggang
     * @date 2020/10/10 11:29
     */
    static void particularTypeInstanceMethod(Person[] rosterAsArray) {
        System.out.println("3.调用特定类型任意对象的实例方法:");
        //官方文档示例
        String[] stringArray = {"Barbara", "James", "Mary", "John",
                "Patricia", "Robert", "Michael", "Linda"};
        Arrays.sort(stringArray, String::compareToIgnoreCase);

        /**
         * 刚开始考虑调用的方法是 compareByAgeNonStatic(Person a, Person b)
         * 有两个参数，结果发现会出现编译错误，提示在静态上下文中调用了非静态变量
         *
         * 看了一下官方文档中使用的compareToIgnoreCase方法，发现其只有一个入参，
         * 然后就给 compareByAgeNonStatic 写了一个只有一个参数的重载方法，发现可以正常调用
         *
         * 当使用类名调用非静态方法时,接口的首个参数应当是调用方,即方法引用中类名的一个实例对象
         * 后面的其他参数则会作为方法入参
         *
         * 疑问：
         * 1. Arrays.sort的比较器调用方法为静态时可以有两个参数的原因
         * 实际是因为作为静态方法，他不需要通过类的实例调用，可以通过类直接进行调用，而实例方法必须通过实例调用，
         * 第一个入参就会被认为是调用方，第二个参数则作为入参Person b,因此形式为一个对象调用来和另一个对象比较
         *
         */
        //这里使用了类的名称，而不是具体的对象，尽管指定的是实例方法。
        // 使用这种形式时，函数式接口的第一个参数匹配调用对象，第二个参数匹配方法指定的参数。
        Arrays.sort(rosterAsArray, Person::compareByAgeNonStatic);
        //和上方等价
        Arrays.sort(rosterAsArray, (a, b) -> a.compareByAgeNonStatic(b));

        //如果调用 compareByAgeNonStatic(a,b) 实际是调用特定对象的实例方法,此时两个参数都是方法参数
        Arrays.sort(rosterAsArray, (a, b) -> new Person().compareByAgeNonStatic(a, b));
        Arrays.sort(rosterAsArray, new Person()::compareByAgeNonStatic);

        System.out.println("排序完成：");
        for (Person person : rosterAsArray) {
            person.printPerson();
        }
    }

    private void oneParamTest() {
        System.out.println("一个参数：");
    }

    private void twoParamTest(String b) {
        System.out.println("两个参数：" + b);
    }

    private void threeParamTest(String b, String c) {
        System.out.println("三个参数：" + b + "," + c);
    }

    public static void main(String[] args) {
        ParticularTypeInstanceMethod particularTypeInstanceMethod = new ParticularTypeInstanceMethod();

        /**
         * 通过方法引用调用实例方法时,首个参数作为调用方,不在被调用方法的参数列表中,只存在于接口中
         */
        TestOne testOne = ParticularTypeInstanceMethod::oneParamTest;
        testOne.testParam(particularTypeInstanceMethod);

        TestTwo test = (a, b) -> System.out.println("两个参数：" + b);
        test.testParam(particularTypeInstanceMethod, "参数B");

        TestTwo testTwo = ParticularTypeInstanceMethod::twoParamTest;
        testTwo.testParam(particularTypeInstanceMethod, "参数B");

        TestThree testThree = ParticularTypeInstanceMethod::threeParamTest;
        testThree.testParam(particularTypeInstanceMethod, "参数B", "参数C");
    }
}

interface TestOne {
    void testParam(ParticularTypeInstanceMethod a);
}

interface TestTwo {
    void testParam(ParticularTypeInstanceMethod a, String b);
}

interface TestThree {
    void testParam(ParticularTypeInstanceMethod a, String b, String c);
}