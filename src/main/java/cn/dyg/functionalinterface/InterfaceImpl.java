package cn.dyg.functionalinterface;

/**
 * InterfaceImpl 类是 函数式接口实现类
 *
 * @author dongyinggang
 * @date 2020-10-12 19:59
 **/
public class InterfaceImpl implements FunctionalInterfaceDemo {
    /**
     * abstractMethod 方法是 唯一的抽象接口
     *
     * @author dongyinggang
     * @date 2020/10/12 19:56
     */
    @Override
    public void abstractMethod() {
        System.out.println("InterfaceImpl实现的接口");
    }
}
