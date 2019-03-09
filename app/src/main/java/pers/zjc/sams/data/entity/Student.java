package pers.zjc.sams.data.entity;

import com.zp.android.zlib.utils.TimeUtils;

import java.util.Date;

public class Student {
    private Integer stuId;

    private Integer classId;

    private Date birthday;

    private String idCard;

    private String sName;

    private String email;

    private String tel;

    private int sex;

    private Integer status;

    private String major;

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday == null ? TimeUtils.string2Date("2000-01-01") : birthday;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? "未录入" : idCard.trim();
    }

    public String getName() {
        return sName;
    }

    public void setName(String name) {
        this.sName = name == null ? "未录入" : name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? "未录入" : email.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? "未录入" : tel.trim();
    }


    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? "未录入" : major.trim();
    }
}