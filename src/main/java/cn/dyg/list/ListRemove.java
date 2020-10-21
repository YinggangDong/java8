package cn.dyg.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * ListRemove 类是 list的移除操作,结合lambda表达式
 *
 * @author dongyinggang
 * @date 2020-09-09 10:50
 **/
public class ListRemove {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }

        //1. list.foreach() 中 调用ArrayList的Remove方法
        List<String> list1 = new ArrayList<>(list.size());
        //深拷贝
        list1.addAll(Arrays.asList(new String[list.size()]));
        Collections.copy(list1,list);
        listForeach(list1);

        //2. 增强for循环 中 调用ArrayList的Remove方法
        List<String> list2 = new ArrayList<>(list.size());
        list2.addAll(Arrays.asList(new String[list.size()]));
        Collections.copy(list2,list);
        enhanceForLoop(list2);

        //3.迭代器遍历 中 调用Iterator的remove方法
        List<String> list3 = new ArrayList<>(list.size());
        list3.addAll(Arrays.asList(new String[list.size()]));
        Collections.copy(list3,list);
        iteratorRemove(list3);

        //4.Itr的forEachRemaining方法调用ArrayList的Remove方法
        List<String> list4 = new ArrayList<>(list.size());
        list4.addAll(Arrays.asList(new String[list.size()]));
        Collections.copy(list4,list);
        forEachRemainingRemove(list4);

    }

    /**
     * listForeach 方法是 1. list.foreach() 中 调用ArrayList的Remove方法
     *
     * @param list 待移除元素的list
     * @author dongyinggang
     * @date 2020/9/8 11:28
     */
    private static void listForeach(List<String> list) {
        /*
         * 以下代码会抛出ConcurrentModificationException
         * 原因是：在遍历过程中删除元素导致 modCount 和 expectedModCount 不一致报错
         * list.forEach方法是重写了Iterable的foreach方法
         *
         * 这里的异常是在remove掉元素之后,阻断循环,判断modCount 和 expectedModCount是否相等，
         * 由于进行了remove，所以不相等，然后抛出了并发修改异常
         */
        try {
            System.out.println("forEach中执行remove操作:");
            list.forEach(s -> {
                if ("8".equals(s)) {
                    list.remove(s);
                }
            });
        } catch (ConcurrentModificationException concurrentModificationException) {
            log.println("在遍历过程中删除元素导致 modCount 和 expectedModCount 不一致报错-并发修改异常");
            concurrentModificationException.printStackTrace();
        }

        System.out.println(list);
    }


    /**
     * enhanceForLoop 方法是 2. 增强for循环 中 调用ArrayList的Remove方法
     *
     * @param list 待处理的list
     * @author dongyinggang
     * @date 2020/9/9 11:24
     */
    private static void enhanceForLoop(List<String> list) {
        /*
         * 增强for循环实际实现是通过构建Iterator（迭代器）来进行循环
         * 会先调用hasNext方法判断是否有下一个元素，然后再调用next方法获取到下一个元素
         * 在next方法中会判断期望修改数和修改数是否一致，因为next方法中没有修改 expectedModCount 值，
         * 调用 fastRemove 时会改变 modCount 值，所以抛出异常。
         *
         * 那么增强for循环中的remove是不是一定会报错呢？
         * 并不是，如果我们删除掉倒数第二个元素，在进行hasNext方法时，就会判断 下一元素游标和size的关系
         * 也就是最后一个元素的游标和 原size-1进行比较，就出现了两者相等的情况，
         * 直接跳出循环，不再调用next方法，此时就不会有 并发修改的异常抛出了
         * foreach遍历list删除元素：https://blog.csdn.net/bimuyulaila/article/details/52088124
         */
        System.out.println("增强for循环中中执行ArrayList的remove(Object o)操作:");
        for (String s : list) {
            if ("8".equals(s)) {
                list.remove(s);
            }
        }
        System.out.println(list);
    }

    /**
     * iteratorRemove 方法是 3.迭代器遍历 中 调用Iterator的remove方法
     *
     * @param list 待移除元素的list
     * @author dongyinggang
     * @date 2020/9/9 11:27
     */
    private static void iteratorRemove(List<String> list) {
        /*
         * 3.迭代器遍历 中 调用Iterator的remove方法
         * 通过迭代器做遍历，然后进行元素的删除
         * 迭代器的remove方法在删除元素的同时修改了 expectedModCount 期望长度
         */
        Iterator<String> iter = list.iterator();
        System.out.println("迭代器遍历 中 调用Iterator的remove()方法:");
        while (iter.hasNext()) {
            String s = iter.next();
            if ("3".equals(s)) {
                /*
                 * list的remove方法还是会导致 并发修改异常
                 * 如果这里使用list.listForeach(s)就和2的情形是一样的
                 *
                 * 迭代器Iterator的remove()方法被ArrayList的私有类Itr重写了,
                 * 做了 expectedModCount 的处理,在调用完ArrayList的remove(int index)方法后，
                 * 将modCount的值赋给了expectedModCount
                 */
                iter.remove();
            }
        }
        System.out.println(list);
    }

    /**
     * forEachRemainingRemove 方法是 4.Itr的forEachRemaining方法调用ArrayList的Remove方法
     *
     * @param list 待处理的list
     * @author dongyinggang
     * @date 2020/9/9 11:28
     */
    private static void forEachRemainingRemove(List<String> list) {
        /*
         * 重新赋值迭代器对象测试forEachRemaining方法,这里调用list的remove(Object o)方法
         * 还是会因为并发修改导致问题
         * 和1中的情形比较类似，都是判断modCount == expectedModCount——但实际代码逻辑有区别
         *
         * 在ArrayList中的Itr类的forEachRemaining方法循环时,
         * 由于remove之后 modCount == expectedModCount不满足，阻断while
         * 此时调用checkForComodification检测modCount == expectedModCount，抛出异常
         */
        System.out.println("迭代器的forEachRemaining方法遍历 中 调用list的remove(Object o)方法:");
        Iterator<String> iter = list.iterator();
        iter.forEachRemaining((a) -> {
            if ("8".equals(a)) {
                list.remove("8");
            }
        });
        System.out.println(list);
    }

}
