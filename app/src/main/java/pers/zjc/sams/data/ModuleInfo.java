package pers.zjc.sams.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class ModuleInfo implements Parcelable {

    public static final Creator<ModuleInfo> CREATOR = new Creator<ModuleInfo>() {
        @Override
        public ModuleInfo createFromParcel(Parcel source) {return new ModuleInfo(source);}

        @Override
        public ModuleInfo[] newArray(int size) {return new ModuleInfo[size];}
    };
    private String moduleId;
    private String name;
    private String icon;
    private String userId;
    private int iconRes;
    private String clazz;
    private String tag;
    private Bundle args;
    private int order;

    private ModuleInfo() { }

    private ModuleInfo(Parcel in) {
        this.moduleId = in.readString();
        this.name = in.readString();
        this.icon = in.readString();
        this.userId = in.readString();
        this.iconRes = in.readInt();
        this.clazz = in.readString();
        this.tag = in.readString();
        this.args = in.readBundle(getClass().getClassLoader());
        this.order = in.readInt();
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Bundle getArgs() {
        return args;
    }

    public void setArgs(Bundle args) {
        this.args = args;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getModuleId() {
        return this.moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.moduleId);
        dest.writeString(this.name);
        dest.writeString(this.icon);
        dest.writeString(this.userId);
        dest.writeInt(this.iconRes);
        dest.writeString(this.clazz);
        dest.writeString(this.tag);
        dest.writeBundle(this.args);
        dest.writeInt(this.order);
    }

    public static class Builder {

        private String moduleId;
        private String name;
        private String icon;
        private String userId;
        private int iconRes;
        private String clazz;
        private String tag;
        private Bundle args;
        private int order;

        public Builder() { }

        public ModuleInfo.Builder moduleId(String moduleId) {
            this.moduleId = moduleId;
            return this;
        }

        public ModuleInfo.Builder name(String name) {
            this.name = name;
            return this;
        }

        public ModuleInfo.Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        public ModuleInfo.Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public ModuleInfo.Builder iconRes(int iconRes) {
            this.iconRes = iconRes;
            return this;
        }

        public ModuleInfo.Builder clazz(String clazz) {
            this.clazz = clazz;
            return this;
        }

        public ModuleInfo.Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public ModuleInfo.Builder args(Bundle args) {
            this.args = args;
            return this;
        }

        public ModuleInfo.Builder order(int order) {
            this.order = order;
            return this;
        }

        public ModuleInfo build() {
            ModuleInfo module = new ModuleInfo();
            module.moduleId = this.moduleId;
            module.name = this.name;
            module.icon = this.icon;
            module.userId = this.userId;
            module.iconRes = this.iconRes;
            module.clazz = this.clazz;
            module.tag = this.tag;
            module.args = this.args;
            module.order = this.order;
            return module;
        }
    }
}

