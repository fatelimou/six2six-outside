/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.common.constant;

import lombok.Getter;

/**
 * TODO:注解
 *
 * @author limozhi on 2020/12/25
 */
@Getter
public enum ResultConstantEnum {

    /**
     * user_token失效
     */
    USER_TOKEN_IS_EXPIRE(9, "user token is expire");

    private Integer value;
    private String message;

    ResultConstantEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }
}
