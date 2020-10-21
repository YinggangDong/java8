package cn.dyg.methodref;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Constructor 类是 构造函数的方法引用形式
 *
 * @author dongyinggang
 * @date 2020-10-20 18:49
 **/
public class Constructor {

    private String name;

    /**
     * 无参构造
     */
    public Constructor(){
    }

    /**
     * 有参构造
     * @param name name值
     */
    public Constructor(String name){
        this.name = name;
    }

    /**
     * constructor 方法是 调用构造函数
     *
     * @author dongyinggang
     * @date 2020/10/10 15:14
     */
    static void constructor(List<Person> roster){
        System.out.println("4.调用构造函数：");
        //1.通过匿名内部类实现get()方法,调用构造函数
        Set<Person> rosterSet = transferElements(roster, new Supplier<Set>(){
            @Override
            public Set get() {
                return new HashSet<>();
            }
        });
        //2.通过lambda表达式实现get()方法,调用构造函数
        Set<Person> rosterSetLambda = transferElements(roster,()->new HashSet<>());
        //3.简化lambda表达式实现get()方法,调用构造函数
        Set<Person> rosterSimple = transferElements(roster,HashSet::new);
        System.out.println("赋值完成：");
        for (Person person : rosterSimple) {
            person.printPerson();
        }
    }

    /**
     * transferElements 方法是 将一个collection的元素放到新的collection中去
     *
     * @param sourceCollection 源集合
     * @param collectionFactory 集合工厂,重写get()方法的Supplier接口实现类
     * @return DEST的集合对象
     * @author dongyinggang
     * @date 2020/10/20 18:52
     */
    private static <T, SOURCE extends Collection<T>, DEST extends Collection<T>>
    DEST transferElements(SOURCE sourceCollection,Supplier<DEST> collectionFactory) {
        //通过get方法创建一个新的集合对象
        DEST result = collectionFactory.get();
        //将源集合中的元素add进新的集合对象中
        result.addAll(sourceCollection);
        //将新的集合对象放回
        return result;
    }

    public static void main(String[] args) {
        //通过方法调用重写create()方法
        ConstructorFactory factory = Constructor::new;
        Constructor constructor = factory.create();
        System.out.println(constructor.name);

        ConstructorFactory1 factory1 = Constructor::new;
        Constructor constructor1 = factory1.create("有参");
        System.out.println(constructor1.name);
    }
}

/**
 * ConstructorFactory 是Constructor的构造器
 */
interface ConstructorFactory{
    /**
     * create 方法是 待重写的返回Constructor实例的create()方法
     *
     * @return Constructor实例
     * @author dongyinggang
     * @date 2020/10/21 13:27
     */
    Constructor create();
}

/**
 * ConstructorFactory1 是Constructor的构造器
 */
interface ConstructorFactory1{
    /**
     * create 方法是 待重写的返回Constructor实例的create()方法
     *
     * @param name name属性
     * @return Constructor实例
     * @author dongyinggang
     * @date 2020/10/21 13:27
     */
    Constructor create(String name);
}
