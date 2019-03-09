package pers.zjc.sams.module.face.contract;

import android.graphics.Bitmap;

import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.zp.android.zlib.base.BaseModel;
import com.zp.android.zlib.base.BasePresenter;
import com.zp.android.zlib.base.BaseView;

import pers.zjc.sams.module.face.FaceDB;

public interface FaceUploadContract {
    interface Model extends BaseModel {
    }

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter<View, Model>{
        void uploadFace(FaceDB mFaceDB, String name, AFR_FSDKFace mAFR_fsdkFace, Bitmap face);
    }
}
