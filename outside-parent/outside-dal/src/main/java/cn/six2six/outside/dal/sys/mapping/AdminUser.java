/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.dal.sys.mapping;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 管理系统用户表.
 *
 * @author limozhi on 2020/12/14s
 */
public class AdminUser implements Serializable {

    private Long id;

    /**
     * 用户名.
     */
    private String loginUsername;

    /**
     * 姓名.
     */
    private String nickname;

    /**
     * 密码.
     */
    private String password;

    /**
     * 绑定手机号.
     */
    private String boundCellphone;

    /**
     * 最后登录时间.
     */
    private Date lastLoginTime;

    /**
     * 状态, 0:正常 ,1:失效.
     */
    private Integer status;

    /**
     * 创建时间.
     */
    private Date createTime;

    /**
     * 更新时间.
     */
    private Date updateTime;

    /**
     * 是否修改过密码: 1=是, 0=否.
     */
    private Integer modifyPwdOr;


    public AdminUser() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBoundCellphone() {
        return boundCellphone;
    }

    public void setBoundCellphone(String boundCellphone) {
        this.boundCellphone = boundCellphone;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getModifyPwdOr() {
        return modifyPwdOr;
    }

    public void setModifyPwdOr(Integer modifyPwdOr) {
        this.modifyPwdOr = modifyPwdOr;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("loginUsername", loginUsername)
                .add("nickname", nickname)
                .add("password", password)
                .add("boundCellphone", boundCellphone)
                .add("lastLoginTime", lastLoginTime)
                .add("status", status)
                .add("createTime", createTime)
                .add("updateTime", updateTime)
                .add("modifyPwdOr", modifyPwdOr)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdminUser adminUser = (AdminUser) o;
        return Objects.equal(id, adminUser.id) &&
                Objects.equal(loginUsername, adminUser.loginUsername) &&
                Objects.equal(nickname, adminUser.nickname) &&
                Objects.equal(password, adminUser.password) &&
                Objects.equal(boundCellphone, adminUser.boundCellphone) &&
                Objects.equal(lastLoginTime, adminUser.lastLoginTime) &&
                Objects.equal(status, adminUser.status) &&
                Objects.equal(createTime, adminUser.createTime) &&
                Objects.equal(updateTime, adminUser.updateTime) &&
                Objects.equal(modifyPwdOr, adminUser.modifyPwdOr);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, loginUsername, nickname, password, boundCellphone, lastLoginTime, status, createTime, updateTime, modifyPwdOr);
    }
}
