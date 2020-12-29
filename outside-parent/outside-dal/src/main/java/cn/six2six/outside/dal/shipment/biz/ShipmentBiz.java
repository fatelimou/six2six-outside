package cn.six2six.outside.dal.shipment.biz;

import cn.six2six.outside.dal.shipment.dao.ShipmentOrderDAO;
import cn.six2six.outside.dal.shipment.mapping.ShipmentOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author : ZengRong
 * @date : 2020-12-29 17:41
 * @description :
 **/
@Service
public class ShipmentBiz {

    @Resource
    private ShipmentOrderDAO shipmentOrderDAO;

    public ShipmentOrder queryById(String shipmentOrderId){
        return  shipmentOrderDAO.queryById(shipmentOrderId);
    }
}
