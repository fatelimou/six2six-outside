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
import cn.six2six.outside.dal.user.mapping.UserToken;
import cn.six2six.outside.dal.user.mapping.WxUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;

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

    /**
     * 获取用户信息.
     *
     * @param token 用户token
     * @return {@link WxUser}.
     */
    public WxUser findWxUser(String token){
        if(redisTemplate.hasKey(token)){
            return (WxUser)redisTemplate.opsForValue().get(token);
        }
        UserToken userToken = userTokenDAO.selectByToken(token);
        if(userToken==null){
            return null;
        }
        WxUser wxUser = wxUserDAO.selectByUserId(userToken.getUserId());
        if(wxUser==null){
            return null;
        }
        redisTemplate.opsForValue().set(token,wxUser, Duration.ofDays(2));
        return wxUser;
    }
}
