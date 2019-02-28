package pers.zjc.sams.data.entity;


import java.util.Date;

/**
 * 审批表
 */
public class Approval {
    private String id;

    private Integer tId;

    private Integer signId;

    private Integer status;

    private Date time;

    private String teacName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public Integer getsignId() {
        return signId;
    }

    public void setsId(Integer sId) {
        this.signId = sId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTeacName() {
        return teacName;
    }

    public void setTeacName(String teacName) {
        this.teacName = teacName;
    }
}