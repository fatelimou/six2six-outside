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
 * 角色枚举.
 *
 * @author limozhi on 2020/12/28
 */
@Getter
public enum RoleTypeEnum {
    //0游客1职员2经理3管理员4超级管理员
    VISITOR(0),
    CLERK(1),
    MANAGER(2),
    ADMIN(3),
    SUPER_ADMIN(4);

    private Integer roleType;

    RoleTypeEnum(Integer roleType) {
        this.roleType = roleType;
    }
}
