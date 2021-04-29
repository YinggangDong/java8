package cn.dyg.functionalinterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * ComputeIfAbsentTest 类是 computeIfAbsent方法测试
 * 参考内容：https://www.jianshu.com/p/8ed5d0f2bff2
 *
 * @author dongyinggang
 * @date 2021-04-29 09:25
 **/
public class ComputeIfAbsentTest {

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("key2","value1");
        //出参被范式 (? extend V) 限定为是 map 的 value 类型 Object 的子类,要不然 put 不进去
        //入参被范式 (? super K) 限定为是 map 的 key 类型 String 的父类
        //但实际入参 key 始终是 key 类型 String 的变量
        //尽管限定支持 String 的父类,但实际还是直接被规定为了 String
        map.computeIfAbsent("key1",key ->new ArrayList<>());
        //将入参设为 Object也可以,但前面的 key 已经被限定为 String 了
        //实际执行  mappingFunction.apply(key) 时，key 就是 String
        map.computeIfAbsent("key1", new Function<Object, Object>() {
            /**
             * Applies this function to the given argument.
             *
             * @param s the function argument
             * @return the function result
             */
            @Override
            public Object apply(Object s) {
                return new ArrayList<>();
            }
        });
    }
}
