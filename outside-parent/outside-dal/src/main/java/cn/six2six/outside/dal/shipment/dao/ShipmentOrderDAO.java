package cn.six2six.outside.dal.shipment.dao;

import cn.six2six.outside.dal.shipment.mapping.ShipmentOrder;
import cn.six2six.outside.dal.shipment.mapping.ShipmentOrderDetail;

import java.util.List;

/**
 * @author : ZengRong
 * @date : 2020-12-27 13:19
 * @description : 出货表信息 tb_shipment_order
 **/
public interface ShipmentOrderDAO {
    /**
     * 根据id 查询一条出货单信息
     * @param shipmentOrderId 出货单号
     * @return 出货数据
     */
    ShipmentOrder selectById(String shipmentOrderId);

    /**
     * 查询所有出货单信息
     */
    List<ShipmentOrder> selectAll();

    /**
     * 插入一条出货单信息.
     *
     * @param shipmentOrder
     */
    int insert(ShipmentOrder shipmentOrder);

    /**
     * 删除一条出货单信息
     * @param shipmentOrderId 出货单号
     */
    int deleteById(String shipmentOrderId);

    /**
     * 更新出货单信息
     * @param shipmentOrder 出货单实体
     */
    int updateById(ShipmentOrder shipmentOrder);

    /**
     * 插入一条出货单详情主体.
     *
     * @param shipmentOrderDetail
     */
    int insertDetail(ShipmentOrderDetail shipmentOrderDetail);

    /**
     * 根据出货单号查询 该单号下所有出货商品
     * @param shipmentOrderId 出货单号Id
     * @return
     */
    List<ShipmentOrderDetail> selectAllDetail(String shipmentOrderId);

    /**
     * 根据Id查询一条记录
     * @param shipmentOrderDetailId 出货单详情Id
     */
    ShipmentOrderDetail selectOneDetail(String shipmentOrderDetailId);

    /**
     * 删除一条出货单详情记录
     * @param shipmentOrderDetailId 出货单详情ID
     */
    int deleteByIdDetail(String shipmentOrderDetailId);

    /**
     * 更新出货单详细
     * @param shipmentOrderDetail 出货单详细实体
     */
    int updateDetail(ShipmentOrderDetail shipmentOrderDetail);
}
