package cn.dyg.functionalinterface.inner;

import java.util.function.Consumer;

/**
 * ConsumerDemo 类是 内置函数式接口-Consumer<T>：消费型接口（void accept(T t)）
 * 有入参，无出参
 *
 * @author dongyinggang
 * @date 2020-10-19 20:22
 **/
public class ConsumerDemo {

    public static void main(String[] args) {
        ConsumerDemo consumerDemo = new ConsumerDemo();
        //通过方法引用重写accept方法
        consumerDemo.consumer(10,System.out::println);
    }

    /**
     * consumer 方法是 输出入参
     *
     * @param money 入参
     * @param c 待重写accept方法的Consumer接口
     * @author dongyinggang
     * @date 2020/10/19 20:26
     */
    private void consumer(double money, Consumer<Double> c){
        c.accept(money);
    }
}
