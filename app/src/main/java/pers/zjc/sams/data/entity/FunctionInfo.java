package pers.zjc.sams.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class FunctionInfo implements Parcelable {

    private String functionName;
    private int icon;
    private String clazz;

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.functionName);
        dest.writeInt(this.icon);
        dest.writeString(this.clazz);
    }

    public FunctionInfo() {
    }

    public FunctionInfo(String functionName, int icon, String clazz) {
        this.functionName = functionName;
        this.icon = icon;
        this.clazz = clazz;
    }

    protected FunctionInfo(Parcel in) {
        this.functionName = in.readString();
        this.icon = in.readInt();
        this.clazz = in.readString();
    }

    public static final Parcelable.Creator<FunctionInfo> CREATOR = new Parcelable.Creator<FunctionInfo>() {
        @Override
        public FunctionInfo createFromParcel(Parcel source) {
            return new FunctionInfo(source);
        }

        @Override
        public FunctionInfo[] newArray(int size) {
            return new FunctionInfo[size];
        }
    };
}
