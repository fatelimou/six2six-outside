/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.dal.user.service;

import cn.six2six.outside.common.exception.OutsideException;
import cn.six2six.outside.dal.user.dao.UserTokenDAO;
import cn.six2six.outside.dal.user.dao.WxUserDAO;
import cn.six2six.outside.dal.user.mapping.UserToken;
import cn.six2six.outside.dal.user.mapping.WxUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * TODO:注解
 *
 * @author limozhi on 2020/12/28
 */
@Service
@Transactional
public class LoginService {

    @Resource
    private WxUserDAO wxUserDAO;

    @Resource
    private UserTokenDAO userTokenDAO;

    /**
     * 保持用户信息.
     *
     * @param wxUser {@link WxUser}
     * @param userToken {@link UserToken}.
     */
    public void saveUserInfo(WxUser wxUser, UserToken userToken){
        int count = wxUserDAO.insert(wxUser);
        if(count!=1){
            throw new OutsideException("wxUser insert count err,The expectation is 1.but now is "+count);
        }

        count = userTokenDAO.insert(userToken);
        if(count!=1){
            throw new OutsideException("userToken insert count err,The expectation is 1.but now is "+count);
        }
    }
}
