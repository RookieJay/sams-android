package pers.zjc.sams.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class AttenceRecord implements Parcelable {
    private Integer attenceId;

    private Integer status;

    private Integer stuId;

    private Integer courseId;

    private String operator;

    private Date createTime;

    private Date updateTime;

    public Integer getAttenceId() {
        return attenceId;
    }

    public void setAttenceId(Integer attenceId) {
        this.attenceId = attenceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.attenceId);
        dest.writeValue(this.status);
        dest.writeValue(this.stuId);
        dest.writeValue(this.courseId);
        dest.writeString(this.operator);
        dest.writeLong(this.createTime != null ? this.createTime.getTime() : -1);
        dest.writeLong(this.updateTime != null ? this.updateTime.getTime() : -1);
    }

    public AttenceRecord() {
    }

    protected AttenceRecord(Parcel in) {
        this.attenceId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.stuId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.courseId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.operator = in.readString();
        long tmpCreateTime = in.readLong();
        this.createTime = tmpCreateTime == -1 ? null : new Date(tmpCreateTime);
        long tmpUpdateTime = in.readLong();
        this.updateTime = tmpUpdateTime == -1 ? null : new Date(tmpUpdateTime);
    }

    public static final Parcelable.Creator<AttenceRecord> CREATOR = new Parcelable.Creator<AttenceRecord>() {
        @Override
        public AttenceRecord createFromParcel(Parcel source) {
            return new AttenceRecord(source);
        }

        @Override
        public AttenceRecord[] newArray(int size) {
            return new AttenceRecord[size];
        }
    };
}