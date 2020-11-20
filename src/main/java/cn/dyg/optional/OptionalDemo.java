package cn.dyg.optional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * OptionalDemo 类是 Optional的样例类
 * 测试stash冲突
 * 测试 git stash
 *
 * @author dongyinggang
 * @date 2020-10-21 15:58
 **/
public class OptionalDemo {
    public static void main(String[] args) {

//        ofTest();

//        ofNullableTest();

//        filterTest();
//        mapTest();
//        mapTest2();
//        flatMapTest();
//        orElseTest();
//        orElseGetTest();
//        orElseThrowTest();
        isPresentTest2();

    }

    /**
     * empty()方法测试
     * 返回一个value为null的Optional对象
     * 由于value为null,本方法输出null
     */
    private static void emptyTest() {
        Optional empty = Optional.empty();
        System.out.println(empty);
    }

    /**
     * of()方法测试
     * 输出optional对象的value值-name
     */
    private static void ofTest() {
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
    private static void ofNullableTest() {
        System.out.println("ofNullable()方法测试:");
        Optional<String> empty = Optional.ofNullable(null);
        try {
            //抛出异常,NoSuchElementException
            System.out.println(empty.get());
        } catch (NoSuchElementException e) {
            System.out.println("value为空,没有输出");
        }
        Optional<String> value = Optional.ofNullable("value");
        System.out.println(value.get());

    }

    /**
     * isPresent方法测试
     * 同时进行ifPresent方法的测试
     */
    private static void isPresentTest() {
        //3.通过Optional的方法进行strings的非null值输出
        String[] strings = new String[]{"1", null, "3", "4", null};
        for (String string : strings) {
            Optional<String> strOptional = Optional.ofNullable(string);
            //isPresent,若value值存在,返回true,否则返回false
            if (strOptional.isPresent()) {
                System.out.println(strOptional.get());
            }
            //与上方等价,若value值存在,执行lambda表达式重写的Consumer的accept方法
            strOptional.ifPresent(System.out::println);
        }
    }

    private static void isPresentTest2() {
        List<String> totalList = Stream.of("1", "2", "3").collect(Collectors.toList());
        List<String> failList = Stream.of("1", "2", "3").collect(Collectors.toList());
//        List<String> failList = new ArrayList<>();
        List<String> successList = totalList.stream().filter(item -> !failList.contains(item)).collect(Collectors.toList());
        Optional.ofNullable(successList).ifPresent(item -> successList.forEach(System.out::println));
        //当successList为空时,会被filter过滤,不会再执行后面的输出
        //ifPresent完成了判null操作,filter完成了判空操作
        Optional.ofNullable(successList).filter(list -> successList.size() > 0)
                .ifPresent((item) -> System.out.println("不为空"));
        totalList.removeAll(failList);
        Optional.ofNullable(totalList).ifPresent(item -> totalList.forEach(System.out::println));
    }

    /**
     * filter方法测试
     * 将String数组中长度大于1的元素取出来放到新的List中去
     */
    private static void filterTest() {
        System.out.println("filter方法测试：");
        String[] strings = new String[]{"1", null, "31", "41", null};
        List<String> newString = new ArrayList<>();
        for (String string : strings) {
            //将strings中长度大于1的字符串筛选出来
            Optional<String> strOptional =
                    Optional.ofNullable(string).filter((s) -> s.length() > 1);
            strOptional.ifPresent(newString::add);
        }
        System.out.println(newString.toString());
    }

    /**
     * map方法测试
     * 将员工list中的名称写成一个新的list
     */
    private static void mapTest() {
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
        //List通过stream进行
        List<String> nameStreamList =
                employeeList.stream().map(Employee::getName).collect(Collectors.toList());
        System.out.println(nameStreamList.toString());
    }

    private static void mapTest2() {
        Employee employee = new Employee("张三");
        Employee employee1 = new Employee("李四");
        Employee employee2 = new Employee(null);
        Map<String, Employee> map = new HashMap<>();
        map.put("1", employee);
        map.put("2", employee1);
        map.put("3", employee2);
        String name = Optional.ofNullable(map.get("4")).map(Employee::getName).orElse("123");
        System.out.println(name);
    }

    /**
     * flatMap方法测试
     * 调用changeName方法修改employee的name并输出
     * 和map的主要区别是flatMap要求返回值必须为Optional对象,而map可以为任意对象
     */
    private static void flatMapTest() {
        System.out.println("flatMap测试");
        Employee employee = new Employee("王五");
        Optional<Employee> optional = Optional.of(employee);
        optional.flatMap((value) -> Optional.ofNullable(employee.changeName(value)))
                .ifPresent(System.out::println);

    }

    /**
     * orElse方法测试
     * 当optional的value为null时，返回orElse的入参值
     * 否则返回value值
     */
    private static void orElseTest() {
        System.out.println("orElseTest测试");
        Optional<String> optional = Optional.ofNullable(null);
        String defaultValue = optional.orElse("default");
        System.out.println(defaultValue);

        Optional<String> optional1 = Optional.ofNullable("原值");
        String defaultValue1 = optional1.orElse("default");
        System.out.println(defaultValue1);
    }

    /**
     * orElseGet方法测试
     * 和orElse的区别是orElse指定了对象，orElseGet的参数是指定了一个 Supplier 接口的实现类
     * 当optional的value为null时，返回orElseGet的参数的返回值
     * 否则返回value值
     */
    private static void orElseGetTest() {
        System.out.println("orElseGet测试");
        Optional<String> optional = Optional.ofNullable(null);
        String defaultValue = optional.orElseGet(() -> "默认值");
        System.out.println(defaultValue);

        Optional<String> optional1 = Optional.ofNullable("原值");
        String defaultValue1 = optional1.orElseGet(() -> "默认值");
        System.out.println(defaultValue1);
    }

    /**
     * orElseThrow方法测试
     * 和orElse的区别是orElse指定了对象，orElseThrow的参数是指定了一个 Supplier 接口的实现类
     * 并且该实现类的返回值被限定为 Throwable 的子类
     * 当optional的value为null时，抛出orElseThrow的参数的get方法返回的异常
     * 否则返回value值
     */
    private static void orElseThrowTest() {
        System.out.println("orElseThrow测试");

        Optional<String> optional1 = Optional.ofNullable("原值");
        String defaultValue1 = optional1.orElseThrow(NoSuchElementException::new);
        System.out.println(defaultValue1);

        Optional<String> optional = Optional.ofNullable(null);
        String defaultValue = optional.orElseThrow(NoSuchElementException::new);
        System.out.println(defaultValue);

    }


    /*
     Optional 的生产及应用如下
     */
    /**
     *
     * getRuleMap 方法是 查询维修中心时效规则Map
     *
     * @return 时效规则Map
     * @author dongyinggang
     * @date 2020/11/4 9:13
     */
//    private Map<String, String> getRuleMap() {
//        try {
//            //调用BBPF的字典项,获取维修中心时效规则
//            log.info("调用字典接口查询维修中心时效规则--开始--->");
//            long start = System.currentTimeMillis();
//            CallResponse<List<DictValue>> callResponse =
//                    dictValueService.getDictValueListByDictTypeCode(McTimeRuleConstant.MC_TIME_RULE);
//            log.info("调用字典接口查询维修中心时效规则--结束--->,耗时{}ms,结果{}", System.currentTimeMillis() - start, callResponse);
//            //判断返回值是否为空,然后比较返回值code是否为"00",BBPF返回的正确返回值为"00"
//            List<DictValue> ruleList = Optional.ofNullable(callResponse)
//                    .filter((s) -> "00".equals(s.getCode()))
//                    .map(CallResponse::getResult).orElseGet(ArrayList::new);
//            //ruleList转为Map
//            return ruleList.stream().collect(Collectors.toMap(DictValue::getValueName, DictValue::getValueCode, (key1, key2) -> key2));
//        } catch (Exception e) {
//            throw new BusinessRuntimeException(McErrorCodeEnum.DICT_EXCEPTION.getCode(), McErrorCodeEnum.DICT_EXCEPTION.getMessage());
//        }
//    }

}
