package cn.dyg.functionalinterface;

/**
 * FunctionalInterfaceDemo 接口是 函数式接口demo
 *
 * 任何接口，如果只包含唯一一个抽象方法，就是一个函数式接口，例： Callable、Runnable、Comparator
 *
 * FunctionalInterface 注解用来标识函数式接口
 * 1、该注解只能标记在"有且仅有一个抽象方法"的接口上。
 * 2、JDK8接口中的静态方法和默认方法，都不算是抽象方法。
 * 3、接口默认继承java.lang.Object，所以如果接口显示声明覆盖了Object中方法，那么也不算抽象方法。
 * 4、该注解不是必须的，如果一个接口符合"函数式接口"定义，那么加不加该注解都没有影响。加上该注解能够更好地让编译器进行检查。
 * 如果编写的不是函数式接口，但是加上了@FunctionInterface，那么编译器会报错。
 *
 * @author dongyinggang
 * @date 2020-10-12 19:54
 **/
@FunctionalInterface
public interface FunctionalInterfaceDemo {

    /**
     * abstractMethod 方法是 唯一的抽象接口
     *
     * @author dongyinggang
     * @date 2020/10/12 19:56
     */
    void abstractMethod();

    /**
     * equals 方法是 显式覆盖了Object的equals方法
     * Object的其他方法也可以显式覆盖,包括各种native方法
     *
     * @param obj 比较参数
     * @return 比较结果
     * @author dongyinggang
     * @date 2020/10/12 18:54
     */
    @Override
    boolean equals(Object obj);

}
