/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.common.constant;

import lombok.Getter;

/**
 * ID枚举.
 *
 * @author limozhi on 2020/12/24
 */
@Getter
public enum IDEnum {
    //用户
    USER_ID("U","用户ID"),

    //货物
    PRODUCT_ID("P","货物ID"),
    PRODUCT_ID_DETAIL("PD","货物详情ID"),

    //出货单.
    SHIPMENT_ORDER_ID("S","出货单ID"),
    SHIPMENT_ORDER_DETAIL_ID("SD","出货单详情ID"),

    //备货单
    PICKING_LIST_ID("PL","备货单ID"),
    PICKING_LIST_DETAIL_ID("PLD","备货单详情ID");

    private String prefix;

    private String desc;

    IDEnum(String prefix, String desc) {
        this.prefix = prefix;
        this.desc = desc;
    }
}
