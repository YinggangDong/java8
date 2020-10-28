package cn.dyg.stream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * FileStreamDemo 类是 文件流测试
 *
 * @author dongyinggang
 * @date 2020-10-28 14:31
 **/
public class FileStreamDemo {

    public static void main(String[] args) {
        //输出当前目录下的文件列表,对应的是项目的根目录
        try {
            System.out.println("当前目录下的文件列表如下：");
            Files.list(new File(".").toPath()).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
