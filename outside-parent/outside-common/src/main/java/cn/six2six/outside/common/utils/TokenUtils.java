/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.common.utils;

import com.google.common.base.Strings;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;

/**
 * 用户token解析工具.
 *
 * @author limozhi on 2020/12/25
 */
public class TokenUtils {

    public static String getToken(HttpServletRequest request){
        String tokenStr;

        /**
         * 1.QueryString获取token.
         */
        if (!Strings.isNullOrEmpty(request.getQueryString())) {
            String queryStr = URLDecoder.decode(request.getQueryString());
            String[] queryArray = queryStr.split("&");
            String key = "";
            for (String temp : queryArray) {
                if (temp.indexOf("=") <= 0) {
                    continue;
                }
                key = temp.substring(0, temp.indexOf("="));
                if(!"token".equals(key)){
                    continue;
                }
                tokenStr = temp.substring(temp.indexOf("=") + 1);
                if(!Strings.isNullOrEmpty(tokenStr)){
                    return tokenStr;
                }
            }
        }

        /**
         * 2.从请求头获取.
         */
        tokenStr = request.getHeader("token");
        if(!Strings.isNullOrEmpty(tokenStr)&&!"null".equalsIgnoreCase(tokenStr)){
            return tokenStr;
        }

        /**
         * 3.从请求体参数获取.
         */
        tokenStr = request.getParameter("token");
        if(!Strings.isNullOrEmpty(tokenStr)&&!"null".equalsIgnoreCase(tokenStr)){
            return tokenStr;
        }

        /**
         * 4.从表单请求属性获取.
         */
        tokenStr = (String)request.getAttribute("token");
        if(Strings.isNullOrEmpty(tokenStr)&&!"null".equalsIgnoreCase(tokenStr)){
            return tokenStr;
        }

        /**
         * 5.cookie中获取token.
         */
        Cookie[] ck = request.getCookies();
        if (!isNullOrContainsNull(ck)) {
            for (Cookie cookie : ck) {
                if (cookie.getName().equals("token")) {
                    tokenStr = cookie.getValue();
                    if (!Strings.isNullOrEmpty(tokenStr) && !tokenStr.equalsIgnoreCase("null")) {
                        return tokenStr;
                    }
                }
            }
        }

        return null;
    }

    /**
     * 判空.
     *
     * @param array {@link Object[]}
     * @return {@link Boolean}
     */
    public static Boolean isNullOrContainsNull(Object[] array){
        if (null == array){
            return true;
        }
        int count = array.length;
        for (int i = 0; i < count; ++i) {
            if (null == array[i]) {
                return true;
            }
        }
        return false;
    }
}
