/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.common.constant;

import cn.six2six.outside.common.result.ResultBean;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * 状态码定义.
 *
 * @author lmz on 2020/12/24
 */
public final class Status {

    /**
     * 状态码.
     */
    private int code;
    /**
     * 状态描述.
     */
    private String phrase;

    private Status(int code, String phrase) {
        this.code = code;
        this.phrase = phrase;
    }

    public int getCode() {
        return code;
    }

    public String getPhrase() {
        return phrase;
    }

    /**
     * 构造{@link Status}.
     */
    public static Status valueOf(int code, String phrase) {
        return new Status(code, phrase);
    }

    /**
     * 通过自身构造{@link ResultBean}.
     *
     * @return {@link ResultBean}.
     */
    public <T> ResultBean<T> toResultBean(T data) {
        return new ResultBean<T>().setCode(getCode()).setMessage(getPhrase()).setData(data);
    }

    /**
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Status status = (Status) o;
        return code == status.code &&
                Objects.equal(phrase, status.phrase);
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(code, phrase);
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("phrase", phrase)
                .toString();
    }
}
