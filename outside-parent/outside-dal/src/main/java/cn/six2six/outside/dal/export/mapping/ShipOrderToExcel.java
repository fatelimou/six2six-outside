package cn.six2six.outside.dal.export.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : ZengRong
 * @date : 2020-12-27 13:37
 * @description : 数据库中excel表所需信息
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShipOrderToExcel {

    private String itemName; //商品名

    private String unit;//单位

    private String model;  //规格

    private String quantity;//结算数量


    private String price;//单价


    private String totalPrice;//出货总金额
}
