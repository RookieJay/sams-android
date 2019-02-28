package pers.zjc.sams.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class SignRecord implements Parcelable {

    private String id;

    private Integer stuId;

    private Date signTime;

    private Integer courseId;

    private String location;

    private Integer signStatus;

    private String signIp;

    private String courseName;

    private String stuName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Date getSignTime() {
        return signTime;
    }

//    public String getSignTimeStr() {
//        return TimeUtils.date2String(signTime, Const.DateFormat.WITH_HMS);
//    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Integer getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Integer signStatus) {
        this.signStatus = signStatus;
    }

    public String getSignIp() {
        return signIp;
    }

    public void setSignIp(String signIp) {
        this.signIp = signIp == null ? null : signIp.trim();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeValue(this.stuId);
        dest.writeLong(this.signTime != null ? this.signTime.getTime() : -1);
        dest.writeValue(this.courseId);
        dest.writeString(this.location);
        dest.writeValue(this.signStatus);
        dest.writeString(this.signIp);
        dest.writeString(this.courseName);
        dest.writeString(this.stuName);
    }

    public SignRecord() {
    }

    protected SignRecord(Parcel in) {
        this.id = in.readString();
        this.stuId = (Integer) in.readValue(Integer.class.getClassLoader());
        long tmpSignTime = in.readLong();
        this.signTime = tmpSignTime == -1 ? null : new Date(tmpSignTime);
        this.courseId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.location = in.readString();
        this.signStatus = (Integer) in.readValue(Integer.class.getClassLoader());
        this.signIp = in.readString();
        this.courseName = in.readString();
        this.stuName = in.readString();
    }

    public static final Parcelable.Creator<SignRecord> CREATOR = new Parcelable.Creator<SignRecord>() {
        @Override
        public SignRecord createFromParcel(Parcel source) {
            return new SignRecord(source);
        }

        @Override
        public SignRecord[] newArray(int size) {
            return new SignRecord[size];
        }
    };
}