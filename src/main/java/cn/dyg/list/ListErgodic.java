package cn.dyg.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * ListErgodic 类是 list的遍历操作,结合lambda表达式
 *
 * @author dongyinggang
 * @date 2020-09-05 09:52
 **/
public class ListErgodic {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }

        //1.原始foreach循环遍历集合
        foreach(list);

        //2.lambda实现遍历
        lambdaErgodic(list);

        //3.迭代器实现遍历
        iteratorErgodic(list);

    }

    /**
     * foreach 方法是 原始foreach循环
     *
     * @param list 待遍历的对象
     * @author dongyinggang
     * @date 2020/9/8 11:07
     */
    private static void foreach(List<String> list) {

        System.out.println("原始foreach循环：");
        //原始foreach循环
        for (String s : list) {
            System.out.print(s + "->");
        }
        System.out.println();

    }

    /**
     * iteratorErgodic 方法是 通过迭代器进行遍历
     *
     * @param list 待遍历的对象
     * @author dongyinggang
     * @date 2020/9/9 11:18
     */
    private static void iteratorErgodic(List<String> list) {
        System.out.println("迭代器版：");
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            String item = iter.next();
            System.out.print(item + "->");
            if (("4").equals(item)) {
                System.out.println();
                System.out.println("测试forEachRemaining方法的开始位置cursor:");
                //会从5开始进行遍历
                iter.forEachRemaining(System.out::print);
                System.out.println("测试结束");
                //测试结束后,由于cursor已经到了最后一个元素,所以不会再进入where之内,遍历在此时结束
            }
        }
        System.out.println();

        //Itr的forEachRemaining方法
        iter = list.iterator();
        System.out.println("迭代器的forEachRemaining版：");
        iter.forEachRemaining(System.out::print);
        System.out.println();
    }

    /**
     * lambdaErgodic 方法是 lambda方式进行遍历
     *
     * @param list 要遍历的list对象
     * @author dongyinggang
     * @date 2020/9/9 10:59
     */
    private static void lambdaErgodic(List<String> list){
        /*
         * 使用 lambda 表达式以及函数操作(functional operation),
         * foreach的参数是Consumer<? super T>
         * Consumer是JDK提供的一个函数式编程接口，其待实现方法是accept方法，
         * 除此之外还有一个default修饰的andThen方法（函数式接口只能有一个未实现方法）
         *
         * 下面的示例就是通过lambda表达式实现了accept方法
         */
        System.out.println("lambda表达式简写版：");
        list.forEach((s) -> System.out.print(s + "->"));
        System.out.println();

        //上下两者等价,下面实际是匿名内部类
        System.out.println("匿名内部类版：");
        list.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.print(s + "->");
            }
        });
        System.out.println();

        //在 Java 8 中使用双冒号操作符(double colon operator)
        System.out.println("lambda表达式+双冒号操作符版：");
        list.forEach(System.out::print);
        System.out.println();
    }
}
