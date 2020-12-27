package cn.six2six.outside.dal.user.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : ZengRong
 * @date : 2020-12-25 16:35
 * @description : 商品表
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 店铺ID
     */
    private String storeId;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 上传商品得用户ID
     */
    private String userId;

    /**
     * 商品全名称
     */
    private String itemName;

    /**
     * 商品简称
     */
    private String itemShortName;

    /**
     * 单位(吨、件、根等)
     */
    private String unit;

    /**
     * 商品状态
     */
    private String status;

    /**
     * 商品是否有效
     */
    private String isValid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
