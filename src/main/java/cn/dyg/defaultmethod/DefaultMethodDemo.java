package cn.dyg.defaultmethod;

/**
 * DefaultMethodDemo 类是接口默认方法demo类
 * 参考内容：
 * 1.Java8 在接口的变化：
 * https://blog.csdn.net/axuanqq/article/details/82773631
 * 2.接口默认方法
 * https://blog.csdn.net/h294590501/article/details/80303722
 * 3.JAVA8学习5-接口默认方法（default）
 * https://blog.csdn.net/z_yemu/article/details/89312788?utm_medium=distribute.pc_relevant.none-task-blog-title-4&spm=1001.2101.3001.4242
 *
 * @author dongyinggang
 * @date 2020-10-10 16:58
 **/
public class DefaultMethodDemo implements MyInterface {


    public static void main(String[] args) {
        DefaultMethodDemo defaultMethodDemo = new DefaultMethodDemo();
        //1.接口的默认方法
        defaultMethodDemo.defaultMethod();
        //2.接口被重写的默认方法
        defaultMethodDemo.defaultMethodWaitOverride();
        //3.接口的静态方法,只能通过接口直接调用,实现类并不能重写该方法
        MyInterface.staticMethod();
        //4.实现类中有和接口静态方法重名的方法
        //  如果实例声明时使用了MyInterface就会报错,因为找不到唯一的方法,
        //  使用DefaultMethodDemo声明调用的实际是实现类自己的静态方法
        //  如果没有在当前实现类中定义staticMethod,就会提示
        //  "static method may be invoked on containing interface class only."
        //  因为实现类并不能够继承接口的静态方法
        defaultMethodDemo.staticMethod();
        //5.实现类重写的普通方法
        defaultMethodDemo.ordinaryMethod();

        //接口的全局变量
        System.out.println("通过接口访问" + MyInterface.INTERFACE_STATIC_VARIABLE);
        System.out.println("通过实现类访问" + DefaultMethodDemo.INTERFACE_STATIC_VARIABLE);
        //虽然标红,但实际是可以通过实现类的实例来访问接口的全局变量的,但不提倡这样写
        System.out.println("通过实现类实例访问" + defaultMethodDemo.INTERFACE_STATIC_VARIABLE);

    }

    /**
     * ordinaryMethod 方法是 普通方法
     *
     * @author dongyinggang
     * @date 2020/10/10 16:54
     */
    @Override
    public void ordinaryMethod() {
        System.out.println("DefaultMethodDemo类实现的ordinaryMethod");
    }

    /**
     * staticMethod 方法是 接口实现类尝试重写接口的static方法
     * 会发现并不能实现重写,加上 @Override 注解编译器会直接报错
     * "Method does not override method from its superclass"
     *
     * @author dongyinggang
     * @date 2020/10/12 9:08
     */
//    @Override
    public void staticMethod() {
        System.out.println("DefaultMethodDemo类尝试重写静态方法");
    }

    @Override
    public void defaultMethodWaitOverride() {
        System.out.println("DefaultMethodDemo类重写了默认方法");
    }
}
