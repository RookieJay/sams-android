package pers.zjc.sams.data.entity;


import android.os.Parcel;
import android.os.Parcelable;

import com.zp.android.zlib.utils.TimeUtils;

import java.util.Date;

public class Leave implements Parcelable {
    private String id;

    private Integer stuId;

    private String reason;

    private Date beginTime;

    private Date endTime;

    private Integer courseId;

    private Integer status;

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getBeginTime() {
        return TimeUtils.date2String(beginTime);
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return TimeUtils.date2String(endTime);
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        dest.writeString(this.reason);
        dest.writeLong(this.beginTime != null ? this.beginTime.getTime() : -1);
        dest.writeLong(this.endTime != null ? this.endTime.getTime() : -1);
        dest.writeValue(this.courseId);
        dest.writeValue(this.status);
        dest.writeString(this.courseName);
        dest.writeString(this.stuName);
    }

    public Leave() {
    }

    protected Leave(Parcel in) {
        this.id = in.readString();
        this.stuId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.reason = in.readString();
        long tmpBeginTime = in.readLong();
        this.beginTime = tmpBeginTime == -1 ? null : new Date(tmpBeginTime);
        long tmpEndTime = in.readLong();
        this.endTime = tmpEndTime == -1 ? null : new Date(tmpEndTime);
        this.courseId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.courseName = in.readString();
        this.stuName = in.readString();
    }

    public static final Parcelable.Creator<Leave> CREATOR = new Parcelable.Creator<Leave>() {
        @Override
        public Leave createFromParcel(Parcel source) {
            return new Leave(source);
        }

        @Override
        public Leave[] newArray(int size) {
            return new Leave[size];
        }
    };
}