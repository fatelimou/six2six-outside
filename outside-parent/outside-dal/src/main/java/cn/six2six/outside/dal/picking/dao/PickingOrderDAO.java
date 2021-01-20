package cn.six2six.outside.dal.picking.dao;

import cn.six2six.outside.dal.picking.mapping.Picking;
import cn.six2six.outside.dal.picking.mapping.PickingListDetail;

import java.util.List;

/**
 * @author : ZengRong
 * @date : 2020-12-30 16:57
 * @description : 进货单
 **/
public interface PickingOrderDAO {
    /**
     * 根据id 查询一条进货单信息
     * @param pickingListId 进货单号
     * @return 进货数据
     */
    Picking selectById(String pickingListId);

    /**
     * 查询所有进货单信息
     */
    List<Picking> selectAll();

    /**
     * 插入一条进货单信息.
     *
     * @param pickingList
     */
    int insert(Picking pickingList);

    /**
     * 删除一条进货单信息
     * @param pickingListId 进货单号
     */
    int deleteById(String pickingListId);

    /**
     * 更新进货单信息
     * @param pickingList 进货单实体
     */
    int updateById(Picking pickingList);

    /**
     * 插入一条进货单详情主体.
     * @param pickingListDetail 进货单详情实体
     */
    int insertDetail(PickingListDetail pickingListDetail);

    /**
     * 根据进货单号查询 该单号下所有进货商品
     * @param pickingListId 进货单号Id
     * @return
     */
    List<PickingListDetail> selectAllDetail(String pickingListId);

    /**
     * 根据Id查询一条记录
     * @param pickingListId 进货单详情Id
     */
    PickingListDetail selectOneDetail(String pickingListId);

    /**
     * 删除一条进货单详情记录
     * @param pickingListId 进货单详情ID
     */
    int deleteByIdDetail(String pickingListId);

    /**
     * 更新进货单详细
     * @param pickingListDetail 进货单详细实体
     */
    int updateDetail(PickingListDetail pickingListDetail);
}
