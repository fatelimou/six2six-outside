/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.dal.wx.biz;

import cn.six2six.outside.common.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 微信小程序接口Biz
 *
 * @author limozhi on 2020/12/28
 */
@Service
public class WeChatMiniAppInterfaceBiz {

    @Value("${miniapp.common.appid:}")
    public String appId;
    @Value("${miniapp.common.secret:}")
    public String secret;

    /**
     * 小程序登陆凭证校验.
     *
     * @param code 登录时获取的code.
     * @return {@link String}.
     */
    public String loginCheck(String code){
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        url.replace("APPID",appId);
        url.replace("SECRET",secret);
        url.replace("JSCODE",code);

        String res = HttpUtils.get(url, null, r -> {
            return r;
        });
        return res;
    }
}
