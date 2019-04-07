package pers.zjc.sams.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.zp.android.zlib.utils.TimeUtils;

import java.util.Date;

public class Student implements Parcelable {
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

    public String getSexStr() {
        switch (sex) {
            case 0:
                return "未知";
            case 1:
                return "男";
            case 2:
                return "女";
            default:
                return "未知";
        }
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

    public String getStatusStr() {
        switch (status) {
            case 0:
                return "正常";
            case 1:
                return "注销";
            default:
                return "未知";
        }
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? "未录入" : major.trim();
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.stuId);
        dest.writeValue(this.classId);
        dest.writeLong(this.birthday != null ? this.birthday.getTime() : -1);
        dest.writeString(this.idCard);
        dest.writeString(this.sName);
        dest.writeString(this.email);
        dest.writeString(this.tel);
        dest.writeInt(this.sex);
        dest.writeValue(this.status);
        dest.writeString(this.major);
    }

    public Student() {}

    protected Student(Parcel in) {
        this.stuId = (Integer)in.readValue(Integer.class.getClassLoader());
        this.classId = (Integer)in.readValue(Integer.class.getClassLoader());
        long tmpBirthday = in.readLong();
        this.birthday = tmpBirthday == -1 ? null : new Date(tmpBirthday);
        this.idCard = in.readString();
        this.sName = in.readString();
        this.email = in.readString();
        this.tel = in.readString();
        this.sex = in.readInt();
        this.status = (Integer)in.readValue(Integer.class.getClassLoader());
        this.major = in.readString();
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {return new Student(source);}

        @Override
        public Student[] newArray(int size) {return new Student[size];}
    };
}