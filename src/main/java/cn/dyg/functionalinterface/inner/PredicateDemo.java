package cn.dyg.functionalinterface.inner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * PredicateDemo 类是 内置函数式接口-Predicate<T>：断言型接口（boolean test（T t））
 *  输入一个参数,输出一个boolean类型的返回值判断是否满足要求
 * @author dongyinggang
 * @date 2020-10-19 20:43
 **/
public class PredicateDemo {

    /**
     * 断言型接口：Predicate<T>
     */
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(102);
        list.add(172);
        list.add(13);
        list.add(82);
        list.add(109);
        //通过重写lambda重写test方法,过滤小于100的数
        List<Integer> newList = filterInt(list, x -> (x > 100));
        for (Integer integer : newList) {
            System.out.println(integer);
        }
    }

    /**
     * 过滤集合
     * @param list 要遍历的list
     * @param pre 待重写test方法的Predicate接口
     * @return 过滤后的集合
     */
    private static List<Integer> filterInt(List<Integer> list, Predicate<Integer> pre){
        List<Integer> newList = new ArrayList<>();
        for (Integer integer : list) {
            //如果满足重写之后的test方法,则将元素add到newList中
            if (pre.test(integer)){
                newList.add(integer);
            }
        }
        return newList;
    }
}
