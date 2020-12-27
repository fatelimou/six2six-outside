package cn.six2six.outside.dal.user.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : ZengRong
 * @date : 2020-12-25 17:48
 * @description : 出货详情表
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShipmentOrderDetail {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 出货商品id
     */
    private String productId;

    /**
     * 出货商品详情id
     */
    private String productDetailId;

    /**
     * 出货单id
     */
    private String shipmentOrderId;

    /**
     * 出货单详情id
     */
    private String shipmentOrderDetailId;

    /**
     * 出货商品型号
     */
    private String model;

    /**
     *出货数量
     */
    private String quantity;

    /**
     *出货价格
     */
    private Double price;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
