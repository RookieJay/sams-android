package pers.zjc.sams.data.entity;

public class Admin {
    private Integer adminId;

    private String aName;

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return aName;
    }

    public void setName(String name) {
        this.aName = name == null ? null : name.trim();
    }
}