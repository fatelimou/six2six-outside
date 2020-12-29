package cn.six2six.outside.common.result;

import cn.six2six.outside.common.constant.CommonStatus;
import cn.six2six.outside.common.constant.Status;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * 接口交互协议对象.
 *
 * @author lmz on unknown date.
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ResultBean<T> implements Serializable {

    /**
     * 返回处理code,包括模块代码以及功能代码等信息,默认为0.
     */
    @JSONField(name = "errcode")
    private int code = CommonStatus.SUCCESS.getCode();
    /**
     * 返回处理状态说明,描述.
     */
    @JSONField(name = "errmsg")
    private String message;
    /**
     * 返回的业务数据.
     */
    @JSONField(name = "result")
    private T data;

    /**
     * 状态, 接口中的保留字段, 大部分情况下为0.
     */
    private Integer status = 0;

    /**
     * 用户TOKEN.
     */
    private String token;

    /**
     * 通过返回代码和返回信息构建{@link ResultBean}.
     *
     * @param code    返回代码.
     * @param message 返回信息.
     * @return {@link ResultBean}.
     */
    public static <T> ResultBean<T> result(int code, String message) {
        ResultBean result = new ResultBean();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 通过{@link Status}实现对象和数据构建{@link ResultBean}.
     *
     * @param result {@link Status}.
     * @return {@link ResultBean}.
     */
    public static <T> ResultBean<T> result(Status result) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(result.getCode());
        resultBean.setMessage(result.getPhrase());
        return resultBean;
    }

    /**
     * 通过{@link Status}实现对象和数据构建{@link ResultBean}.
     *
     * @param result {@link Status}.
     * @param data   数据.
     * @return {@link ResultBean}.
     */
    public static <T> ResultBean<T> result(Status result, T data) {
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(result.getCode());
        resultBean.setMessage(result.getPhrase());
        resultBean.setData(data);
        return resultBean;
    }

    /**
     * 构建成功响应的{@link ResultBean}对象.
     *
     * @return {@link ResultBean}.
     */
    public static <T> ResultBean<T> success() {
        ResultBean result = new ResultBean();
        result.setCode(CommonStatus.SUCCESS.getCode());
        result.setMessage(CommonStatus.SUCCESS.getPhrase());
        return result;
    }

    /**
     * 通过返回信息构建成功响应的{@link ResultBean}对象.
     *
     * @param message 返回信息.
     * @return {@link ResultBean}.
     */
    public static <T> ResultBean<T> success(String message) {
        ResultBean result = new ResultBean();
        result.setMessage(message);
        result.setCode(CommonStatus.SUCCESS.getCode());
        return result;
    }

    /**
     * 通过返回信息构建成功响应的{@link ResultBean}对象.
     *
     * @param message 返回信息.
     * @return {@link ResultBean}.
     */
    public static <T> ResultBean<T> success(String message, T data) {
        ResultBean result = new ResultBean();
        result.setMessage(message);
        result.setCode(CommonStatus.SUCCESS.getCode());
        result.setData(data);
        return result;
    }

    /**
     * 通过数据构建成功响应的{@link ResultBean}对象.
     *
     * @param data 返回数据.
     * @return {@link ResultBean}.
     */
    public static <T> ResultBean<T> successForData(T data) {
        ResultBean<T> result = new ResultBean<T>();
        result.setMessage(CommonStatus.SUCCESS.getPhrase());
        result.setCode(CommonStatus.SUCCESS.getCode());
        result.setData(data);
        return result;
    }

    /**
     * 通过返回错误信息构建失败响应的{@link ResultBean}.
     *
     * @return {@link ResultBean}.
     */
    public static <T> ResultBean<T> failed() {
        ResultBean result = new ResultBean();
        result.setCode(CommonStatus.FAIL.getCode());
        result.setMessage(CommonStatus.FAIL.getPhrase());
        return result;
    }

    public static <T> ResultBean<T> failed(ResultBean resultBean) {
        ResultBean result = new ResultBean();
        result.setCode(resultBean.getCode());
        result.setMessage(resultBean.getMessage());
        return result;
    }

    /**
     * 通过返回错误信息构建失败响应的{@link ResultBean}.
     *
     * @param message 返回错误信息.
     * @return {@link ResultBean}.
     */
    public static <T> ResultBean<T> failed(String message) {
        ResultBean result = new ResultBean();
        result.setCode(CommonStatus.FAIL.getCode());
        result.setMessage(message);
        return result;
    }

    /**
     * 通过返回代码和返回信息构建失败响应的{@link ResultBean}.
     *
     * @param code    返回代码.
     * @param message 返回信息.
     * @return {@link ResultBean}.
     */
    public static <T> ResultBean<T> failed(int code, String message) {
        ResultBean result = new ResultBean();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 通过返回代码和返回信息构建失败响应的{@link ResultBean}.
     *
     * @param code    返回代码.
     * @param message 返回信息.
     * @param data    返回数据.
     * @return {@link ResultBean}.
     */
    public static <T> ResultBean<T> failed(int code, String message, T data) {
        ResultBean result = new ResultBean();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 通过{@link Status}实现对象实例构建失败响应的{@link ResultBean}.
     *
     * @param error {@link Status}实现对象实例.
     * @return {@link ResultBean}.
     */
    public static <T> ResultBean<T> failed(Status error) {
        ResultBean result = new ResultBean();
        result.setCode(error.getCode());
        result.setMessage(error.getPhrase());
        return result;
    }

    /**
     * 通过{@link Status}实现对象实例构建失败响应的{@link ResultBean}。
     *
     * @param error {@link Status}实现对象实例.
     * @return {@link ResultBean}.
     */
    public static <T> ResultBean<T> failed(Status error, T data) {
        ResultBean result = new ResultBean();
        result.setCode(error.getCode());
        result.setMessage(error.getPhrase());
        result.setData(data);
        return result;
    }

    /**
     * 响应失败, 设置返回代码和返回信息, 返回当前实例.
     *
     * @param code    返回代码.
     * @param message 返回信息.
     * @return {@link ResultBean}.
     */
    public ResultBean<T> setFailed(int code, String message) {
        this.setCode(code);
        this.setMessage(message);
        return this;
    }

    /**
     * 响应失败, 设置返回代码和返回信息, 返回当前实例.
     *
     * @param error {@link Status}.
     * @return {@link ResultBean}.
     */
    public ResultBean<T> setFailed(Status error) {
        this.setCode(error.getCode());
        this.setMessage(error.getPhrase());
        return this;
    }

    /**
     * {@link #success}的getter方法.
     *
     * @return true:成功; false: 失败.
     */
    public boolean isSuccess() {
        return code == CommonStatus.SUCCESS.getCode();
    }

    /**
     * 建议用{@link #setCode(int)}.
     * <p>
     * {@link #success}的setter方法.
     *
     * @param success true: 成功; false:失败.
     */
    public ResultBean<T> setSuccess(boolean success) {
        code = success ? CommonStatus.SUCCESS.getCode() : CommonStatus.FAIL.getCode();
        return this;
    }

    /**
     * {@link #code}的getter方法.
     *
     * @return 返回代码.
     */
    public int getCode() {
        return code;
    }

    /**
     * {@link #code}的setter方法.
     *
     * @param code 返回代码.
     */
    public ResultBean<T> setCode(int code) {
        this.code = code;
        return this;
    }

    /**
     * {@link #message}的getter方法.
     *
     * @return 返回信息.
     */
    public String getMessage() {
        return message;
    }

    /**
     * {@link #message}的setter方法.
     *
     * @param message 返回信息.
     */
    public ResultBean<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * {@link #data}的getter方法.
     *
     * @return 返回数据.
     */
    public T getData() {
        return data;
    }

    /**
     * {@link #data}的setter方法.
     *
     * @param data 返回数据.
     */
    public ResultBean<T> setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * {@link #status}的getter方法.
     *
     * @return {@link #status}.
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * {@link #status}的setter方法.
     *
     * @param status {@link #status}.
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * {@link #token}的getter方法.
     *
     * @return {@link #token}.
     */
    public String getToken() {
        return token;
    }

    /**
     * {@link #token}的setter方法.
     *
     * @param token {@link #token}.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 自身{@link ResultBean}转换为JSON字符串.
     *
     * <p>
     *     利用fastjson库.
     * </p>
     *
     * @return JSON格式字符串.
     */
    public String toJSON() {
        return JSONObject.toJSONString(this);
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", code)
                .add("message", message)
                .add("data", data)
                .add("status", status)
                .add("token", token)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResultBean)) return false;
        ResultBean<?> that = (ResultBean<?>) o;
        return getCode() == that.getCode() &&
                Objects.equal(getMessage(), that.getMessage()) &&
                Objects.equal(getData(), that.getData()) &&
                Objects.equal(getStatus(), that.getStatus()) &&
                Objects.equal(getToken(), that.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCode(), getMessage(), getData(), getStatus(), getToken());
    }
}
