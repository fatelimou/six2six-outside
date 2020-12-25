/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.admin.config.interceptor;

import cn.six2six.outside.common.annotation.IngoreUserSession;
import cn.six2six.outside.common.constant.ResultConstantEnum;
import cn.six2six.outside.common.result.ResultBean;
import cn.six2six.outside.common.utils.HttpServletResponseUtils;
import cn.six2six.outside.common.utils.TokenUtils;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 用户token检查拦截器.
 *
 * @author limozhi on 2020/12/25
 */
@Component
public class UserLoginTokenCheckExtractorInterceptor implements HandlerInterceptor {

    /**
     * 随便给的一个order值，后续很多interceptor需要依赖登录的状态, 所以登录必须是最靠前的检查
     * {@link UserLoginTokenCheckExtractorInterceptor#ORDER} 用户信息就是用这个ORDER+1
     */
    public static final int ORDER = 100;

    /**
     * 接口方法执行前拦截.
     *
     * @param request {@link HttpServletRequest}.
     * @param response {@link HttpServletResponse}.
     * @param handler {@link Object}.
     * @return {@link Boolean}.
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod =(HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查Controller上加了IngoreUserSession注解，就不用去加载用户信息.
        if(method.getDeclaringClass().isAnnotationPresent(IngoreUserSession.class)){
            return true;
        }
        if(method.isAnnotationPresent(IngoreUserSession.class)){
            return true;
        }

        String token = TokenUtils.getToken(request);
        if(Strings.isNullOrEmpty(token)){
            _responseTokenExpire(response);
            return false;
        }

        return true;
    }

    /**
     * 向返回信息报文加入resultBean的string
     *
     * @param response {@link HttpServletResponse}
     */
    private void _responseTokenExpire(HttpServletResponse response) {
        HttpServletResponseUtils.outPut(ResultBean.failed(ResultConstantEnum.USER_TOKEN_IS_EXPIRE.getValue(),ResultConstantEnum.USER_TOKEN_IS_EXPIRE.getMessage()), response);
    }
}
