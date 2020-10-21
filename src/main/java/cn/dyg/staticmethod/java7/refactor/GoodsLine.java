package cn.dyg.staticmethod.java7.refactor;

import lombok.Data;

/**
 * GoodsLine 类是 商品行对象
 *
 * 商品行实现HasGoodsUuid接口。
 *
 * @author dongyinggang
 * @date 2020-10-12 11:12
 **/
@Data
public class GoodsLine implements HasGoodsUuid {
    private String goodsUuid;
    private String goodsCode;

}
