package cn.dyg.staticmethod.java8;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 指示类拥有商品uuid。
 *
 * 只是将HasGoodsUuidUtil中的方法移至HasGoodsUuid接口中。
 *
 * 这个细小的变化，提高了代码的内聚性。
 * 对于新手开发来说，可以轻易的找到到工具方法。对长期维护的项目来说，可读性、可维护性至关重要。
 *
 */
public interface HasGoodsUuid {

    /**
     * @return 商品uuid。
     */
    String getGoodsUuid();

    /**
     * @param goodsUuid
     *          商品uuid。
     */
    void setGoodsUuid(String goodsUuid);

    /**
     * 取得全部商品uuid。
     *
     * @param col
     *          数据对象集合。传入null等价于传入空集合。忽略所有null元素。
     * @return 返回包含商品uuid的集合。正常情况下不会返回null。
     */
    static <T extends HasGoodsUuid> Set<String> toGoodsUuids(Collection<T> col) {
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
