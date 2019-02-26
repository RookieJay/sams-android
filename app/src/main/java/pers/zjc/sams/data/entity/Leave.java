package pers.zjc.sams.data.entity;


import com.zp.android.zlib.utils.TimeUtils;

import java.util.Date;

public class Leave {
    private String id;

    private Integer stuId;

    private String reason;

    private Date beginTime;

    private Date endTime;

    private Integer courseId;

    private Integer status;

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
}