/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * request参数获取.
 *
 * @author limozhi on 2020/12/28
 */
public class ParamUtils {

    /**
     * 得到参数
     *
     * @param request {@link HttpServletRequest}.
     * @param paramName 参数名
     * @param defaultStr 默认值
     */
    public static String getString(HttpServletRequest request, String paramName, String defaultStr) {
        String value = request.getParameter(paramName);
        if (value == null) {
            return defaultStr;
        }
        return value.trim();
    }

    /**
     * 得到字符串数组参数
     *
     * @param request {@link HttpServletRequest}.
     * @param paramName 参数名.
     */
    public static String[] getStringArray(HttpServletRequest request, String paramName) {
        return request.getParameterValues(paramName);
    }

    /**
     * 得到整形参数
     *
     * @param request {@link HttpServletRequest}.
     * @param paramName 参数名
     * @param defaultInt 默认值
     */
    public static Integer getInteger(HttpServletRequest request, String paramName, int defaultInt) {
        String value = request.getParameter(paramName);
        if (value == null) {
            return defaultInt;
        }
        try {
            return new Integer(value);
        } catch (NumberFormatException e) {
            return defaultInt;
        }
    }

    /**
     * 得到整形数组参数
     *
     * @param request {@link HttpServletRequest}.
     * @param paramName 参数名.
     */
    public static Integer[] getIntegerArray(HttpServletRequest request, String paramName) {
        String[] values = getStringArray(request, paramName);
        if (values == null) {
            return null;
        }
        List<Integer> list = new ArrayList<Integer>();
        for (String value : values) {
            try {
                Integer paramValue = new Integer(value);
                list.add(paramValue);
            } catch (NumberFormatException e) {
            }
        }
        return list.toArray(new Integer[] {});
    }

    /**
     * 得到长整形参数
     *
     * @param request {@link HttpServletRequest}.
     * @param paramName 参数名
     * @param defaultLong 默认值
     */
    public static Long getLong(HttpServletRequest request, String paramName, long defaultLong) {
        String value = request.getParameter(paramName);
        if (value == null) {
            return defaultLong;
        }
        try {
            return new Long(value);
        } catch (NumberFormatException e) {
            return defaultLong;
        }
    }

    /**
     * 得到长整形数组参数
     *
     * @param request {@link HttpServletRequest}.
     * @param paramName 参数名
     */
    public static Long[] getLongArray(HttpServletRequest request, String paramName) {
        String[] values = getStringArray(request, paramName);
        if (values == null) {
            return null;
        }
        List<Long> list = new ArrayList<Long>();
        for (String value : values) {
            try {
                Long paramValue = new Long(value);
                list.add(paramValue);
            } catch (NumberFormatException e) {
            }
        }
        return list.toArray(new Long[] {});
    }

    /**
     * 得到小数参数
     *
     * @param request {@link HttpServletRequest}.
     * @param paramName 参数名
     * @param defaultDouble 默认值
     */
    public static Double getDouble(HttpServletRequest request, String paramName, double defaultDouble) {
        String value = request.getParameter(paramName);
        if (value == null) {
            return defaultDouble;
        }
        try {
            return new Double(value);
        } catch (NumberFormatException e) {
            return defaultDouble;
        }
    }

    /**
     * 得到小数数组参数
     *
     * @param request {@link HttpServletRequest}.
     * @param paramName 参数名
     */
    public static Double[] getDoubleArray(HttpServletRequest request, String paramName) {
        String[] values = getStringArray(request, paramName);
        if (values == null) {
            return null;
        }
        List<Double> list = new ArrayList<Double>();
        for (String value : values) {
            try {
                Double paramValue = new Double(value);
                list.add(paramValue);
            } catch (NumberFormatException e) {
            }
        }
        return list.toArray(new Double[] {});
    }

}
