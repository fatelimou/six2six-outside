/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.admin.controller.login;

import cn.six2six.outside.common.constant.ResultConstantEnum;
import cn.six2six.outside.common.result.ResultBean;
import cn.six2six.outside.common.utils.ParamUtils;
import cn.six2six.outside.dal.user.biz.LoginBiz;
import com.google.common.base.Strings;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆控制器.
 *
 * @author limozhi on 2020/12/24
 */
@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Resource
    private LoginBiz loginBiz;

    /**
     * 登陆.
     *
     * @param request {@link HttpServletRequest}.
     * @return {@link ResultBean}.
     */
    @RequestMapping(value = "")
    public ResultBean login(HttpServletRequest request, HttpServletResponse response){
        /**
         * 1.获取参数.
         */
        String code = ParamUtils.getString(request, "code", "");

        /**
         * 2.check参数.
         */
        ResultBean resultBean = _checkParams(code);
        if(!resultBean.isSuccess()){
            return resultBean;
        }

        /**
         * 3.返回结果vo.
         */
        return loginBiz.loginCheck(code,response);
    }

    /**
     * check参数.
     *
     * @param code code码.
     * @return {@link ResultBean}.
     */
    private ResultBean _checkParams(String code) {
        if(Strings.isNullOrEmpty(code)){
            return ResultBean.failed(ResultConstantEnum.USER_TOKEN_IS_EXPIRE.getValue(),ResultConstantEnum.USER_TOKEN_IS_EXPIRE.getMessage());
        }
        return ResultBean.success();
    }

}
