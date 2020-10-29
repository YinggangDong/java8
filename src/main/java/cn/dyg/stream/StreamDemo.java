package cn.dyg.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * StreamDemo 类是 流的相关demo
 *
 * @author dongyinggang
 * @date 2020-10-27 18:10
 **/
public class StreamDemo {
    public static void main(String[] args) {
        String[] array = {"张三", "李四", "王五", "张三丰"};
        Stream<String> namestrs = Stream.of(array);

        Stream<String> nameStrs3 = Stream.of("张三", "李四", "王五", "张三丰");

        try {
            Stream<String> lines = Files.lines(Paths.get("file.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stream<Integer> stream5 = Stream.generate(new Random()::nextInt)
                .limit(10);
//        stream5.forEach(System.out::println);
        //流只能使用一次,也没有重置的可能性,可以理解为
        stream5.filter((a) -> a > 0).map((a) -> "正整数：" + a).
                collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("Stream重新赋值后：");
        Stream<Integer> stream6 = Stream.generate(new Random()::nextInt).limit(10);
        stream6.filter((a) -> a > 0).map((a) -> "正整数：" + a).
                collect(Collectors.toList()).forEach(System.out::println);

        Stream.generate(() -> new Random().nextInt() + "123")
                .limit(10).mapToInt((item) -> Integer.valueOf(item.length())).forEach(System.out::println);

        Optional accResult = Stream.of(1, 2, 3, 4).reduce((acc, item) -> {
            System.out.println("acc : " + acc);
            acc += item;
            System.out.println("item: " + item);
            System.out.println("acc+ : " + acc);
            System.out.println("--------");
            return acc;
        });
        System.out.println(accResult);

    }
}
