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
import cn.six2six.outside.common.bean.UserSessionThreadLocalBean;
import cn.six2six.outside.common.constant.ResultConstantEnum;
import cn.six2six.outside.common.result.ResultBean;
import cn.six2six.outside.common.utils.HttpServletResponseUtils;
import cn.six2six.outside.common.utils.TokenUtils;
import cn.six2six.outside.dal.user.biz.WxUserBiz;
import cn.six2six.outside.dal.user.dao.WxUserDAO;
import cn.six2six.outside.dal.user.mapping.WxUser;
import com.google.common.base.Strings;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 用户会话拦截器.
 *
 * @author limozhi on 2020/12/25
 */
@Component
public class UserSessionExtractorInterceptor implements HandlerInterceptor {

    public static final int ORDER = UserSessionExtractorInterceptor.ORDER + 1;

    public static final InheritableThreadLocal<UserSessionThreadLocalBean> cache = new InheritableThreadLocal<UserSessionThreadLocalBean>(){
        @Override
        protected UserSessionThreadLocalBean initialValue() {
            return new UserSessionThreadLocalBean();
        }
    };

    public static UserSessionThreadLocalBean getUserSession(){
        return cache.get();
    }

    /**
     * 请求路径为html尾缀的请求
     */
    private static String REQUEST_URL_HTML_SUFFIX = ".html";
    /**
     * 请求路径含"/static/"的常量
     */
    private static String REQUEST_PATH_CONTAINS_STATIC = "/static/";
    private static String REQUEST_PATH_CONTAINS_FAVICON_ICO = "/favicon.ico/";

    @Resource
    private WxUserBiz wxUserBiz;

    /**
     * 预处理回调方法，实现处理器的预处理(判断用户是否登录)
     *
     * @param request  http请求
     * @param response 响应
     * @param handler  请求处理{@link HandlerMethod}
     * @return 返回值：true表示继续流程（如调用下一个拦截器或处理器）；false表示流程中断，不会继续调用其他的拦截器或处理器，
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

        /**
         * 静态资源请求直接返回
         */
        String requestURL = request.getRequestURL().toString();
        if (requestURL.indexOf(REQUEST_PATH_CONTAINS_STATIC) != -1 || requestURL.indexOf(REQUEST_URL_HTML_SUFFIX) != -1 || requestURL.indexOf(REQUEST_PATH_CONTAINS_FAVICON_ICO) != -1) {
            return true;
        }

        /**
         * 获取token,获取不到直接返回
         */
        String token = TokenUtils.getToken(request);
        if (Strings.isNullOrEmpty(token)) {
            HttpServletResponseUtils.outPut(ResultBean.failed(ResultBean.failed(ResultConstantEnum.USER_TOKEN_IS_EXPIRE.getValue(),ResultConstantEnum.USER_TOKEN_IS_EXPIRE.getMessage())), response);
            return false;
        }

        /**
         * 获取版本号.
         */
        Integer version = HttpServletResponseUtils.getVersionAsInteger(request);
        if(version==null){
            version=0;
        }

        /**
         * 获取用户ID与角色身份
         */
        WxUser wxUser = wxUserBiz.findWxUser(token);
        if(wxUser==null){
            HttpServletResponseUtils.outPut(ResultBean.failed(ResultBean.failed(ResultConstantEnum.USER_TOKEN_IS_EXPIRE.getValue(),ResultConstantEnum.USER_TOKEN_IS_EXPIRE.getMessage())), response);
            return false;
        }

        //设置本地线程对象属性.
        UserSessionThreadLocalBean userSessionThreadLocalBean = getUserSession();
        userSessionThreadLocalBean.setRoleType(wxUser.getRoleType());
        userSessionThreadLocalBean.setToken(token);
        userSessionThreadLocalBean.setUserId(wxUser.getUserId());
        userSessionThreadLocalBean.setVersion(version);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
