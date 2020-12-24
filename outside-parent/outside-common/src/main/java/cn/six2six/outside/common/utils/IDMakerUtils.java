/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.common.utils;

import cn.six2six.outside.common.constant.IDEnum;

/**
 * ID生成器工具类.
 *
 * @author limozhi on 2020/12/24
 */
public class IDMakerUtils {

    /**
     * ID生成方法.
     *
     * @param idEnum id枚举
     * @return {@link String}.
     */
    public static String makeID(IDEnum idEnum){
        String dateStr = DateTimeUtils.nowAsDateTime12();
        String currentTimeStr = String.valueOf(System.currentTimeMillis());
        String random = String.valueOf((int)(Math.random()*100));
        StringBuilder builder = new StringBuilder();
        return builder.append(idEnum.getPrefix()).append(dateStr).append(currentTimeStr).append(random).toString();
    }

}
