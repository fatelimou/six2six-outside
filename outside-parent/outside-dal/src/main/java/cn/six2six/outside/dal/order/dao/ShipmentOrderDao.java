package cn.six2six.outside.dal.order.dao;

import cn.six2six.outside.dal.user.mapping.ShipmentOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author : ZengRong
 * @date : 2020-12-27 13:19
 * @description : 出货表信息 tb_shipment_order
 **/
@Mapper
@Component
public interface ShipmentOrderDao {
    /**
     * 根据id 查询出货单信息
     * @param shipmentOrderId 出货单号
     * @return 出货数据
     */
    ShipmentOrder getById(String shipmentOrderId);
}
