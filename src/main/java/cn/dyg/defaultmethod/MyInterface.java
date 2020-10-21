package cn.dyg.defaultmethod;

/**
 * MyInterface 接口是 测试默认方法和静态方法的接口
 *
 * @author dongyinggang
 * @date 2020-10-10 16:52
 **/
public interface MyInterface {

    /**
     * 在interface里面的变量都是public static final 的
     *
     * 接口就是提供一种统一的协议, 而接口中的属性也属于协议中的成员。
     * 它们是公共的,静态的,最终的常量。相当于全局常量。
     *
     * 尽管不写public static final,也是默认常量
     */
    String INTERFACE_STATIC_VARIABLE = "静态变量";

    /**
     * defaultMethod 方法是 接口默认方法
     *
     * 接口的默认方法被引入的主要原因：
     *    以前创建了一个接口，并且已经被大量的类实现。
     *    如果需要再扩充这个接口的功能加新的方法，就会导致所有已经实现的子类需要重写这个方法。
     *    如果在接口中使用默认方法就不会有这个问题。
     *    所以从 JDK8 开始新加了接口默认方法，便于接口的扩展。
     *
     * @author dongyinggang
     * @date 2020/10/10 16:53
     */
    default void defaultMethod(){
        System.out.println("这是接口MyInterface的默认方法");
    }

    /**
     * defaultMethodWaitOverride 方法是 等待重写的接口默认方法
     *
     * @author dongyinggang
     * @date 2020/10/12 9:47
     */
    default void defaultMethodWaitOverride(){
        System.out.println("等待重写的默认方法");
    }

    /**
     * staticMethod 方法是 接口静态方法
     *
     * 在Java 8中，在接口中添加静态方法带来了一个限制 ：这些方法不能由实现它的类继承。
     * 这样做是有道理的，因为一个类可以实现多个接口。如果2个接口具有相同的静态方法，
     * 它们都将被继承，编译器就不知道要调用哪个接口。
     *
     * 参考：
     *  关于在java 8中，为什么不能调用当前类正在实现的接口的静态方法的解释
     *      https://blog.csdn.net/u012580143/article/details/81217732
     *
     * 疑问：
     *   引入的原因是什么？有什么源码级应用么？暂时个人理解是有部分方法实现可以通过static方法写在接口中，
     *   不需要必须写一个实现类来实现对应方法。提高代码的内聚性。
     *
     * 代码进化史：Java8接口静态方法应用
     *   https://my.oschina.net/geektao/blog/3156306
     *
     * @author dongyinggang
     * @date 2020/10/10 16:53
     */
    static void staticMethod(){
        System.out.println("这是接口的静态方法");
    }
    /**
     * ordinaryMethod 方法是 普通方法
     *
     * @author dongyinggang
     * @date 2020/10/10 16:54
     */
    void ordinaryMethod();
}
