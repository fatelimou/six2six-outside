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
 * 管理系统用户表.
 *
 * @author limozhi on 2020/12/14s
 */
@Data
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
     * 角色身份.
     */
    private Integer roleType;

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
}
