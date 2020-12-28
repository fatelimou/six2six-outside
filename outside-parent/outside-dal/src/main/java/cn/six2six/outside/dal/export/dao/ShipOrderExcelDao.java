package cn.six2six.outside.dal.export.dao;

import cn.six2six.outside.dal.export.mapping.ShipOrderToExcel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author : ZengRong
 * @date : 2020-12-27 13:12
 * @description : 结算单excel  信息获取  tb_product,tb_product_detail,tb_shipment_order,tb_shipment_order_ment
 **/
@Mapper
@Component
public interface ShipOrderExcelDao {

    /**
     * 根据出货单号查询 数据库中excel表所需信息
     * @param shipmentOrderId
     * @return
     */
    List<ShipOrderToExcel> getAllExcel(String shipmentOrderId);

}
