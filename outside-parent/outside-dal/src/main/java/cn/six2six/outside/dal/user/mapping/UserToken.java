/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.dal.user.mapping;


import lombok.Data;

import java.util.Date;

/**
 * 用户token
 *
 * @author limozhi on 2020/12/25
 */
@Data
public class UserToken {

    /**
     * 主键ID.
     */
    private Long id;

    /**
     * 用户ID.
     */
    private String userId;

    /**
     * 用户token.
     */
    private String token;

    /**
     * 客户端.
     */
    private String clientType;

    /**
     * 创建时间.
     */
    private Date createTime;

    /**
     * 更新时间.
     */
    private Date updateTime;

    public UserToken() {
        super();
    }
}
