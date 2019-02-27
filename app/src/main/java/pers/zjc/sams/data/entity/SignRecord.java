package pers.zjc.sams.data.entity;

import java.util.Date;

public class SignRecord {

    private String id;

    private Integer stuId;

    private Date signTime;

    private Integer courseId;

    private String location;

    private Integer signStatus;

    private String signIp;

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

    @Override
    public String toString() {
        return "SignRecord{" +
                "id='" + id + '\'' +
                ", stuId=" + stuId +
                ", signTime=" + signTime +
                ", courseId=" + courseId +
                ", location='" + location + '\'' +
                ", signStatus=" + signStatus +
                ", signIp='" + signIp + '\'' +
                '}';
    }
}