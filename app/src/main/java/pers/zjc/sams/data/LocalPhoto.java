package pers.zjc.sams.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ColumnIgnore;
import com.raizlabs.android.dbflow.structure.BaseModel;

public class LocalPhoto extends BaseModel implements Parcelable {

    private long id;
    @Column
    private String serviceId;
    @Column
    private String photoPath;
    @Column
    private long photoSize;
    @Column
    private long createTime;
    @ColumnIgnore
    private boolean isChange;
    @Column
    private String fileId;
    @ColumnIgnore
    private boolean editable = true;
    @ColumnIgnore
    private boolean canDelete = true;

    public LocalPhoto() { }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getPhotoPath() {
        return this.photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public long getPhotoSize() {
        return this.photoSize;
    }

    public void setPhotoSize(long photoSize) {
        this.photoSize = photoSize;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isChange() {
        return isChange;
    }

    public void setChange(boolean change) {
        isChange = change;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof LocalPhoto) {
            return ((LocalPhoto)obj).getId() == id;
        }
        return super.equals(obj);
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.serviceId);
        dest.writeString(this.photoPath);
        dest.writeLong(this.photoSize);
        dest.writeLong(this.createTime);
        dest.writeByte(this.isChange ? (byte)1 : (byte)0);
        dest.writeString(this.fileId);
        dest.writeByte(this.editable ? (byte)1 : (byte)0);
        dest.writeByte(this.canDelete ? (byte)1 : (byte)0);
    }

    protected LocalPhoto(Parcel in) {
        this.id = in.readLong();
        this.serviceId = in.readString();
        this.photoPath = in.readString();
        this.photoSize = in.readLong();
        this.createTime = in.readLong();
        this.isChange = in.readByte() != 0;
        this.fileId = in.readString();
        this.editable = in.readByte() != 0;
        this.canDelete = in.readByte() != 0;
    }

    public static final Creator<LocalPhoto> CREATOR = new Creator<LocalPhoto>() {
        @Override
        public LocalPhoto createFromParcel(Parcel source) {return new LocalPhoto(source);}

        @Override
        public LocalPhoto[] newArray(int size) {return new LocalPhoto[size];}
    };
}
