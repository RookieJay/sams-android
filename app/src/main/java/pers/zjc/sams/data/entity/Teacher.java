package pers.zjc.sams.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Teacher implements Parcelable {
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

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.major);
        dest.writeString(this.tName);
        dest.writeString(this.email);
        dest.writeString(this.tel);
        dest.writeInt(this.sex);
    }

    public Teacher() {}

    protected Teacher(Parcel in) {
        this.id = (Integer)in.readValue(Integer.class.getClassLoader());
        this.major = in.readString();
        this.tName = in.readString();
        this.email = in.readString();
        this.tel = in.readString();
        this.sex = in.readInt();
    }

    public static final Parcelable.Creator<Teacher> CREATOR = new Parcelable.Creator<Teacher>() {
        @Override
        public Teacher createFromParcel(Parcel source) {return new Teacher(source);}

        @Override
        public Teacher[] newArray(int size) {return new Teacher[size];}
    };
}