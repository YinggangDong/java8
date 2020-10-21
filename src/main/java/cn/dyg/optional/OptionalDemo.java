package cn.dyg.optional;

import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * OptionalDemo 类是 Optional的样例类
 *
 * @author dongyinggang
 * @date 2020-10-21 15:58
 **/
public class OptionalDemo {
    public static void main(String[] args) {

//        ofTest();

//        ofNullableTest();

//        filterTest();
        mapTest();

    }

    /**
     * empty()方法测试
     * 返回一个value为null的Optional对象
     * 由于value为null,本方法输出null
     */
    private static void emptyTest(){
        Optional empty = Optional.empty();
        System.out.println(empty);
    }

    /**
     * of()方法测试
     * 输出optional对象的value值-name
     */
    private static void ofTest(){
        System.out.println("of()方法测试");
        //1.of方法为非null的值创建一个Optional。
        Optional optional = Optional.of("name");
        System.out.println(optional.get());

        Optional<List> optionalList = Optional.of(new ArrayList<>());
        System.out.println(optionalList.get());
    }

    /**
     * ofNullable()方法测试
     * ofNullable方法为指定的值创建一个Optional，如果指定的值为null，则返回一个空的Optional。
     * 返回的Optional对象调用orElse方法,
     * 若Optional的value为空则将其赋值为orElse方法的参数,通常用来给null赋默认值
     */
    private static void ofNullableTest(){
        System.out.println("ofNullable()方法测试:");
        Optional<String> empty = Optional.ofNullable(null);
        try {
            //抛出异常,NoSuchElementException
            System.out.println(empty.get());
        }catch (NoSuchElementException e){
            System.out.println("value为空,没有输出");
        }
        Optional<String> value = Optional.ofNullable("value");
        System.out.println(value.get());

    }

    /**
     * isPresent方法测试
     * 同时进行ifPresent方法的测试
     */
    private static void isPresentTest(){
        //3.通过Optional的方法进行strings的非null值输出
        String[] strings = new String[]{"1",null,"3","4",null};
        for (String string : strings) {
            Optional<String> strOptional = Optional.ofNullable(string);
            //isPresent,若value值存在,返回true,否则返回false
            if(strOptional.isPresent()){
                System.out.println(strOptional.get());
            }
            //与上方等价,若value值存在,执行lambda表达式重写的Consumer的accept方法
            strOptional.ifPresent(System.out::println);
        }
    }

    /**
     * filter方法测试
     * 将String数组中长度大于1的元素取出来放到新的List中去
     */
    private static void filterTest(){
        System.out.println("filter方法测试：");
        String[] strings = new String[]{"1",null,"31","41",null};
        List<String> newString = new ArrayList<>();
        for (String string : strings) {
            //将strings中长度大于1的字符串筛选出来
            Optional<String> strOptional =
                    Optional.ofNullable(string).filter((s)->s.length()>1);
            strOptional.ifPresent(newString::add);
        }
        System.out.println(newString.toString());
    }

    /**
     * map方法测试
     * 将员工list中的名称写成一个新的list
     */
    private static void mapTest(){
        System.out.println("map方法测试");
        Employee employee = new Employee("张三");
        Employee employee1 = new Employee("李四");
        Employee employee2 = new Employee(null);
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        employeeList.add(employee1);
        employeeList.add(employee2);
        List<String> nameList = new ArrayList<>();

        for (Employee item : employeeList) {
            //这里的getName是实例方法,入参是item,是调用对象,因此是引用特定类型的实例方法
            Optional<String> optional = Optional.ofNullable(item).map(Employee::getName);
            optional.ifPresent(nameList::add);
        }
        System.out.println(nameList.toString());

        List<String> nameStreamList =
                employeeList.stream().map(Employee::getName).collect(Collectors.toList());
        System.out.println(nameStreamList.toString());
    }

    /**
     * flatMap方法测试
     */
    private static void flatMapTest() {
        Optional optional = Optional.of("name");
        optional.flatMap(Optional::of).ifPresent(System.out::println);

    }

}
