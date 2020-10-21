package cn.dyg.staticmethod.java7.refactor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 定义HasGoodsUuidUtil工具类提供静态的toGoodsUuids方法。
 *
 * 提供{@link HasGoodsUuid}相关的工具类。
 *
 * 所有实现HasGoodsUuid接口的商品行都使用HasGoodsUuidUtil工具类，
 * 无需在各个商品行类中重复相似方法，有效的减少了代码的重复量。
 *
 */
public class HasGoodsUuidUtil {
    /**
     * 取得全部商品uuid。
     *
     * @param col
     *          数据对象集合。传入null等价于传入空集合。忽略所有null元素。
     * @return 返回包含商品uuid的集合。正常情况下不会返回null。
     */
    public static <T extends HasGoodsUuid> Set<String> toGoodsUuids(Collection<T> col) {
        Set<String> uuids = new HashSet<>();
        if (col == null || col.isEmpty()) {
            return uuids;
        }
        for (T item : col) {
            if (item != null) {
                uuids.add(item.getGoodsUuid());
            }
        }
        return uuids;
    }
}
