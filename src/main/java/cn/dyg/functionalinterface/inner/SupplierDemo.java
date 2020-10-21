package cn.dyg.functionalinterface.inner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

/**
 * SupplierDemo 类是 内置函数式接口-Supplier<T>：供给型接口（T get（））
 * 无输入,有输出
 *
 * @author dongyinggang
 * @date 2020-10-19 20:19
 **/
public class SupplierDemo {

    public static void main(String[] args) {
        SupplierDemo supplierDemo = new SupplierDemo();
        Random ran = new Random();
        //lambda重写get方法,获取十以内的随机数
        List<Integer> list = supplierDemo.supplier(10, () -> ran.nextInt(10));

        for (Integer i : list) {
            System.out.println(i);
        }
    }

    /**
     * 随机产生sum个数量得集合
     *
     * @param sum 集合内元素个数
     * @param sup Supplier接口
     * @return 随机产生的集合
     */
    private List<Integer> supplier(int sum, Supplier<Integer> sup) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < sum; i++) {
            list.add(sup.get());
        }
        return list;
    }
}
