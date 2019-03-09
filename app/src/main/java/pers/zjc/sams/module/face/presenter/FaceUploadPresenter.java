package pers.zjc.sams.module.face.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.arcsoft.facerecognition.AFR_FSDKFace;

import javax.inject.Inject;

import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.module.face.FaceDB;
import pers.zjc.sams.module.face.contract.FaceUploadContract;

public class FaceUploadPresenter implements FaceUploadContract.Presenter {

    private FaceUploadContract.View view;
    @Inject
    AppConfig appConfig;

    public FaceUploadPresenter(FaceUploadContract.View view) {
        this.view = view;
    }

    @Override
    public void uploadFace(FaceDB mFaceDB, String name, AFR_FSDKFace mAFR_fsdkFace, Bitmap face) {
        Log.d("uploadFace: ", String.valueOf(appConfig == null));
    }
}
