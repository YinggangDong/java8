package cn.dyg.defaultmethod;

/**
 * MyInterface2 接口是 接口2
 *
 * @author dongyinggang
 * @date 2020-10-12 11:44
 **/
public interface MyInterface2 {

    /**
     * defaultMethod 方法是 和MyInterface默认方法重名的默认接口
     *
     * @author dongyinggang
     * @date 2020/10/12 11:44
     */
    default void defaultMethod(){
        System.out.println("这是接口MyInterface2的默认方法");
    }
}
