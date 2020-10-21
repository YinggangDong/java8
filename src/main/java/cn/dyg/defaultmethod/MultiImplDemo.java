package cn.dyg.defaultmethod;

/**
 * MultiImplDemo 类是 模拟实现多个接口，多个接口的默认方法重名的情况
 *
 * @author dongyinggang
 * @date 2020-10-12 11:45
 **/
public class MultiImplDemo implements MyInterface,MyInterface2 {

    public static void main(String[] args) {
        MyInterface myInterface = new MultiImplDemo();
        MyInterface2 myInterface2 = new MultiImplDemo();
        MultiImplDemo multiImplDemo = new MultiImplDemo();
        //以下三种调用都是调用了 MultiImplDemo 重写的 defaultMethod 方法
        myInterface.defaultMethod();
        myInterface2.defaultMethod();
        multiImplDemo.defaultMethod();
    }

    /**
     * defaultMethod 方法是 MultiImplDemo 重写的 defaultMethod 方法
     * 由于 MultiImplDemo 实现了两个接口 MyInterface、MyInterface2
     * 两个接口中有相同的默认方法 defaultMethod 此时如果不重写 defaultMethod 编译期会提示
     * "实现类 inherit unrelated defaults for 重名方法 两个接口"的错误
     * 要求实现类必须重写 defaultMethod 方法
     * @author dongyinggang
     * @date 2020/10/12 13:12
     */
    @Override
    public void defaultMethod() {
        System.out.println("MultiImplDemo重写了两个接口的都有的默认方法");
    }

    /**
     * ordinaryMethod 方法是 接口普通方法
     *
     * @author dongyinggang
     * @date 2020/10/12 13:04
     */
    @Override
    public void ordinaryMethod() {
        System.out.println("MultiImplDemo类重写MyInterface的普通方法");
    }
}
