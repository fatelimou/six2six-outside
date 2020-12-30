package cn.six2six.outside.dal.picking.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : ZengRong
 * @date : 2020-12-25 16:51
 * @description : 出货单详情表
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PickingListDetail {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 进货商品id
     */
    private String productId;

    /**
     * 进货商品详情ID
     */
    private String productDetailId;

    /**
     * 进货单id
     */
    private String pickingListId;

    /**
     * 进货商品型号
     */
    private String model;

    /**
     * 进货价格
     */
    private Double price;

    /**
     * 进货数量
     */
    private String amount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
