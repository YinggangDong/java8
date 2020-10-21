package cn.dyg.staticmethod.java7.old;

import cn.dyg.staticmethod.java7.refactor.GoodsLine;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 在进销存系统中，绝大部分单据都会有商品行，并且在业务处理过程中从商品行集合中
 * 取出商品uuid的集合是一个很常见的操作。
 * 那么本文我将以“从GoodsLine中取goodsUuid集合”为例讲述代码的演化，并最终应用Java8接口静态方法。
 *
 * GoodsLineOld 类是 从需求出发很容易写出如下代码：
 *
 *
 * 商品行GoodsLine中定义了静态方法toGoodsUuids(Collection<GoodsLine> lines)
 * 方法用于从GoodsLine中取出goodsUuid集合。
 *
 * 但是，每新增一种单据类型的商品行，类似方法又复制一遍，导致代码中很多重复代码。
 *
 * @author dongyinggang
 * @date 2020-10-12 11:14
 **/
@Data
public class GoodsLineOld {
    private String goodsUuid;
    private String goodsCode;

    /**
     * 取得全部商品uuid。
     *
     * @param lines
     *          数据对象集合。传入null等价于传入空集合。忽略所有null元素。
     * @return 返回包含商品uuid的集合。正常情况下不会返回null。
     */
    public static Set<String> toGoodsUuids(Collection<GoodsLine> lines) {
        Set<String> goodsUuids = new HashSet<>();
        for (GoodsLine line : lines) {
            goodsUuids.add(line.getGoodsUuid());
        }
        return goodsUuids;
    }
}
