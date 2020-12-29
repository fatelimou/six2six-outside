/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户会话本地线程对象
 *
 * @author limozhi on 2020/12/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSessionThreadLocalBean implements Serializable {

    /**
     * 用户ID.
     */
    private String userId;

    /**
     * 用户角色.
     */
    private Integer roleType;

    /**
     * 版本.
     */
    private Integer version;

    /**
     * 用户token.
     */
    private String token;
}
