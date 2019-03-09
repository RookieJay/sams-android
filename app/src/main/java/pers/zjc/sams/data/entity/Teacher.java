package pers.zjc.sams.data.entity;

public class Teacher {
    private Integer id;

    private String major;

    private String tName;

    private String email;

    private String tel;

    private int sex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? "未录入" : major.trim();
    }

    public String getName() {
        return tName;
    }

    public void setName(String name) {
        this.tName = name == null ? "未录入" : name.trim();
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
        this.tel = tel == null ? null : tel.trim();
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}