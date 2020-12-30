/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.dal.product.dao;

import cn.six2six.outside.dal.product.mapping.Product;
import cn.six2six.outside.dal.product.mapping.ProductDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品主体数据访问接口.
 *
 * @author limozhi on 2020/12/16
 */
public interface ProductDAO {

    /**
     * 插入一条商品主体.
     *
     * @param product {@link Product}.
     */
    int insert(Product product);

    /**
     * 删除一条商品主题
     * @param productId 商品ID
     */
    int deleteById(String productId);

    /**
     * 更新商品
     * @param product 商品实体
     */
    int updateById(Product product);

    /**
     * 查询所有商品
     */
    List<Product> selectAll();

    /**
     * 根据Id查询一条记录
     * @param productId 商品ID
     */
    Product selectOne(String productId);

    /**
     * 插入一条商品详情主体.
     *
     * @param productDetail
     */
    int insertDetail(ProductDetail productDetail);

    /**
     * 根据商品ID查询 该商品下所有型号
     * @param productId 商品Id
     * @return
     */
    List<ProductDetail> selectAllDetail(String productId);

    /**
     * 根据Id查询一条记录
     * @param productDetailId 商品详情ID
     */
    ProductDetail selectOneDetail(String productDetailId);

    /**
     * 删除一条商品详情记录
     * @param productDetailId 商品详情ID
     */
    int deleteByIdDetail(String productDetailId);

    /**
     * 更新商品详细
     * @param productDetail 商品详细实体
     */
    int updateDetail(ProductDetail productDetail);
}
