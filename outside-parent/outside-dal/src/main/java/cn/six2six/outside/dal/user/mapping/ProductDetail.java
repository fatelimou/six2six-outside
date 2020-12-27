package cn.six2six.outside.dal.user.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : ZengRong
 * @date : 2020-12-25 17:33
 * @description : 商品详情表
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDetail {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品详情ID
     */
    private String productDetailId;

    /**
     * 单价
     */
    private Double unitPrice;

    /**
     * 价格类型(0实价1面议)
     */
    private String priceType;

    /**
     * 型号
     */
    private String model;

    /**
     *库存
     */
    private String inventory;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
