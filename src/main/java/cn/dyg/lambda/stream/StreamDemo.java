package cn.dyg.lambda.stream;

import java.util.Arrays;
import java.util.List;

/**
 * StreamDemo 类是 lambda和stream的结合Demo
 *
 * @author dongyinggang
 * @date 2020-10-13 17:17
 **/
public class StreamDemo {

    public static void main(String[] args) {

        /**
         * 打印list中每个元素的平方
         */
        //原实现方式
        List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5);
        for (Integer n : list) {
            int x = n * n;
            System.out.println(x);
        }
        //使用lambda表达式和JAVA8新特性流结合的实现形式
        /**
         * 代码解读：
         * list.stream()得到一个流
         * 调用Stream.map(Function<? super T, ? extends R> mapper) 方法，
         * 通过Lambda表达式完成对Function接口中apply方法的重写,
         * Stream.map()依旧返回Stream对象,
         * 该对象调用Stream.forEach(Consumer<? super T> action)方法
         * 通过lambda表达式进行方法引用
         */
        list.stream().map((n) -> n * n).forEach(System.out::println);

        /**
         * 计算给定数值中每个元素平方后的总和
         */
        //原实现方式
        int sum = 0;
        for (Integer n : list) {
            int x = n * n;
            sum += x;
        }
        System.out.println(sum);

        //lambda表达式
        /**
         * 方法解析：
         * 和上面样例有部分类似，从reduce方法开始解析
         * Stream的Optional<T> reduce(BinaryOperator<T> accumulator);
         * 方法参数是一个函数式接口BinaryOperator,它继承了函数式接口BiFunction，
         * BiFunction有一个抽象的apply方法
         * 返回值是Optional类，调用Optional类的get()方法
         */
        int lambdaSum = list.stream().map(n -> n * n).reduce((x, y) -> x + y).get();
        System.out.println(lambdaSum);
    }
}
