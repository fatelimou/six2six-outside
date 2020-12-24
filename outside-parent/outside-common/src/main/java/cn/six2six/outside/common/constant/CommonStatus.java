/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.common.constant;

/**
 * 通用错误码表.
 *
 * @author lmz on 2020/12/24
 */
public interface CommonStatus {

    Status SUCCESS = Status.valueOf(0, "操作成功");
    Status FAIL = Status.valueOf(1, "操作失败，请重试操作或联系客服人员。");
}