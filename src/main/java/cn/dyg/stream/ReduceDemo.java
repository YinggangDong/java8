package cn.dyg.stream;

import java.util.Arrays;
import java.util.List;

/**
 * ReduceDemo 类是
 *
 * @author dongyinggang
 * @date 2021-05-06 13:58
 **/
public class ReduceDemo {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);

        Integer v1 = list.stream().reduce((x1, x2) -> x1 + x2).get();
        //15
        System.out.println("reduce 规约累加结果" + v1);

        Integer v2 = list.stream().reduce(1, (x1, x2) -> x1 + x2);
        //16
        System.out.println("reduce 规约初始值为1的累加结果" + v2);

        //串行流的 combiner 无效
        Integer v3 = list.stream().reduce(1, (x1, x2) -> {
                    System.out.println("stream accumulator: x1:" + x1 + "  x2:" + x2);
                    return x1 - x2;
                },
                (x1, x2) -> {
                    System.out.println("stream combiner: x1:" + x1 + "  x2:" + x2);
                    return x1 * x2;
                });
        // -14
        System.out.println(v3);

        //并行流先得到 0,-1,-2,-3,-4 然后串行执行乘法 得到0
        Integer v4 = list.parallelStream().reduce(1, (x1, x2) -> {
                    System.out.println("stream accumulator: x1:" + x1 + "  x2:" + x2);
                    return x1 - x2;
                },
                (x1, x2) -> {
                    System.out.println("stream combiner: x1:" + x1 + "  x2:" + x2);
                    return x1 * x2;
                });
        //
        System.out.println(v4);
    }
}
