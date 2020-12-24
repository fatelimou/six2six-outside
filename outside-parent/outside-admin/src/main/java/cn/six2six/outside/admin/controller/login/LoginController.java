/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.admin.controller.login;

import cn.six2six.outside.common.result.ResultBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 登陆控制器.
 *
 * @author limozhi on 2020/12/24
 */
@RestController
@RequestMapping(value = "/login")
public class LoginController {

    /**
     * 登陆.
     *
     * @param request {@link HttpServletRequest}.
     * @return {@link ResultBean}.
     */
    @RequestMapping(value = "/")
    public ResultBean login(HttpServletRequest request){
        return ResultBean.success();
    }

}
