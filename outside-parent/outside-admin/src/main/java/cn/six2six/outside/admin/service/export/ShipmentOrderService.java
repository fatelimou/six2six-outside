package cn.six2six.outside.admin.service.export;

import cn.six2six.outside.common.result.ResultBean;
import cn.six2six.outside.dal.user.mapping.ShipmentOrder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : ZengRong
 * @date : 2020-12-27 13:49
 * @description : 出货单 业务逻辑层
 **/
public interface ShipmentOrderService {

    /**
     * 根据出货单号查询 数据库中excel表所需信息
     * @param shipmentOrderId
     * @return
     */
    ResultBean getExcel(HttpServletResponse response, String shipmentOrderId) throws IOException;

    /**
     * 根据订单号 查询出货单信息
     * @param shipmentOrderId 出货单号
     * @return 出货数据
     */
    ShipmentOrder getById(String shipmentOrderId);
}
