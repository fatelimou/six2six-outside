/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.dal.user.dao;

import cn.six2six.outside.dal.user.mapping.WxUser;

/**
 * 微信授权用户.
 *
 * @author limozhi on 2020/12/04
 */
public interface WxUserDAO {

    /**
     * 插入一条微信授权用户记录.
     *
     * @param wxUser {@link WxUser}.
     */
    int insert(WxUser wxUser);
}
