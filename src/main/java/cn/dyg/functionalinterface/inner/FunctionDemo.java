package cn.dyg.functionalinterface.inner;

import java.util.function.Function;

/**
 * FunctionDemo 类是 内置函数式接口-Function<T, R>：函数型接口（R apply（T t））
 * 既有入参，又有出参，传入的入参经过处理后得到出参
 * 入参出参的类型可以有别,也可以一致
 *
 * @author dongyinggang
 * @date 2020-10-19 20:30
 **/
public class FunctionDemo {

    private static final int SIX = 6;

    public static void main(String[] args) {
        FunctionDemo functionDemo = new FunctionDemo();
        //重写apply方法,获取工单后六位的编号
        Integer wrNum = functionDemo.function("FXGD20201019100001",
                (wrCode) -> Integer.valueOf(wrCode.substring(wrCode.length() - SIX))
        );
        System.out.println("当前工单编号" + wrNum);
    }

    /**
     * function 方法是 获取工单号的当前生成编号
     *
     * @param wrCode   工单号 “前缀+日期+6位编号”
     * @param function 待重写apply方法的Function接口
     * @return 6位编号的int值
     * @author dongyinggang
     * @date 2020/10/19 20:35
     */
    private Integer function(String wrCode, Function<String, Integer> function) {
        return function.apply(wrCode);
    }
}
