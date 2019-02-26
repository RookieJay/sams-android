package pers.zjc.sams.data.entity;


import com.zp.android.zlib.utils.TimeUtils;

import java.util.Date;

import pers.zjc.sams.common.Const;

public class User {
    private Integer id;

    private String account;

    private String password;

    private Date createTime;

    private Date updateTime;

    private Integer role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getCreateTime() {
        return TimeUtils.date2String(createTime, Const.DateFormat.WITH_HMS);
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return TimeUtils.date2String(updateTime, Const.DateFormat.WITH_HMS);
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", role=" + role +
                '}';
    }
}

