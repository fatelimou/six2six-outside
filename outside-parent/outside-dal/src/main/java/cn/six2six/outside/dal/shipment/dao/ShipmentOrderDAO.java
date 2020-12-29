package cn.six2six.outside.dal.shipment.dao;

import cn.six2six.outside.dal.shipment.mapping.ShipmentOrder;

/**
 * @author : ZengRong
 * @date : 2020-12-27 13:19
 * @description : 出货表信息 tb_shipment_order
 **/
public interface ShipmentOrderDAO {
    /**
     * 根据id 查询出货单信息
     * @param shipmentOrderId 出货单号
     * @return 出货数据
     */
    ShipmentOrder getById(String shipmentOrderId);
}
