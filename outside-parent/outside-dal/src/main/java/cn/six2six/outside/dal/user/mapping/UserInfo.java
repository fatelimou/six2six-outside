package cn.six2six.outside.dal.user.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author : ZengRong
 * @date : 2020-12-25 19:28
 * @description : 用户信息表
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserInfo {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户username
     */
    private String login_username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     *用户密码
     */
    private String password;

    /**
     * 绑定手机号
     */
    private String boundCellphone;

    /**
     * 最后一次登陆
     */
    private Date last_login_time;

    /**
     *是否修改1非修改0
     */
    private String modify_pwd_or;

    /**
     *身份角色
     */
    private String role_type;

    /**
     *是否可用
     */
    private String valid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}
