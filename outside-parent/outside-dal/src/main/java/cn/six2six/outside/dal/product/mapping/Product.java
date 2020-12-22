/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.dal.product.mapping;

import lombok.Data;

import java.util.Date;

/**
 * 商品主体实体.
 *
 * @author limozhi on 2020/12/16
 */
@Data
public class Product {

    /**
     * 主键.
     */
    private Long id;

    /**
     * 店铺ID.
     */
    private String storeId;

    /**
     * 用户ID.
     */
    private String userId;

    /**
     * 商品名称.
     */
    private String itemName;

    /**
     * 商品简称.
     */
    private String itemShortName;

    /**
     * 商品状态(e.g 上架、下架、补货)
     */
    private Integer status;

    /**
     * 是否有效
     */
    private Boolean valid;

    /**
     * 创建时间.
     */
    private Date createTime;

    /**
     * 更新时间.
     */
    private Date updateTime;
}
