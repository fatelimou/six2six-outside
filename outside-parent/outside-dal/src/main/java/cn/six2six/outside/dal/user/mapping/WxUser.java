/**
 * Copyright(c)) 2014-2019 Wegooooo Ltd. All rights reserved.
 * <p>
 * You may not use this file except authorized by Wegooooo.
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed is prohibited.
 */
package cn.six2six.outside.dal.user.mapping;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Date;

/**
 * 管理系统用户表.
 *
 * @author limozhi on 2020/12/14s
 */
public class WxUser{

    /**
     * 主键ID.
     */
    private Long id;

    /**
     * 用户ID.
     */
    private String userId;

    /**
     * 微信唯一标志.
     */
    private String unionId;

    /**
     * openId.
     */
    private String openId;

    /**
     * 姓名.
     */
    private String nickname;

    /**
     * 背景图片.
     */
    private String backImage;

    /**
     * 绑定手机号.
     */
    private Integer gender;

    /**
     * 微信号.
     */
    private String wxNum;

    /**
     * 电话.
     */
    private String wxPhone;

    /**
     * 创建时间.
     */
    private Date createTime;

    /**
     * 更新时间.
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getWxNum() {
        return wxNum;
    }

    public void setWxNum(String wxNum) {
        this.wxNum = wxNum;
    }

    public String getWxPhone() {
        return wxPhone;
    }

    public void setWxPhone(String wxPhone) {
        this.wxPhone = wxPhone;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WxUser wxUser = (WxUser) o;
        return Objects.equal(id, wxUser.id) &&
                Objects.equal(userId, wxUser.userId) &&
                Objects.equal(unionId, wxUser.unionId) &&
                Objects.equal(openId, wxUser.openId) &&
                Objects.equal(nickname, wxUser.nickname) &&
                Objects.equal(backImage, wxUser.backImage) &&
                Objects.equal(gender, wxUser.gender) &&
                Objects.equal(wxNum, wxUser.wxNum) &&
                Objects.equal(wxPhone, wxUser.wxPhone) &&
                Objects.equal(createTime, wxUser.createTime) &&
                Objects.equal(updateTime, wxUser.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, userId, unionId, openId, nickname, backImage, gender, wxNum, wxPhone, createTime, updateTime);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("userId", userId)
                .add("unionId", unionId)
                .add("openId", openId)
                .add("nickname", nickname)
                .add("backImage", backImage)
                .add("gender", gender)
                .add("wxNum", wxNum)
                .add("wxPhone", wxPhone)
                .add("createTime", createTime)
                .add("updateTime", updateTime)
                .toString();
    }
}
