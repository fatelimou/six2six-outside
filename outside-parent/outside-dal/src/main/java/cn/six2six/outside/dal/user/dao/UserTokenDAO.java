/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.dal.user.dao;

import cn.six2six.outside.dal.user.mapping.UserToken;
import org.apache.ibatis.annotations.Param;

/**
 * 用户token数据访问层.
 * <p>tb_user_token</p>
 *
 * @author limozhi on 2020/12/25
 */
public interface UserTokenDAO {

    /**
     * 插入一条用户token记录.
     */
    int insert(UserToken userToken);

    UserToken selectByToken(@Param("token") String token);
}
