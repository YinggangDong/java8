package cn.dyg.functionalinterface;

/**
 * FunctionalInterfaceTest 类是 函数式接口测试类
 *
 * @author dongyinggang
 * @date 2020-10-12 19:58
 **/
public class FunctionalInterfaceTest {

    public static void main(String[] args) {

        //1.通过实现类实现函数式接口
        FunctionalInterfaceDemo interfaceImpl = new InterfaceImpl();
        interfaceImpl.abstractMethod();

        //2.匿名内部类实现函数式接口
        FunctionalInterfaceDemo demo = new FunctionalInterfaceDemo() {
            @Override
            public void abstractMethod() {
                System.out.println("通过内部类形式实现abstractMethod");
            }
        };
        demo.abstractMethod();

        //3.lambda实现函数式接口
        FunctionalInterfaceDemo lambda =
                ()-> System.out.println("lambda表达式实现abstractMethod");
        lambda.abstractMethod();

    }
}
