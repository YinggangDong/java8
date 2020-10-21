package cn.dyg.staticmethod.java7.refactor;

/**
 * HasGoodsUuid 接口是 指示类拥有商品uuid。
 *
 * 定义一个HasGoodsUuid接口，指示类拥有商品uuid。
 *
 * @author dongyinggang
 * @date 2020-10-12 11:11
 **/
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
}
