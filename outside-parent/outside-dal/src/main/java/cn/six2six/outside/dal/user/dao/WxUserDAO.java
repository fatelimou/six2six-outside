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
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 非空插入一条微信授权用户记录.
     */
    int insertNotNull(WxUser wxUser);

    /**
     * 插入多条微信授权用户记录.
     */
    int batchInsert(@Param("list") List<WxUser> list);

    /**
     * 更新一条微信授权用户记录
     */
    int update(WxUser wxUser);

    /**
     * 非空更新一条微信授权用户记录.
     */
    int updateNotNull(WxUser wxUser);

    /**
     * 查询用户-用户ID.
     *
     * @param userId 用户ID.
     * @return {@link WxUser}.
     */
    WxUser selectByUserId(@Param("userId") String userId);

    /**
     * 查询用户-根据unionId.
     *
     * @param unionId
     * @return
     */
    WxUser selectByUnionId(@Param("unionId") String unionId);
}
