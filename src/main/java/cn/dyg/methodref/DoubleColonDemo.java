package cn.dyg.methodref;

import java.util.List;

/**
 * DoubleColonDemo 类是 双冒号应用demo
 * “::” 是域操作符（也可以称作定界符、分隔符）。
 * ::关键字提供了四种语法，可以直接引用已有Java类或对象（实例）的方法或构造器。
 * 与lambda联合使用，::关键字可以使语言更简洁，减少冗余代码。
 * 参考内容：
 * 1.JAVA 8 '::' 关键字，带你深入了解它！
 * https://blog.csdn.net/weixin_42740530/article/details/104655470
 * 2.官方文档
 * https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html
 * 3.Java 8 新特性：Lambda 表达式之方法引用（Lambda 表达式补充版）
 * https://blog.csdn.net/sun_promise/article/details/51190256
 *
 * @author dongyinggang
 * @date 2020-10-09 14:49
 **/
public class DoubleColonDemo {

    public static void main(String[] args) {

        List<Person> roster = Person.createRoster();
        for (Person p : roster) {
            p.printPerson();
        }
        Person[] rosterAsArray = roster.toArray(new Person[roster.size()]);

        //1.引用静态方法
        StaticMethod.staticMethod(rosterAsArray);

        //2.调用特定对象的实例方法
        ParticularObjInstanceMethod.particularObjInstanceMethod(rosterAsArray);

        //3.调用特定类型的任意对象的实例方法
        ParticularTypeInstanceMethod.particularTypeInstanceMethod(rosterAsArray);

        //4.调用构造函数
        Constructor.constructor(roster);
    }

}

