/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.admin.config;

import cn.six2six.outside.admin.config.interceptor.UserLoginTokenCheckExtractorInterceptor;
import cn.six2six.outside.admin.config.interceptor.UserSessionExtractorInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 *
 *
 * @author limozhi on 2020/12/04
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private UserLoginTokenCheckExtractorInterceptor userLoginTokenCheckExtractorInterceptor;

    @Resource
    private UserSessionExtractorInterceptor userSessionExtractorInterceptor;


    /**
     * 增加拦截器.
     *
     * @param registry {@link InterceptorRegistry}.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //registry.addInterceptor(userLoginTokenCheckExtractorInterceptor).addPathPatterns("/**").order(UserLoginTokenCheckExtractorInterceptor.ORDER);
        //registry.addInterceptor(userSessionExtractorInterceptor).addPathPatterns("/**").order(UserSessionExtractorInterceptor.ORDER);

    }

    /**
     * 资源地址重定向.
     *
     * @param registry {@link ResourceHandlerRegistry}.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }
    */
}
