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
}
