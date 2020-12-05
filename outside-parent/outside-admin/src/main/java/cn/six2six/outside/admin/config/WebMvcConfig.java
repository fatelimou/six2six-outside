/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * TODO:注解
 *
 * @author limozhi on 2020/12/04
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * @Resource依赖拦截器对象.
     */

    /**
     * 增加拦截器.
     *
     * @param registry {@link InterceptorRegistry}.
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logContextInterceptor);
        registry.addInterceptor(debugSwitchInterceptor);
        registry.addInterceptor(optLogInterceptor).addPathPatterns("/**");
        registry.addInterceptor(pageHelperInterceptor).addPathPatterns("/**");
        registry.addInterceptor(initPasswordInterceptor).addPathPatterns("/","/index");
    }
    */

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
