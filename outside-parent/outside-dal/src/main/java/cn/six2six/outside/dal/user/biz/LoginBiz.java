/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.dal.user.biz;

import cn.six2six.outside.common.constant.ClientTypeEnum;
import cn.six2six.outside.common.constant.IDEnum;
import cn.six2six.outside.common.constant.RoleTypeEnum;
import cn.six2six.outside.common.result.ResultBean;
import cn.six2six.outside.common.utils.IDMakerUtils;
import cn.six2six.outside.dal.user.dao.UserTokenDAO;
import cn.six2six.outside.dal.user.dao.WxUserDAO;
import cn.six2six.outside.dal.user.mapping.UserToken;
import cn.six2six.outside.dal.user.mapping.WxUser;
import cn.six2six.outside.dal.user.service.LoginService;
import cn.six2six.outside.dal.wx.biz.WeChatMiniAppInterfaceBiz;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.UUID;

/**
 * 登陆Biz
 *
 * @author limozhi on 2020/12/28
 */
@Service
public class LoginBiz {

    @Value(value = "${miniapp.common.appid}")
    private String appId;

    @Value(value = "${miniapp.common.secret}")
    private String secret;

    @Resource
    private WxUserDAO wxUserDAO;
    @Resource
    private UserTokenDAO userTokenDAO;

    @Resource
    private LoginService loginService;
    @Resource
    private WeChatMiniAppInterfaceBiz weChatMiniAppInterfaceBiz;

    /**
     * 登陆校验.
     *
     * @return {@link ResultBean}.
     */
    public ResultBean loginCheck(String code,HttpServletResponse response){
        //小程序登陆凭证校验.
        String res = weChatMiniAppInterfaceBiz.loginCheck(code);

        JSONObject jsonObject = JSONObject.parseObject(res);
        if(jsonObject.getInteger("errcode")!=0){
            return ResultBean.failed(jsonObject.getString("errmsg"));
        }

        //查询用户信息
        String unionId = jsonObject.getString("unionid");
        String openId = jsonObject.getString("openid");
        WxUser wxUser = wxUserDAO.selectByUnionId(unionId);
        UserToken userToken = null;

        //当用户不存在时，进行新建用户.
        if(wxUser==null){
            wxUser = _makeWxUser(unionId, openId);
            userToken = _makeUserToken(wxUser.getUserId());
            loginService.saveUserInfo(wxUser,userToken);
        }else{
            userToken = userTokenDAO.selectByUserId(wxUser.getUserId());
        }

        Cookie cookie = new Cookie("token",userToken.getToken());
        response.addCookie(cookie);
        return ResultBean.success();
    }

    /**
     * 构建微信用户.
     *
     * @param unionId 微信唯一标志.
     * @param openId openId
     * @return {@link WxUser}.
     */
    private WxUser _makeWxUser(String unionId,String openId) {
        WxUser wxUser = new WxUser();
        Date now = new Date();
        wxUser.setUserId(IDMakerUtils.makeID(IDEnum.USER_ID));
        wxUser.setUnionId(unionId);
        wxUser.setOpenId(openId);
        wxUser.setRoleType(RoleTypeEnum.VISITOR.getRoleType());
        wxUser.setCreateTime(now);
        wxUser.setUpdateTime(now);
        return wxUser;
    }

    /**
     * 构建用户token信息.
     */
    private UserToken _makeUserToken(String userId) {
        Date now = new Date();
        UserToken userToken = new UserToken();
        userToken.setUserId(userId);
        userToken.setClientType(ClientTypeEnum.MINIAPP.name().toLowerCase());
        userToken.setToken(UUID.randomUUID().toString());
        userToken.setCreateTime(now);
        userToken.setCreateTime(now);
        return userToken;
    }
}
