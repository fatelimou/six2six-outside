package cn.six2six.outside.dal.user.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : ZengRong
 * @date : 2020-12-25 17:41
 * @description : 出货表
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShipmentOrder {

    /**
     *主键ID
     */
    private Long id;

    /**
     * 出货人id
     */
    private String userId;

    /**
     * 店铺ID
     */
    private String storeId;

    /**
     * 出货单ID
     */
    private String shipmentOrderId;

    /**
     * 出货单状态
     */
    private String orderStatus;

    /**
     * total_price
     */
    private Double totalPrice;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
