package cn.dyg.lambda.scope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntConsumer;

/**
 * LambdaScopeDemo 类是 lambda表达式作用域测试
 *
 * @author dongyinggang
 * @date 2020-10-13 08:38
 **/
public class LambdaScopeDemo {
    /**
     * 静态变量
     */
    private static int staticNum = 1;
    /**
     * 对象字段
     */
    private int objectNum = 2;

    public static void main(String[] args) {

        LambdaScopeDemo lambdaScopeDemo = new LambdaScopeDemo();

        //1.访问局部变量/对象
        lambdaScopeDemo.localNumTest();
        lambdaScopeDemo.localObjTest();
        lambdaScopeDemo.alreadyDefinedDemo();
//        2.访问对象字段与静态变量
        lambdaScopeDemo.objectAndStaticTest();
        //3.不能访问接口的默认方法
        lambdaScopeDemo.defaultMethodTest();
        //4.Lambda表达式中的this
//        lambdaScopeDemo.thisTest();

        //5.综合理解Lambda表达式的作用域
        lambdaScopeDemo.repeatMessage("Hello world!", 20);
        System.out.println("repeatMessage方法调用已结束");
    }

    /**
     * localNumTest 方法是 1.lambda访问局部变量
     * 对于局部变量，lambda可读，不可写，即可以使用隐性的具有final语义的局部变量
     *
     * @author dongyinggang
     * @date 2020/10/13 19:41
     */
    private void localNumTest() {
        //IntConsumer -以int作为输入，执行某种动作，没有返回值

        System.out.println("1.访问局部变量：");
        //1.1 可以直接在lambda表达式中访问外层的局部变量
        //localNum可以显式的声明为final,也可以不声明,但要求其实际不可变
        int localNum = 1;
        IntConsumer localNumOperation = (a) -> {
            //编译出错
//            localNum++;
            System.out.println("局部变量值为：" + (localNum));
        };
        localNumOperation.accept(0);
        //localNum++;不合法,如果localNum值改变,编译报错,提示lambda表达式中使用的变量应当是final的
//        localNum++;
    }

    /**
     * localObjTest 方法是 访问局部对象
     * 在lambda表达式中访问局部对象时，局部对象不能够被重新赋值，即其指向地址不会修改，
     * 但对象属性，或者类似List中的元素等都是可以被修改的
     * 但是不推荐在lambda表达式中做这种操作，尤其是在
     *
     * @author dongyinggang
     * @date 2020/10/15 16:27
     */
    private void localObjTest() {
        //测试类
        class TempObj {
            private String tempField;

            public void setTempField(String tempField) {
                this.tempField = tempField;
            }

            public String getTempField() {
                return tempField;
            }
        }
        // 测试对象作为局部变量的调用情况
        List<Integer> list = new ArrayList<>();
        list.add(0);
        List<Integer> countList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        TempObj tempObj = new TempObj();
        tempObj.setTempField("原字段");
        for (Integer i : countList) {
            new Thread(() -> {
                // 当不对list赋值，而是增加元素时，不会编译报错,
                // 但在并发情况下，可能出现预期不到的内容,list内部元素位置不可控,虽然编译不报错，但不推荐
                list.add(i);
                //如果改变其指向,则会编译报错
//                list = null;
                //tempObj的 tempField 字段也是个不可预期的值，尽管编译不报错，但不推荐
                tempObj.setTempField("新字段" + i);
//                tempObj=null;
            }).start();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        list.forEach(System.out::println);
        System.out.println(tempObj.getTempField());
    }

    /**
     * alreadyDefinedDemo 方法是 lambda尝试声明一个与局部变量同名的参数或者局部变量
     * 由于lambda表达式不会另外创建一个作用域，和其所在的方法体是同一个作用域
     * 因此不能够声明一个与局部变量同名的参数或者局部变量
     * 如果声明，会报 变量已经在作用域中进行了定义 的异常
     *
     * @author dongyinggang
     * @date 2020/10/15 18:19
     */
    private void alreadyDefinedDemo() {
        //1.2 在 Lambda 表达式当中不允许声明一个与局部变量同名的参数或者局部变量。编译报错
        int localNum = 1;
//        IntConsumer localNumSameNameParam = (localNum)-> System.out.println("参数与局部变量重名");

        IntConsumer localNumSameNameVariable = (a) -> {
//            String localNum = "局部变量与外部局部变量重名";
            System.out.println(localNum);
        };
    }

    /**
     * objectAndStaticTest 方法是 2.访问对象字段与静态变量
     * 和局部变量不同的是，Lambda内部对于实例的字段（即：成员变量）以及静态变量是即可读又可写。
     *
     * @author dongyinggang
     * @date 2020/10/13 19:41
     */
    private void objectAndStaticTest() {
        System.out.println("2.访问对象字段与静态变量：");
        //访问静态变量staticNum和对象字段objectNum
        IntConsumer staticNumOperation =
                (a) -> System.out.println("对象字段值为：" + objectNum + "静态变量值为：" + staticNum);
        staticNumOperation.accept(0);
        //可读可写
        staticNum++;
        objectNum++;
        staticNumOperation.accept(0);
    }

    /**
     * defaultMethodTest 方法是 测试访问接口的默认方法
     * lambda表达式不能访问接口的默认方法
     *
     * @author dongyinggang
     * @date 2020/10/15 18:49
     */
    private void defaultMethodTest() {
        System.out.println("3.测试访问接口的默认方法：");
        /**
         * 通过匿名内部类,可以在重写accept方法时调用IntConsumer的默认方法andThen
         */
        IntConsumer classTest = new IntConsumer() {
            @Override
            public void accept(int value) {
                System.out.println(value + "调用accept方法,对value的第一次消费");
                //调用本接口的默认方法,该方法返回一个IntConsumer对象,
                // 该对象重写了accept方法,先调用当前accept方法,再调用其参数的accept方法
                // 这里只是进行返回未调用其accept方法，没问题，但如果在本方法体里调用andThen返回值的
                // accept方法，就会导致死循环
                andThen((a) -> System.out.println("andThen方法"));
            }
        };

        //不能调用IntConsumer的默认方法andThen
//        IntConsumer test = (tempField)-> andThen(System.out::println);

        // 先调用classTest的accept方法,再调用andThen的参数的accept方法
        classTest.andThen((a) ->
                System.out.println(a + "调用andThen方法参数重写的accept方法,对入参的第二次消费")).accept(0);
    }

    /**
     * thisTest 方法是 4.lambda表达式中的this测试
     * 使用this会引用 该Lambda表达式的所在方法的this（即所在方法的类对象）
     *
     * @author dongyinggang
     * @date 2020/10/14 8:07
     */
    private void thisTest() {
        //调用 当前类的localNumTest方法
        System.out.println("4.测试lambda表达式中的this：");
        IntConsumer thisTest = (a) -> this.thisMethod();
        thisTest.accept(0);
    }

    /**
     * thisMethod 方法是 配合thisTest测试lambda表达式中的this
     *
     * @author dongyinggang
     * @date 2020/10/15 13:21
     */
    private void thisMethod() {
        System.out.println("调用了本类的thisMethod()方法");
    }

    /**
     * repeatMessage 方法是 5.综合理解Lambda表达式的作用域
     * 方法入参text、count在lambda表达式中被使用，从打印结果看，在子线程开始执行前,
     * repeatMessage方法已经调用结束，也就是说参数变量已消失，
     * 要了解lambda表达式依然能够使用这两个参数的原因，就需要更深入的了解lambda表达式
     * 一个lambda表达式包括三部分：
     * 1.一段代码
     * 2.参数
     * 3.自由变量的值，这里的"自由"指的是那些不是lambda表达式参数并且未在lambda表达式中定义的变量,即外部变量
     * <p>
     * 在本方法中，lambda表达式有两个自由变量，text和count，分析代码可知，lambda表达式必须存储
     * 这两个值，才能够完成 repeatMessage 方法调用结束后的循环输出。
     * 可以理解为这两个值被lambda表达式捕获了（eg:捕获可以理解为lambda表达式是包含一个方法的对象，
     * 自由变量被复制到这个对象的实例变量中，且是 final 修饰的最终变量）
     * <p>
     * Lambda表达式的方法体与嵌套代码块有着相同的作用域。因此它也适用同样的命名冲突和屏蔽规则。
     * 在Lambda表达式中不允许声明一个与局部变量同名的参数或者局部变量。
     *
     * @param text  重复输出的text
     * @param count 输出次数
     * @author dongyinggang
     * @date 2020/10/15 13:13
     */
    private void repeatMessage(String text, int count) {
        System.out.println("5.综合理解Lambda表达式的作用域");
        Runnable r = () -> {
            for (int i = 0; i < count; i++) {
                System.out.println(text);
                Thread.yield();
            }
        };

        new Thread(r).start();
    }
}
