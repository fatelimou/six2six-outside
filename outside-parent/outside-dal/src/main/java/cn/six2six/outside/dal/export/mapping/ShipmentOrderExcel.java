package cn.six2six.outside.dal.export.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : ZengRong
 * @date : 2020-12-27 13:26
 * @description : 出货单excel表头信息
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShipmentOrderExcel {

   /* @ExcelProperty(value = {"序号"},index = 0)
    @ColumnWidth(10)*/
    private String orderNum;

  /*  @ExcelProperty(value = {"商品名"},index = 1)
    @ColumnWidth(15)*/
    private String itemShortName;

    /*@ExcelProperty(value = {"规格"},index = 2)
    @ColumnWidth(10)*/
    private String model;

   /* @ExcelProperty(value = {"单位（吨）"},index = 3)
    @ColumnWidth(15)*/
    private String unit;

    /*@ExcelProperty(value = {"结算数量"},index = 4)
    @ColumnWidth(15)*/
    private String quantity;

    /*@ExcelProperty(value = {"单价"},index = 5)
    @ColumnWidth(10)*/
    private String price;

    /*@ExcelProperty(value = {"金额"},index = 6)
    @ColumnWidth(30)*/
    private String totalPrice;

   /* @ExcelProperty(value = {"备注"},index = 7)
    @ColumnWidth(10)*/
    private String comment ;

}
