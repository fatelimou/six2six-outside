/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.dal.user.biz;

import cn.six2six.outside.dal.user.dao.UserTokenDAO;
import cn.six2six.outside.dal.user.dao.WxUserDAO;
import cn.six2six.outside.dal.user.mapping.WxUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户信息Biz
 *
 * @author limozhi on 2020/12/25
 */
@Service
public class WxUserBiz {

    @Resource
    private WxUserDAO wxUserDAO;

    @Resource
    private UserTokenDAO userTokenDAO;

    @Resource
    private RedisTemplate redisTemplate;

    public WxUser findWxUser(String token){

        return null;
    }
}
