/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.common.exception;

/**
 * 异常
 *
 * @author limozhi on 2020/12/24
 */
public class OutsideException extends RuntimeException{

    /**
     * 包装{@link Throwable}.
     */
    public OutsideException(Throwable cause) {
        super(cause);
    }


    /**
     * 自定义例外信息.
     */
    public OutsideException(String message) {
        super(message);
    }

}
