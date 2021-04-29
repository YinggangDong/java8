package cn.dyg.functionalinterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * ComputeIfAbsentTest 类是 computeIfAbsent方法测试
 *
 * @author dongyinggang
 * @date 2021-04-29 09:25
 **/
public class ComputeIfAbsentTest {

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("key2","value1");
        //返回的value值一定是 Value 类的子类,要不然 put 不进去
        map.computeIfAbsent("key1",key ->new ArrayList<>());
        map.computeIfAbsent("key1", new Function<String, Object>() {
            /**
             * Applies this function to the given argument.
             *
             * @param s the function argument
             * @return the function result
             */
            @Override
            public Object apply(String s) {
                return new ArrayList<>();
            }
        });
    }
}
