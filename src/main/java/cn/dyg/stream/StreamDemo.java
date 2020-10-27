package cn.dyg.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        String[] array = {"张三","李四","王五","张三丰"};
        Stream<String> namestrs = Stream.of(array);

        Stream<String> nameStrs3 = Stream.of("张三","李四","王五","张三丰");

        try {
            Stream<String> lines = Files.lines(Paths.get("file.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stream<Integer> stream5 = Stream.generate(new Random()::nextInt)
                .limit(10);
//        stream5.forEach(System.out::println);
        //流只能使用一次,不能进行重置
        stream5.filter((a)->a>0).map((a)-> "正整数："+a).collect(Collectors.toList())
        .forEach(System.out::println);

    }
}
