/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.common.utils;

import cn.six2six.outside.common.exception.OutsideException;
import cn.six2six.outside.common.result.ResultBean;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 响应的一些工具类.
 *
 * @author limozhi on 2020/12/25
 */
public class HttpServletResponseUtils {

    /**
     * 强制输出json给前端.
     */
    public static void outPut(ResultBean<?> resultBean, HttpServletResponse response) {
        if (resultBean == null) {
            throw new OutsideException("resultBean输出为NULL.");
        }
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(JSONObject.toJSONString(resultBean));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 获取版本号, 作为整型返回.
     *
     * @param request {@link HttpServletRequest}.
     * @return 版本号.
     */
    public static Integer getVersionAsInteger(HttpServletRequest request) {
        if(request==null){
            throw new OutsideException("请求不能为空");
        }
        String version = getVersion(request);
        if (Strings.isNullOrEmpty(version)) {
            return null;
        }
        try {
            return Integer.parseInt(version);
        } catch (NumberFormatException ex) {
            throw new OutsideException(ex);
        }
    }

    public static String getVersion(HttpServletRequest request){
        /**
         * 1.请求参数获取版本号.
         */
        String version = request.getParameter("version");
        if(!Strings.isNullOrEmpty(version)){
            return version;
        }

        /**
         * 2.请求头获取版本号.
         */
        version = request.getHeader("version");
        if(!Strings.isNullOrEmpty(version)){
            return version;
        }

        return null;
    }
}
