package cn.dyg.defaultmethod;

/**
 * ExtendsAndImpl 类是 模拟既继承又实现接口且父类和接口中有重名的默认方法
 *
 * @author dongyinggang
 * @date 2020-10-12 13:22
 **/
public class ExtendsAndImpl extends DefaultMethodDemo implements MyInterface{

    public static void main(String[] args) {
        MyInterface myInterface = new ExtendsAndImpl();
        DefaultMethodDemo defaultMethodDemo = new ExtendsAndImpl();
        ExtendsAndImpl extendsAndImpl = new ExtendsAndImpl();
        //当 ExtendsAndImpl 未重写 defaultMethodWaitOverride 方法时,
        // 实际调用了 DefaultMethodDemo 重写的 defaultMethodWaitOverride方法
        //当 ExtendsAndImpl 重写 defaultMethodWaitOverride 方法时,
        // 实际调用了 ExtendsAndImpl 重写的 defaultMethodWaitOverride方法
        myInterface.defaultMethodWaitOverride();
        defaultMethodDemo.defaultMethodWaitOverride();
        extendsAndImpl.defaultMethodWaitOverride();

    }

    /**
     * defaultMethodWaitOverride 方法是 重写的 defaultMethodWaitOverride 方法
     * 实际完成了对 DefaultMethodDemo 类中方法的重写
     * 在本类中并不要求一定重写该方法，默认会调用 DefaultMethodDemo 的 defaultMethodWaitOverride 方法
     *
     * 在下面的ExtendsAndImpl2类中，因为 DefaultMethodDemo 未实现 MyInterface2
     * 所以会出现类似实现多个接口时的提示，要求必须重写默认方法
     *
     * @author dongyinggang
     * @date 2020/10/12 13:43
     */
    @Override
    public void defaultMethodWaitOverride(){
        System.out.println("ExtendsAndImpl重写了默认方法");
    }

}

class ExtendsAndImpl2 extends DefaultMethodDemo implements MyInterface2{

    /**
     * defaultMethod 方法是 要求必须重写默认方法
     *
     * @author dongyinggang
     * @date 2020/10/12 14:02
     */
    @Override
    public void defaultMethod() {
        System.out.println("要求必须重写该默认方法");
    }
}
