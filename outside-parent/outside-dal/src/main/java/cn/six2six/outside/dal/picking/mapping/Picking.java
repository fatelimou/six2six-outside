package cn.six2six.outside.dal.picking.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : ZengRong
 * @date : 2020-12-25 16:40
 * @description : 进货用户表
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Picking {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 进货人id
     */
    private String userId;

    /**
     * 进货的卖家userId
     */
    private String parentUserId;

    /**
     * 进货的卖家storeId
     */
    private String parentStoreId;

    /**
     * 备货单ID
     */
    private String pickingListId;

    /**
     *进货成本总金额
     */
    private Double totalCostPrice;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
